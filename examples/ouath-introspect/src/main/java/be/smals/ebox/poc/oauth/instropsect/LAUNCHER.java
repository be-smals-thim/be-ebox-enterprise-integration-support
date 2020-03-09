package be.smals.ebox.poc.oauth.instropsect;

public class LAUNCHER {
    public static void main(String[] args) {
        POC_CONFIG.inst=new POC_CONFIG(){

            @Override
            public String getOauth2V2Endpoint() {
                return "https://services-acpt.socialsecurity.be/REST/oauth/v3";
            }

            @Override
            public String getAudience() {
                return "https://oauthacc.socialsecurity.be";
            }

            @Override
            public OauthConfig getDocumentProviderOauthConfig() {
                /**
                 * The Document Provider configuration for getting a token that can perform an introspect
                 */
                return new OauthConfig()
                        .setKeyFileLocation("<classpath location of the .p12 file>") // e.g. /jks/documentmanagement_publication_ebox.p12
                        .setPassword("<p12 password>")
                        .setAlias("<alias in the .p12>") // e.g. documentmanagement:publication:ebox
                        .setAliasPassword("<p12 alias password>")
                        .setClientId("<oauth client urn>"); // e.g. documentmanagement:publication:ebox:client
            }

            @Override
            public OauthConfig getDocumentConsumerOauthConfig() {
                /**
                 * The client-credential client configuration for getting a Document Consumer token that can be introspected
                 * Usually a different identity than that of the server
                 */
                return new OauthConfig()
                        .setKeyFileLocation("<classpath location of the .p12 file>") // e.g. /jks/documentmanagement_publication_ebox.p12
                        .setPassword("<p12 password>")
                        .setAlias("<alias in the .p12>") // e.g. documentmanagement:publication:ebox
                        .setAliasPassword("<p12 alias password>")
                        .setClientId("<oauth client urn>"); // e.g. documentmanagement:publication:ebox:client

            }
        };
        new IntrospectPoc().start();
    }
}
