package crudApp.config.OtherConfig;

import crudApp.models.Role;
import crudApp.models.User;
import crudApp.service.RoleService;
import crudApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class AddAdminAndRoleToDataBase {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AddAdminAndRoleToDataBase(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void doInit(){
        // roles
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleService.save(roleAdmin);
        roleService.save(roleUser);
        // set for Admin
        Set<Role> setRolesAdminAndUser = new HashSet<>();
        setRolesAdminAndUser.add(roleAdmin);
        setRolesAdminAndUser.add(roleUser);
        // set for User
        Set<Role> setRolesUser = new HashSet<>();
        setRolesUser.add(roleUser);
        // Admin
        User user = new User("Zhalaldin", "Toichubaev", (byte)20, "Zhalal", "admin");
        user.setRoles(setRolesAdminAndUser);
        // User
        User user2 = new User("Igor", "Ptushkin",(byte)27, "Igorek","igorek123");
        user2.setRoles(setRolesUser);

        userService.add(user);
        userService.add(user2);
    }
}
