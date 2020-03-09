package be.smals.ebox.poc.oauth.instropsect;

import java.util.Objects;

public class OauthConfig {
    private String alias;
    private String password;
    private String aliasPassword;
    private String keyFileLocation;
    private String clientId;

    public String getAlias() {
        return alias;
    }

    public OauthConfig setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public OauthConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAliasPassword() {
        return aliasPassword;
    }

    public OauthConfig setAliasPassword(String aliasPassword) {
        this.aliasPassword = aliasPassword;
        return this;
    }

    public String getKeyFileLocation() {
        return keyFileLocation;
    }

    public OauthConfig setKeyFileLocation(String keyFileLocation) {
        this.keyFileLocation = keyFileLocation;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OauthConfig setClientId(String clientUrn) {
        this.clientId = clientUrn;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OauthConfig that = (OauthConfig) o;
        return Objects.equals(alias, that.alias) &&
                Objects.equals(password, that.password) &&
                Objects.equals(aliasPassword, that.aliasPassword) &&
                Objects.equals(keyFileLocation, that.keyFileLocation) &&
                Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(alias, password, aliasPassword, keyFileLocation, clientId);
    }
}
