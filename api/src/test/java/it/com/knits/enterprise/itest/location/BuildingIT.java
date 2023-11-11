package it.com.knits.enterprise.itest.location;

import com.knits.enterprise.dto.data.location.BuildingDto;
import com.knits.enterprise.mocks.dto.location.BuildingDtoMock;
import com.knits.enterprise.utils.TestConsts;
import it.com.knits.enterprise.itest.GenericIntegrationTest;
import it.com.knits.enterprise.templates.location.BuildingTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class BuildingIT extends GenericIntegrationTest {

    @Autowired
    private BuildingTemplate buildingTemplate;

    @Test
    public void testCreateBuildingSuccess() throws Exception {
        String token = userTemplate.loginAndGetToken();
        BuildingDto saved = createFlow(token);

        List<BuildingDto> buildingDtos = buildingTemplate.searchAll(token);
        Optional<BuildingDto> buildingDtoOptional = buildingDtos.stream()
                .filter(buildingDto -> Objects.equals(buildingDto.getId(), saved.getId()))
                .findFirst();
        assertThat(buildingDtoOptional).isNotEmpty();
        BuildingDto foundBuildingDto = buildingDtoOptional.get();
        assertThat(foundBuildingDto).usingRecursiveComparison().isEqualTo(saved);

        buildingTemplate.delete(token, foundBuildingDto.getId());
    }

    private BuildingDto createFlow(String token) {
        Long someLocationId = 2L;
        BuildingDto expected = BuildingDtoMock.shallowBuildingDto(TestConsts.NO_COUNTER, someLocationId);
        return buildingTemplate.create(token, expected);
    }
}
