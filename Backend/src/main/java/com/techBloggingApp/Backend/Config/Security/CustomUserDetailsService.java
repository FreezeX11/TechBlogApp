package com.techBloggingApp.Backend.Config.Security;

import com.techBloggingApp.Backend.Entity.User;
import com.techBloggingApp.Backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found"));

        return CustomUserDetails.build(user);
    }
}
