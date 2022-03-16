package xyz.flevarakis.arubatranquility.models;

import java.io.Serializable;

public class Instance implements Serializable {
    private String hostname;
    private String email;
    private String password;
    private String token;

    public Instance(String hostname, String email, String password) {
        this.hostname = hostname;
        this.email = email;
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
