package pl.shopmatelist.shopmatelist.services;

import pl.shopmatelist.shopmatelist.entity.request.SignInRequest;
import pl.shopmatelist.shopmatelist.entity.request.SignUpRequest;
import pl.shopmatelist.shopmatelist.entity.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
}
