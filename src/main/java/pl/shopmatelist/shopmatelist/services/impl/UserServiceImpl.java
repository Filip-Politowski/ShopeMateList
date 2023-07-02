package pl.shopmatelist.shopmatelist.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.repository.UserRepository;
import pl.shopmatelist.shopmatelist.services.JwtService;
import pl.shopmatelist.shopmatelist.services.UserService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public Integer idFromUserToken(String token) {
        String email = jwtService.extractUserName(token.substring(7));
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getId();
        }
        throw new NoSuchElementException("Nie ma takiego użytkownika");
    }

}
