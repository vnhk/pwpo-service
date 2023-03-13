package com.pwpo.security;


import com.pwpo.user.AccountRole;
import com.pwpo.user.UserAccount;
import com.pwpo.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        Optional<UserAccount> userOpt = userRepository.findByNick(nick);

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        List<GrantedAuthority> authorityList = new ArrayList<>();
        UserAccount user = userOpt.get();

        Set<AccountRole> roles = user.getRoles();
        for (AccountRole role : roles) {
            authorityList.add(new SimpleGrantedAuthority(role.name()));
        }
        return new User(user.getNick(), user.getPassword(), authorityList);
    }
}