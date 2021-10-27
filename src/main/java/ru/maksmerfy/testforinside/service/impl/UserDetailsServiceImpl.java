package ru.maksmerfy.testforinside.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maksmerfy.testforinside.models.User;
import ru.maksmerfy.testforinside.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        if (!user.getUsername().equals(username)) throw new UsernameNotFoundException(username);
        return UserDetailsImpl.fromUserEntityToCustomUserDetails(user);
    }
}
