package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.BuildingDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.mapper.location.BuildingMapper;
import com.knits.enterprise.mocks.dto.location.BuildingDtoMock;
import com.knits.enterprise.mocks.dto.security.UserDtoMock;
import com.knits.enterprise.mocks.model.location.BuildingMock;
import com.knits.enterprise.mocks.model.location.LocationMock;
import com.knits.enterprise.model.location.Building;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.repository.location.BuildingRepository;
import com.knits.enterprise.repository.location.LocationRepository;
import com.knits.enterprise.service.security.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BuildingServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private BuildingMapper buildingMapper;

    @Mock
    private UserService userService;

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private BuildingService buildingService;

    @Captor
    private ArgumentCaptor<Building> buildingArgumentCaptor;

    @Test
    void saveSuccess() {
        Long buildingId = 1L;
        Long locationId = 1L;

        BuildingDto toSaveDto = BuildingDtoMock.shallowBuildingDto(buildingId, locationId);
        Location location = LocationMock.shallowLocation(locationId);
        Building building = BuildingMock.shallowBuilding(null);
        UserDto userDto = UserDtoMock.shallowUserDto(null);

        when(userService.getCurrentUserAsDto()).thenReturn(userDto);
        when(locationRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(location));
        when(buildingMapper.toEntity(Mockito.any(BuildingDto.class))).thenReturn(building);
        when(buildingRepository.save(Mockito.any(Building.class))).thenAnswer(
                invocation -> invocation.getArguments()[0]);
        when(buildingMapper.toDto(Mockito.any(Building.class))).thenReturn(toSaveDto);

        buildingService.create(toSaveDto);

        verify(locationRepository, times(1)).findById(toSaveDto.getLocationId());
        verify(buildingMapper, times(1)).toEntity(toSaveDto);
        verify(buildingRepository).save(buildingArgumentCaptor.capture());
        Building capturedBuilding = buildingArgumentCaptor.getValue();
        verify(buildingMapper, times(1)).toDto(capturedBuilding);

        assertThat(capturedBuilding.getLocation().getId()).isEqualTo(locationId);
    }
}
