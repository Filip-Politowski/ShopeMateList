package pl.shopmatelist.shopmatelist.services.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.repository.UserRepository;
import pl.shopmatelist.shopmatelist.services.security.JwtService;
import pl.shopmatelist.shopmatelist.services.security.UserService;

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
    public User userFromToken(String token) {
        String email = jwtService.extractUserName(token.substring(7));
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NoSuchElementException("Nie ma takiego użytkownika");
    }

    @Override
    public User findByUserId(Long id) {
       Optional<User> user = userRepository.findById(id);
       if(user.isPresent()){
           return user.get();
       }else{
           throw new NoSuchElementException("Nie ma takiego użytkownika");
       }

    }


}
