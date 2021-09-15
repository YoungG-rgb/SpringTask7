package crudApp.dao;

import crudApp.models.Role;
import java.util.Set;

public interface RoleDao {
    public void save(Role role);
    public Set<Role> getAllRoles();
    public Role getRoleById(Long id);
}
