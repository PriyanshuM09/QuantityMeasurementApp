package com.placement.authservice.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleTokenInfo {
    private String aud;
    private String sub;
    private String email;
    private String name;

    @JsonProperty("email_verified")
    private Boolean emailVerified;
}
