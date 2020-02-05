package be.smals.ebox.poc.oauth.instropsect;

public abstract class POC_CONFIG {
    public static POC_CONFIG inst;

    public abstract String getOauth2V2Endpoint();
    public abstract String getAudience();
    public String getOauth2V2TokenEndpoint(){
        return getOauth2V2Endpoint()+"/token";
    }
    public String getOauth2V2IntrospectEndpoint(){
        return getOauth2V2Endpoint()+"/introspect";
    }


    public abstract OauthConfig getDocumentProviderOauthConfig() ;

    public abstract OauthConfig getDocumentConsumerOauthConfig() ;
}
