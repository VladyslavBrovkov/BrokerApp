package myapps.brokerapp.service.impl;

import myapps.brokerapp.model.User;
import myapps.brokerapp.model.enums.UserRole;
import myapps.brokerapp.service.AccountService;
import myapps.brokerapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;
    private final Set<User> userSet;

    public UserServiceImpl(PasswordEncoder passwordEncoder, AccountService accountService) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
        userSet = new HashSet<>();
        userSet.add(new User(1, "admin", "admin", "$2a$12$CRXi0CESN3TXg8yTqKs64uSeBR5RN0td99hrRk8F6c3HiqR5EOKB2", UserRole.ADMIN,
                accountService.generateAccount()));
    }

    @Override
    public User findUserByLogin(String login) {
        return userSet.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void registerUser(User user) {
        Integer maxId = userSet.stream()
                .map(User::getId)
                .max(Integer::compareTo)
                .orElse(1);
        user.setId(maxId + 1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.TRADER);
        user.setAccount(accountService.generateAccount());
        userSet.add(user);
    }

    @Override
    public Set<User> getAll() {
        return userSet;
    }
}
