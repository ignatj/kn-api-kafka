package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.mapper.common.AddressMapper;
import com.knits.enterprise.mapper.common.AddressMapperImpl;
import com.knits.enterprise.mapper.common.CountryMapper;
import com.knits.enterprise.mapper.common.CountryMapperImpl;
import com.knits.enterprise.mapper.location.LocationMapper;
import com.knits.enterprise.mapper.location.LocationMapperImpl;
import com.knits.enterprise.mocks.dto.location.LocationDtoMock;
import com.knits.enterprise.mocks.dto.security.UserDtoMock;
import com.knits.enterprise.mocks.model.location.LocationMock;
import com.knits.enterprise.mocks.model.security.UserMock;
import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.repository.location.LocationRepository;
import com.knits.enterprise.service.security.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LocationServiceTest {

    @Mock
    LocationRepository repository;

    @Mock
    UserService userService;

    @Spy
    private LocationMapper mapper = new LocationMapperImpl();

    @Captor
    private ArgumentCaptor<Location> locationCaptor;

    @InjectMocks
    LocationService service;

    @BeforeEach
    private void iniMapperDependencies(){
        AddressMapper addressMapper = new AddressMapperImpl();
        CountryMapper countryMapper = new CountryMapperImpl();

        ReflectionTestUtils.setField(addressMapper,"countryMapper",countryMapper);
        ReflectionTestUtils.setField(mapper,"addressMapper",addressMapper);
    }

    @Test
    @DisplayName("Save Location Success")
    void saveSuccess() {

        final Long mockId=1L;
        final Long locationGeneratedId=1L;
        LocationDto toSaveDto = LocationDtoMock.shallowLocationDto(null);

        when(repository.save(Mockito.any(Location.class)))
                .thenAnswer(invocation -> {
                    Location location = (Location)invocation.getArgument(0);
                    location.setId(locationGeneratedId);
                    return location;
                });

        when(userService.getCurrentUserAsDto()).thenReturn(UserDtoMock.shallowUserDto(1L));

        LocationDto savedDto= service.create(toSaveDto);

        verify(repository).save(locationCaptor.capture());
        Location toSaveEntity = locationCaptor.getValue();

        verify(mapper, times(1)).toEntity(toSaveDto);
        verify(userService, times(1)).getCurrentUserAsEntity();
        verify(repository, times(1)).save(toSaveEntity);
        verify(mapper, times(1)).toDto(toSaveEntity);

        assertThat(toSaveDto.getName()).isEqualTo(savedDto.getName());

    }

    @Test
    @DisplayName("partial Update success")
    void partialUpdate (){

        Long entityIdToUpdate = 1L;
        String updateOnTitleofLocation = "updatedTitleofLocation";
        Location foundEntity = LocationMock.shallowLocation(entityIdToUpdate);
        LocationDto toUpdateDto =mapper.toDto(foundEntity);
        toUpdateDto.setName(updateOnTitleofLocation);

        when(repository.findById(entityIdToUpdate)).thenReturn(Optional.of(foundEntity));

        LocationDto updatedDto =service.partialUpdate(toUpdateDto);

        verify(repository).save(locationCaptor.capture());
        Location toUpdateEntity = locationCaptor.getValue();

        verify(mapper, times(1)).partialUpdate(toUpdateEntity,toUpdateDto);
        verify(repository, times(1)).save(foundEntity);
        verify(mapper, times(2)).toDto(foundEntity);

        assertThat(toUpdateDto).isEqualTo(updatedDto);

    }

    @Test
    @DisplayName("delete success")
    void deleteSuccess (){

        Long entityIdToDelete = 1L;
        Location foundEntity = LocationMock.shallowLocation(entityIdToDelete);
        LocationDto toDeleteDto =mapper.toDto(foundEntity);
        when(repository.findById(entityIdToDelete)).thenReturn(Optional.of(foundEntity));
        service.delete(entityIdToDelete);
        verify(repository,times(1)).deleteById(entityIdToDelete);

    }
}