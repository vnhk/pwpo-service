package com.pwpo.security;

import com.pwpo.user.UserAccount;
import com.pwpo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        Optional<UserAccount> userOptional = userRepository.findByNick(nick);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User 404");
        }

        return new UserPrincipal(userOptional.get());
    }
}
