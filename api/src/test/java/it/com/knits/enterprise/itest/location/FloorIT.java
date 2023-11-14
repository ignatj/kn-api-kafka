package it.com.knits.enterprise.itest.location;

import com.knits.enterprise.dto.data.location.FloorDto;
import com.knits.enterprise.mocks.dto.location.FloorDtoMock;
import com.knits.enterprise.utils.TestConsts;
import it.com.knits.enterprise.itest.GenericIntegrationTest;
import it.com.knits.enterprise.templates.location.FloorTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class FloorIT extends GenericIntegrationTest {

    @Autowired
    private FloorTemplate floorTemplate;

    @Test
    public void testCreateFloorSuccess() throws Exception {
        String token = userTemplate.loginAndGetToken();
        FloorDto saved = createFlow(token);

        List<FloorDto> floorDtos = floorTemplate.search(token, "");
        Optional<FloorDto> floorDtoOptional = floorDtos.stream()
                .filter(floorDto -> Objects.equals(floorDto.getId(), saved.getId()))
                .findFirst();
        assertThat(floorDtoOptional).isNotEmpty();
        FloorDto foundFloorDto = floorDtoOptional.get();
        assertThat(foundFloorDto).usingRecursiveComparison().isEqualTo(saved);

        floorTemplate.delete(token, foundFloorDto.getId());
    }

    private FloorDto createFlow(String token) {
        Long buildingId = 11L;
        FloorDto expected = FloorDtoMock.shallowFloorDto(TestConsts.NO_COUNTER, buildingId);
        return floorTemplate.create(token, expected);
    }
}
