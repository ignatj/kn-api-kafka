package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.WorkingAreaDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.mapper.location.WorkingAreaMapper;
import com.knits.enterprise.mocks.dto.location.WorkingAreaDtoMock;
import com.knits.enterprise.mocks.dto.security.UserDtoMock;
import com.knits.enterprise.mocks.model.location.FloorMock;
import com.knits.enterprise.mocks.model.location.WorkingAreaMock;
import com.knits.enterprise.model.location.Floor;
import com.knits.enterprise.model.location.WorkingArea;
import com.knits.enterprise.repository.location.FloorRepository;
import com.knits.enterprise.repository.location.WorkingAreaRepository;
import com.knits.enterprise.service.security.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WorkingAreaServiceTest {

    @Mock
    private WorkingAreaRepository workingAreaRepository;

    @Mock
    private WorkingAreaMapper workingAreaMapper;

    @Mock
    private UserService userService;

    @Mock
    private FloorRepository floorRepository;

    @Captor
    private ArgumentCaptor<WorkingArea> workingAreaArgumentCaptor;

    @InjectMocks
    private WorkingAreaService workingAreaService;

    @Test
    void saveSuccess() {
        Long workingAreaId = 1L;
        Long floorId = 1L;

        WorkingAreaDto toSaveDto = WorkingAreaDtoMock.shallowWorkingAreaDto(workingAreaId, floorId);
        WorkingArea workingArea = WorkingAreaMock.shallowWorkingArea(workingAreaId);
        Floor floor = FloorMock.shallowFloor(floorId);
        UserDto userDto = UserDtoMock.shallowUserDto(null);

        when(userService.getCurrentUserAsDto()).thenReturn(userDto);
        when(floorRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(floor));
        when(workingAreaMapper.toEntity(Mockito.any(WorkingAreaDto.class))).thenReturn(workingArea);
        when(userService.getCurrentUserAsEntity()).thenReturn(null);
        when(workingAreaRepository.save(Mockito.any(WorkingArea.class))).thenAnswer(
                invocation -> invocation.getArguments()[0]);
        when(workingAreaMapper.toDto(Mockito.any(WorkingArea.class))).thenReturn(toSaveDto);

        workingAreaService.create(toSaveDto);

        verify(floorRepository, times(1)).findById(toSaveDto.getFloorId());
        verify(workingAreaMapper, times(1)).toEntity(toSaveDto);
        verify(workingAreaRepository).save(workingAreaArgumentCaptor.capture());
        WorkingArea capturedWorkingArea = workingAreaArgumentCaptor.getValue();
        verify(workingAreaMapper, times(1)).toDto(capturedWorkingArea);

        assertThat(capturedWorkingArea.getFloor().getId()).isEqualTo(floorId);
    }
}
