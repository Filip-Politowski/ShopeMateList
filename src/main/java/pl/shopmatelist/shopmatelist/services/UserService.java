package pl.shopmatelist.shopmatelist.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    Integer idFromUserToken(String token);
}
