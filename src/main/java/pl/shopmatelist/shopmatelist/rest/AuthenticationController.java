package pl.shopmatelist.shopmatelist.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shopmatelist.shopmatelist.dto.request.SignInRequest;
import pl.shopmatelist.shopmatelist.dto.request.SignUpRequest;
import pl.shopmatelist.shopmatelist.dto.response.JwtAuthenticationResponse;
import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
import pl.shopmatelist.shopmatelist.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request){
       try {
           return ResponseEntity.ok(authenticationService.signin(request));
       }catch (AuthorizationException exc){
           throw new AuthorizationException(exc.getMessage());
       }

    }
}
