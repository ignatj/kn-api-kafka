package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.RoomDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.mapper.location.RoomMapper;
import com.knits.enterprise.mocks.dto.location.RoomDtoMock;
import com.knits.enterprise.mocks.dto.security.UserDtoMock;
import com.knits.enterprise.mocks.model.location.FloorMock;
import com.knits.enterprise.mocks.model.location.RoomMock;
import com.knits.enterprise.model.location.Floor;
import com.knits.enterprise.model.location.Room;
import com.knits.enterprise.repository.location.FloorRepository;
import com.knits.enterprise.repository.location.RoomRepository;
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
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private UserService userService;

    @Mock
    private FloorRepository floorRepository;

    @Captor
    private ArgumentCaptor<Room> roomArgumentCaptor;

    @InjectMocks
    private RoomService roomService;

    @Test
    void saveSuccess() {
        Long roomId = 1L;
        Long floorId = 1L;

        RoomDto toSaveDto = RoomDtoMock.shallowRoomDto(roomId, floorId);
        Room room = RoomMock.shallowRoom(roomId);
        Floor floor = FloorMock.shallowFloor(floorId);
        UserDto userDto = UserDtoMock.shallowUserDto(null);

        when(userService.getCurrentUserAsDto()).thenReturn(userDto);
        when(floorRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(floor));
        when(roomMapper.toEntity(Mockito.any(RoomDto.class))).thenReturn(room);
        when(userService.getCurrentUserAsEntity()).thenReturn(null);
        when(roomRepository.save(Mockito.any(Room.class))).thenAnswer(
                invocation -> invocation.getArguments()[0]);
        when(roomMapper.toDto(Mockito.any(Room.class))).thenReturn(toSaveDto);

        roomService.create(toSaveDto);

        verify(floorRepository, times(1)).findById(toSaveDto.getFloorId());
        verify(roomMapper, times(1)).toEntity(toSaveDto);
        verify(roomRepository).save(roomArgumentCaptor.capture());
        Room capturedRoom = roomArgumentCaptor.getValue();
        verify(roomMapper, times(1)).toDto(capturedRoom);

        assertThat(capturedRoom.getFloor().getId()).isEqualTo(floorId);
    }
}
