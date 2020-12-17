package br.com.wagnerandrade.springboot.service;

import br.com.wagnerandrade.springboot.repository.SpringUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpringUserDetailsService implements UserDetailsService {
    private final SpringUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(this.repository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Spring User not found"));
    }
}
