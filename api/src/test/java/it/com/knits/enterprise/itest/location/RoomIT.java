package it.com.knits.enterprise.itest.location;

import com.knits.enterprise.dto.data.location.RoomDto;
import com.knits.enterprise.mocks.dto.location.RoomDtoMock;
import com.knits.enterprise.utils.TestConsts;
import it.com.knits.enterprise.itest.GenericIntegrationTest;
import it.com.knits.enterprise.templates.location.RoomTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class RoomIT extends GenericIntegrationTest {

    @Autowired
    private RoomTemplate roomTemplate;

    @Test
    public void testCreateRoomSuccess() throws Exception {
        String token = userTemplate.loginAndGetToken();
        RoomDto saved = createFlow(token);

        List<RoomDto> roomDtos = roomTemplate.search(token, "");
        Optional<RoomDto> roomDtoOptional = roomDtos.stream()
                .filter(roomDto -> Objects.equals(roomDto.getId(), saved.getId()))
                .findFirst();
        assertThat(roomDtoOptional).isNotEmpty();
        RoomDto foundRoomDto = roomDtoOptional.get();
        assertThat(foundRoomDto).usingRecursiveComparison().isEqualTo(saved);

        roomTemplate.delete(token, foundRoomDto.getId());
    }

    private RoomDto createFlow(String token) {
        Long someFloorId = 11L;
        RoomDto expected = RoomDtoMock.shallowRoomDto(TestConsts.NO_COUNTER, someFloorId);
        return roomTemplate.create(token, expected);
    }
}

