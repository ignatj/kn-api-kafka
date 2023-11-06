package com.knits.enterprise.mocks.model.security;

import com.knits.enterprise.model.security.Role;

public class RoleMock {
    public static Role shallowRole(Long counter) {
        return shallowRole(counter,"mock Role Name");
     }

    public static Role shallowRole(Long counter,String name) {
        return Role.builder().
                id(counter).
                name(name).
                build();
    }
}
