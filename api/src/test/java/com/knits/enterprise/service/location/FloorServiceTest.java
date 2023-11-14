package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.FloorDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.mapper.location.FloorMapper;
import com.knits.enterprise.mocks.dto.location.FloorDtoMock;
import com.knits.enterprise.mocks.dto.security.UserDtoMock;
import com.knits.enterprise.mocks.model.location.BuildingMock;
import com.knits.enterprise.mocks.model.location.FloorMock;
import com.knits.enterprise.model.location.Building;
import com.knits.enterprise.model.location.Floor;
import com.knits.enterprise.repository.location.BuildingRepository;
import com.knits.enterprise.repository.location.FloorRepository;
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
public class FloorServiceTest {

    @Mock
    private FloorRepository floorRepository;

    @Mock
    private FloorMapper floorMapper;

    @Mock
    private UserService userService;

    @Mock
    private BuildingRepository buildingRepository;

    @Captor
    private ArgumentCaptor<Floor> floorArgumentCaptor;

    @InjectMocks
    private FloorService floorService;

    @Test
    void saveSuccess() {
        Long floorId = 1L;
        Long buildingId = 1L;

        FloorDto toSaveDto = FloorDtoMock.shallowFloorDto(floorId, buildingId);
        Floor floor = FloorMock.shallowFloor(floorId);
        Building building = BuildingMock.shallowBuilding(buildingId);
        UserDto userDto = UserDtoMock.shallowUserDto(null);

        when(userService.getCurrentUserAsDto()).thenReturn(userDto);
        when(buildingRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(building));
        when(floorMapper.toEntity(Mockito.any(FloorDto.class))).thenReturn(floor);
        when(userService.getCurrentUserAsEntity()).thenReturn(null);
        when(floorRepository.save(Mockito.any(Floor.class))).thenAnswer(
                invocation -> invocation.getArguments()[0]);
        when(floorMapper.toDto(Mockito.any(Floor.class))).thenReturn(toSaveDto);

        floorService.create(toSaveDto);

        verify(buildingRepository, times(1)).findById(toSaveDto.getBuildingId());
        verify(floorMapper, times(1)).toEntity(toSaveDto);
        verify(floorRepository).save(floorArgumentCaptor.capture());
        Floor capturedFloor = floorArgumentCaptor.getValue();
        verify(floorMapper, times(1)).toDto(capturedFloor);

        assertThat(capturedFloor.getBuilding().getId()).isEqualTo(buildingId);

    }
}
