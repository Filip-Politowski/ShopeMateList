package pl.shopmatelist.shopmatelist.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.shopmatelist.shopmatelist.entity.User;

public interface UserService {
    UserDetailsService userDetailsService();
    User userFromToken(String token);
    User findByUserId(Long id);
}
