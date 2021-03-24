package com.sebastian.springrestapp.setup;

import com.sebastian.springrestapp.model.PermissionEntity;
import com.sebastian.springrestapp.model.PermissionEnum;
import com.sebastian.springrestapp.model.RoleEntity;
import com.sebastian.springrestapp.model.UserEntity;
import com.sebastian.springrestapp.repository.PermissionRepository;
import com.sebastian.springrestapp.repository.RoleRepository;
import com.sebastian.springrestapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    public static final String ROLE_ADMIN = "ADMIN";

    private boolean setupFinished = false;

    private final UserRepository userRepository;


    private final RoleRepository roleRepository;


    private final PermissionRepository permissionRepository;


    private final PasswordEncoder encoder;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (setupFinished) return;
        Set<String> permissionNames = PermissionEnum.getNames();
        Set<PermissionEntity> permissions = createPermissions(permissionNames);
        RoleEntity adminRole = createAdminRole(permissions);
        createAdminUser(adminRole);
        setupFinished = true;
    }


    @Transactional
    Set<PermissionEntity> createPermissions(Set<String> permissions) {
        return permissions.stream().map(this::createPermission).collect(Collectors.toSet());
    }

    @Transactional
    PermissionEntity createPermission(String name) {
        var permission = new PermissionEntity(name);
        return permissionRepository.save(permission);
    }

    @Transactional
    RoleEntity createAdminRole(Collection<PermissionEntity> permissionsEntities) {
        var role = new RoleEntity(ROLE_ADMIN, permissionsEntities);
        return roleRepository.save(role);
    }

    @Transactional
    void createAdminUser(RoleEntity role) {
        UserEntity user = new UserEntity("admin", encoder.encode("admin"), role);
        userRepository.save(user);
    }
}
