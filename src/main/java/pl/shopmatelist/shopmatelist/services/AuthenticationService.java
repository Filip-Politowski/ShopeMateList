package pl.shopmatelist.shopmatelist.services;

import pl.shopmatelist.shopmatelist.dto.request.SignInRequest;
import pl.shopmatelist.shopmatelist.dto.request.SignUpRequest;
import pl.shopmatelist.shopmatelist.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
}
