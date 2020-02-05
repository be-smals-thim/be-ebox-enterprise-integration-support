package be.smals.ebox.poc.oauth.instropsect;

import java.util.Objects;

public class OauthConfig {
    private String alias;
    private String password;
    private String aliasPassword;
    private String keyFileLocation;
    private String clientUrn;

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

    public String getClientUrn() {
        return clientUrn;
    }

    public OauthConfig setClientUrn(String clientUrn) {
        this.clientUrn = clientUrn;
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
                Objects.equals(clientUrn, that.clientUrn);
    }

    @Override
    public int hashCode() {

        return Objects.hash(alias, password, aliasPassword, keyFileLocation, clientUrn);
    }
}
