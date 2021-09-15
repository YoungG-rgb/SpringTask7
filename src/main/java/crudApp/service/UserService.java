package crudApp.service;

import crudApp.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    void add(User user);
    void add(User user, Long [] roles);
    void delete(int id);
    void update(User user, Long[] roles);
    User getById(int id);
}