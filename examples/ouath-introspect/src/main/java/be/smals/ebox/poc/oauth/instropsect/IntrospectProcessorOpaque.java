package be.smals.ebox.poc.oauth.instropsect;

import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import net.minidev.json.JSONObject;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


public class IntrospectProcessorOpaque {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String introspectUrl;


    /**
     *
     */
    public IntrospectProcessorOpaque(String introspectUrl) {
        this.introspectUrl = Objects.requireNonNull(introspectUrl);
    }

    public Optional<JwtClaims> getClaims(String securityAccessToken, String accessToken) {

        logger.debug("getClaims(String securityAccessToken,String accessToken)");

        JwtClaims claims = null;

        TokenIntrospectionResponse response;

        try {
            TokenIntrospectionRequest request = new TokenIntrospectionRequest(new URI(this.introspectUrl), new BearerAccessToken(securityAccessToken), new BearerAccessToken(accessToken), null);
            response = send(request);
        } catch (Exception e) {
            logger.error("Cannot call introspect with url ({}) [{}]", this.introspectUrl, e.getMessage());
            throw new RuntimeException("Cannot call introspect",e);
        }


        if (response.indicatesSuccess()) {
            TokenIntrospectionSuccessResponse successResponse = (TokenIntrospectionSuccessResponse) response;

            claims = new JwtClaims();
            JSONObject jsonObject = successResponse.toJSONObject(); // ATTENTION: THIM a changer ceci
            Set<Entry<String, Object>> set = jsonObject.entrySet();
            for (Entry<String, Object> entry : set) {

                logger.debug("processing claim {} of type {}", entry, entry.getValue().getClass());

                if (JSONObject.class.isInstance(entry.getValue())) {
                    // for principalAttributes claim
                    JSONObject value = (JSONObject) entry.getValue();
                    try {
                        // to return only jose4j class, not minidev class
                        claims.setClaim(entry.getKey(), JsonUtil.parseJson(value.toJSONString()));
                    } catch (JoseException e) {
                        logger.error("Cannot parse json for claim {}", entry.getKey());
                    }
                } else {
                    // default, String, Boolean, ...
                    claims.setClaim(entry.getKey(), entry.getValue());
                }
            }

        } else {
            TokenIntrospectionErrorResponse errorResponse = (TokenIntrospectionErrorResponse) response;
            logger.error("Cannot get claims with introspect with url ({}) [{}]", this.introspectUrl, errorResponse.getErrorObject().getHTTPStatusCode());
            throw new RuntimeException("Cannot get claims with introspect");
        }

        logger.debug("claims {}", claims);

        return Optional.ofNullable(claims);

    }

    /**
     * Added for testing
     */
    protected TokenIntrospectionResponse send(TokenIntrospectionRequest request) throws ParseException, IOException {
        return TokenIntrospectionResponse.parse(request.toHTTPRequest().send());
    }
}
