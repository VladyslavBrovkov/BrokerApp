package myapps.brokerapp.service.impl;

import myapps.brokerapp.model.User;
import myapps.brokerapp.security.SecurityUser;
import myapps.brokerapp.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User foundedUser =  userService.findUserByLogin(userName);
        if (foundedUser == null){
            throw new UsernameNotFoundException("User not found!");
        }
        return new SecurityUser(foundedUser);
    }

}
