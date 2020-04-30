package be.smals.ebox.poc.oauth.instropsect;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.auth.*;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.id.ClientID;

import java.io.InputStream;
import java.net.URI;
import java.security.Key;
import java.security.KeyStore;
import java.security.Provider;
import java.security.interfaces.RSAPrivateKey;

public class GetAccessTokenV3 {

    public String getAccessToken(OauthConfig oauthConfig,String ...scopes){
        try {
// Construct the client credentials grant
            AuthorizationGrant clientGrant = new ClientCredentialsGrant();

            InputStream is = this.getClass().getResourceAsStream(oauthConfig.getKeyFileLocation());

            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(is, oauthConfig.getPassword().toCharArray());
            Key key=keystore.getKey(oauthConfig.getAlias(),oauthConfig.getAliasPassword().toCharArray());

            URI tokenEndpoint = new URI(POC_CONFIG.inst.getOauth2V2TokenEndpoint());
// The credentials to authenticate the client at the token endpoint
            ClientID clientID = new ClientID(oauthConfig.getClientId());

            ClientAuthentication clientAuth = new PrivateKeyJWT(clientID,
                    new URI(POC_CONFIG.inst.getAudience()), JWSAlgorithm.RS256, (RSAPrivateKey)key,
                    null, (Provider)null);


// The request scope for the token (may be optional)
            Scope scope = new Scope(scopes);

// The token endpoint

// Make the token request
            TokenRequest request = new TokenRequest(tokenEndpoint, clientAuth, clientGrant, scope);
            HTTPRequest httpRequest=request.toHTTPRequest();
          //  System.out.println(request.toHTTPRequest());
           // System.out.println(new ObjectMapper().writer().writeValueAsString(httpRequestrequest.toHTTPRequest()
            TokenResponse response = TokenResponse.parse(httpRequest.send());

            if (!response.indicatesSuccess()) {
                throw new RuntimeException(TokenErrorResponse.parse(response.toHTTPResponse()).toHTTPResponse().getStatusCode()+"\n"
                +response.toHTTPResponse().getStatusMessage()+"\n"
                +response.toHTTPResponse().getContent());

            }

            AccessTokenResponse successResponse = AccessTokenResponse.parse(response.toHTTPResponse());
            return successResponse.getTokens().getAccessToken().getValue();
        }
        catch(RuntimeException e){
            throw e;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
