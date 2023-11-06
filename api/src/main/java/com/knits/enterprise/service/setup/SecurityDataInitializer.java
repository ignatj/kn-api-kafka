package com.knits.enterprise.service.setup;

import com.knits.enterprise.dto.data.security.RoleDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.repository.security.RoleRepository;
import com.knits.enterprise.repository.security.UserRepository;
import com.knits.enterprise.service.security.RoleService;
import com.knits.enterprise.service.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class SecurityDataInitializer {

    private static final String ADMIN_ROLE = "Admin";
    private static final String USERNAME = "stefanofiorenza";

    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleService roleService;

    public boolean checkAdminExists() {
        if (userRepository.findOneByUsername(USERNAME).isPresent()) {
            log.info("{} already existing. DB initialization will be skipped", USERNAME);
            log.info("setupDatabase Terminated.");
            return true;
        }
        return false;
    }

    public User createAdminUser() {

        Optional<RoleDto> optionalRole = roleService.findByName(ADMIN_ROLE);
        if (optionalRole.isEmpty()) {
            createRole();
        }
        createUser(ADMIN_ROLE);
        log.info("createAdminUser Completed.");
        return userRepository.findOneByUsername(USERNAME).get();

    }

    private void createUser(String roleName) {
        UserDto newUser = UserDto.builder()
                                 .firstName("Stefano")
                                 .lastName("Fiorenza")
                                 .password("123")
                                 .username(USERNAME)
                                 .active(true)
                                 .roleName(roleName)
                                 .email("stefanofiorenza@email.it")
                                 .build();

        userService.save(newUser);
    }

    private String createRole() {
        RoleDto roleDto = RoleDto.builder().
                                 name(ADMIN_ROLE).build();

        RoleDto created = roleService.create(roleDto);
        return created.getName();
    }
}
