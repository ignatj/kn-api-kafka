package it.com.knits.enterprise.itest.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.mocks.dto.location.LocationDtoMock;
import com.knits.enterprise.utils.TestConsts;
import it.com.knits.enterprise.itest.GenericIntegrationTest;
import it.com.knits.enterprise.templates.location.LocationTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class LocationIT extends GenericIntegrationTest {

    @Autowired
    private LocationTemplate locationTemplate;

    private static final String MOCK_NAME = "MockLocationNameItest";

    @Test
    public void testCreateLocationSuccess() throws Exception {
        String token = userTemplate.loginAndGetToken();
        LocationDto saved = createFlow(token, MOCK_NAME);


        List<LocationDto> paginatedFoundResponseDto = locationTemplate.search(token, saved.getName());
        LocationDto foundByName = paginatedFoundResponseDto.get(0);
        assertThat(foundByName).usingRecursiveComparison().isEqualTo(saved);

        locationTemplate.delete(token, foundByName.getId());
    }

    private LocationDto createFlow(String token, String name) throws Exception {
        LocationDto expected = LocationDtoMock.shallowLocationDto(TestConsts.NO_COUNTER);
        expected.setName(name);
        cleanUpByName(token, expected.getName());
        return locationTemplate.create(token, expected);
    }

    private void cleanUpByName(String token, String name) throws JsonProcessingException {
        List<LocationDto> response = locationTemplate.search(token, name);

        if (!response.isEmpty()) {
            assertThat(response.size()).isEqualTo(1);
            locationTemplate.delete(token, response.get(0).getId());
        }
    }
}
