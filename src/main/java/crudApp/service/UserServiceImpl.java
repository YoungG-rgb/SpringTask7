package crudApp.service;

import crudApp.dao.RoleDao;
import crudApp.dao.UserDao;
import crudApp.models.Role;
import crudApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    // не понимаю, внедрение через конструктор не идет ( если через конструктор внедряю, ошибка идет в классе с PostConstruct )
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
    }

    @Override
    public void add(User user, Long [] roles) {
        Set <Role> containerRoles = new HashSet<>();
        if (roles == null) {
            containerRoles.add(roleDao.getRoleById(2L));
        } else {
            containerRoles = Arrays.stream(roles)
                    .map(roleDao::getRoleById)
                    .collect(Collectors.toSet());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(containerRoles);
        userDao.add(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public void update(User user, Long [] roles) {
        if (roles != null) {
            user.setRoles(
                    Arrays.stream(roles)
                    .map(roleDao::getRoleById)
                    .collect(Collectors.toSet())
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByName(s);
        if (user == null){
            throw new UsernameNotFoundException("USERNAME IS NULL");
        }
        return user;
    }
}
