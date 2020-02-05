package be.smals.ebox.poc.oauth.instropsect;

import org.jose4j.jwt.JwtClaims;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

public class IntrospectPoc {


    public void start(){
        System.out.println("Started at: "+new Date());
        try {
            // Get token for Document Provider (used fo introspect)
            String introspectAccessToken = new GetAccessTokenV3().getAccessToken(POC_CONFIG.inst.getDocumentProviderOauthConfig(), "scope:security:authorization:oauth:oauth-autz-server:introspect");

            // Get token for Document Consumer (used to get ebox messages)
            String accessToken = new GetAccessTokenV3().getAccessToken(POC_CONFIG.inst.getDocumentConsumerOauthConfig(),
                    "scope:document:management:consult:ws-eboxrestentreprise:messagesfull",
                    "scope:document:management:consult:ws-eboxrestentreprise:summaryownebox"
            );
            System.out.println("Client token: " + accessToken);
            System.out.println("Server token: " + introspectAccessToken);

            JwtClaims claims = new IntrospectProcessorOpaque(POC_CONFIG.inst.getOauth2V2IntrospectEndpoint())
                    .getClaims(introspectAccessToken, accessToken)
                    .orElseThrow(() -> new RuntimeException("Oho"));

            System.out.println(claims.getClaimNames());

            String kbo = tryGet(() -> ((Map<String, ArrayList<String>>) claims.getClaimValue("principalAttributes")).get("urn:be:fgov:kbo-bce:organization:cbe-number").get(0));
            System.out.println("Kbo is: " + kbo);
            System.out.println("Exp is: " + new Date(1000 * (Long) claims.getClaimValue("exp")));
            System.out.println("Active is: " + claims.getClaimValue("active"));
            System.out.println("Token type is: " + claims.getClaimValue("token_type"));
            System.out.println("Scopes are: " + claims.getClaimValue("scope"));
        }finally {
            System.out.println("Finished at: "+new Date());
        }
    }

    private static <T>T tryGet(Supplier<T> supplier) {
        try{
            return supplier.get();
        }catch(RuntimeException e){
            return null;
        }
    }
}
