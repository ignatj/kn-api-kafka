package com.knits.enterprise.service.security;

import com.knits.enterprise.dto.data.security.RoleDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.security.RolerMapper;
import com.knits.enterprise.model.security.Role;
import com.knits.enterprise.repository.security.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class RoleService {

    private final RolerMapper rolerMapper;

    private final RoleRepository roleRepository;

    public RoleDto create(RoleDto roleDto) {
        Role role = rolerMapper.toEntity(roleDto);
        if (roleRepository.existsRoleByName(roleDto.getName())) {
            throw new UserException("Role %s already exists".formatted(roleDto.getName()));
        }
        role.setActive(true);
        return rolerMapper.toDto(roleRepository.save(role));
    }

    public Optional<RoleDto> findByName(String name) {
        Optional<Role> roleIfAny = roleRepository.findOneByName(name);
        return roleIfAny.map(rolerMapper::toDto).or(Optional::empty);
    }
}
