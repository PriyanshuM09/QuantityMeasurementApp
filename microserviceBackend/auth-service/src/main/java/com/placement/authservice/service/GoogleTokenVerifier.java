package com.placement.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleTokenVerifier {

    private final RestClient restClient = RestClient.create();

    @Value("${google.oauth.client-id:}")
    private String googleClientId;

    public GoogleUserInfo verify(String idToken) {
        if (!StringUtils.hasText(googleClientId)) {
            throw new RuntimeException("Google client ID is not configured");
        }

        if (!StringUtils.hasText(idToken)) {
            throw new RuntimeException("Google ID token is required");
        }

        String url = UriComponentsBuilder
                .fromUriString("https://oauth2.googleapis.com/tokeninfo")
                .queryParam("id_token", idToken)
                .build()
                .toUriString();

        GoogleTokenInfo tokenInfo = restClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new RuntimeException("Invalid Google token");
                })
                .body(GoogleTokenInfo.class);

        if (tokenInfo == null || !StringUtils.hasText(tokenInfo.getSub()) || !StringUtils.hasText(tokenInfo.getEmail())) {
            throw new RuntimeException("Invalid Google token");
        }

        if (!googleClientId.equals(tokenInfo.getAud())) {
            throw new RuntimeException("Google token audience mismatch");
        }

        if (!Boolean.TRUE.equals(tokenInfo.getEmailVerified())) {
            throw new RuntimeException("Google email is not verified");
        }

        return new GoogleUserInfo(tokenInfo.getSub(), tokenInfo.getEmail(), tokenInfo.getName());
    }
}
