package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.mapper.common.AddressMapper;
import com.knits.enterprise.mapper.common.CountryMapper;
import com.knits.enterprise.mapper.location.LocationMapper;
import com.knits.enterprise.mocks.dto.location.LocationDtoMock;
import com.knits.enterprise.mocks.dto.security.UserDtoMock;
import com.knits.enterprise.mocks.model.common.AddressMock;
import com.knits.enterprise.mocks.model.location.LocationMock;
import com.knits.enterprise.mocks.model.security.UserMock;
import com.knits.enterprise.model.common.Address;
import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.repository.common.AddressRepository;
import com.knits.enterprise.repository.common.CountryRepository;
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

    @Mock
    CountryRepository countryRepository;

    @Mock
    LocationMapper locationMapper;

//    @Spy
//    private LocationMapper mapper = new LocationMapperImpl();

    @Mock
    AddressRepository addressRepository;

    @Captor
    private ArgumentCaptor<Location> locationCaptor;

    @InjectMocks
    LocationService service;

    @Test
    @DisplayName("Save Location Success")
    void saveSuccess() {
        LocationDto toSaveDto = LocationDtoMock.shallowLocationDto(null);
        Address toSaveAddress = AddressMock.shallowAddressMock(null);
        Location toSaveLocation = LocationMock.shallowLocation(null);
        UserDto userDto = UserDtoMock.shallowUserDto(null);
        User user = UserMock.shallowUser(null);

        when(userService.getCurrentUserAsDto()).thenReturn(userDto);
        when(countryRepository.findFirstByName(Mockito.any(String.class)))
                .thenReturn(toSaveAddress.getCountry());
        when(locationMapper.toEntity(Mockito.any(LocationDto.class)))
                .thenReturn(toSaveLocation);
        when(userService.getCurrentUserAsEntity()).thenReturn(user);
        when(addressRepository.save(Mockito.any(Address.class)))
                .thenReturn(toSaveAddress);
        when(repository.save(Mockito.any(Location.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);
        when(locationMapper.toDto(Mockito.any(Location.class)))
                .thenReturn(toSaveDto);

        service.create(toSaveDto);

        verify(countryRepository, times(1))
                .findFirstByName(toSaveDto.getAddress().getCountry().getName());
        verify(locationMapper, times(1)).toEntity(toSaveDto);
        verify(userService, times(1)).getCurrentUserAsEntity();
        verify(addressRepository, times(1)).save(toSaveLocation.getAddress());
        verify(repository).save(locationCaptor.capture());
        Location capturedLocation = locationCaptor.getValue();
        verify(locationMapper, times(1)).toDto(capturedLocation);
    }

//    @Test
//    @DisplayName("partial Update success")
//    void partialUpdate (){
//
//        Long entityIdToUpdate = 1L;
//        String updateOnTitleofLocation = "updatedTitleofLocation";
//        Location foundEntity = LocationMock.shallowLocation(entityIdToUpdate);
//        LocationDto toUpdateDto =mapper.toDto(foundEntity);
//        toUpdateDto.setName(updateOnTitleofLocation);
//
//        when(repository.findById(entityIdToUpdate)).thenReturn(Optional.of(foundEntity));
//
//        LocationDto updatedDto =service.partialUpdate(toUpdateDto);
//
//        verify(repository).save(locationCaptor.capture());
//        Location toUpdateEntity = locationCaptor.getValue();
//
//        verify(mapper, times(1)).partialUpdate(toUpdateEntity,toUpdateDto);
//        verify(repository, times(1)).save(foundEntity);
//        verify(mapper, times(2)).toDto(foundEntity);
//
//        assertThat(toUpdateDto).isEqualTo(updatedDto);
//
//    }
//
//    @Test
//    @DisplayName("delete success")
//    void deleteSuccess (){
//
//        Long entityIdToDelete = 1L;
//        Location foundEntity = LocationMock.shallowLocation(entityIdToDelete);
//        LocationDto toDeleteDto =mapper.toDto(foundEntity);
//        when(repository.findById(entityIdToDelete)).thenReturn(Optional.of(foundEntity));
//        service.delete(entityIdToDelete);
//        verify(repository,times(1)).deleteById(entityIdToDelete);
//
//    }
}