package com.knits.enterprise.service.security;

import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.mapper.security.RolerMapper;
import com.knits.enterprise.mapper.security.RolerMapperImpl;
import com.knits.enterprise.mapper.security.UserMapper;
import com.knits.enterprise.mapper.security.UserMapperImpl;
import com.knits.enterprise.mocks.dto.security.UserDtoMock;
import com.knits.enterprise.mocks.model.security.RoleMock;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.repository.security.RoleRepository;
import com.knits.enterprise.repository.security.UserRepository;
import com.knits.enterprise.utils.TestConsts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private static final String MOCK_ENCODED_PW="3423454YSGSFGFG345REFSDFA";
    private static final String ADMIN="admin";
    @Test
    @DisplayName("Save User Success")
    void saveSuccess() {

        //1) create some mock data (make them reusable in ohter tests)
        UserDto toSaveDto = UserDtoMock.shallowUserDto(null);
        toSaveDto.setRoleName(ADMIN);

        //2) prepare mocks for everything they should return
        when(userRepository.save(Mockito.any(User.class))) //any object of type User will match here
                .thenAnswer(i -> i.getArguments()[0]); //echo 1st parameter received

        when(roleRepository.findOneByName(toSaveDto.getRoleName())).thenReturn(Optional.of(RoleMock.shallowRole(TestConsts.NO_COUNTER,ADMIN)));

        when(passwordEncoder.encode(Mockito.any(String.class))).thenReturn(MOCK_ENCODED_PW);
        //3) class under test
        UserDto savedDto = userService.save(toSaveDto);

        //4) use captor in spy/mocks for everything they get as input
        verify(userRepository).save(userCaptor.capture());
        User toSaveEntity = userCaptor.getValue();

        //5) check if all dependencies were called (eventually with check on input data)
        verify(userMapper, times(1)).toEntity(toSaveDto);
        verify(userRepository, times(1)).save(toSaveEntity);
        verify(userMapper, times(1)).toDto(toSaveEntity);

        //6) assertions actual vs expected
        assertThat(savedDto).usingRecursiveComparison().ignoringFields("id","token","authorities").isEqualTo(toSaveDto);
     }

    @Test
    @DisplayName("delete success")
    void deleteSuccess() {
        Long entityIdToDelete = 1L;
        userService.delete(entityIdToDelete);
    }
}
