package com.kunal.service_now.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.List;


@Component
@Slf4j
public class ServiceNowAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RestTemplate restTemplate;


    @Value("${service.now.instance}")
    private String serviceNowUrl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Call ServiceNow API to validate credentials
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    serviceNowUrl + "/api/now/table/sys_user?user_name=" + username + "&sysparm_limit=1", HttpMethod.GET, new HttpEntity<>(headers), String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
            }
        } catch (Exception e) {
            log.error("Authentication failed for user {}: {}", username, e.getMessage());
            throw new BadCredentialsException("Invalid credentials");
        }
        throw new BadCredentialsException("Invalid credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
