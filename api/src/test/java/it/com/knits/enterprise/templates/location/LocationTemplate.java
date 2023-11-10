package it.com.knits.enterprise.templates.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.dto.search.location.LocationSearchDto;
import io.restassured.response.Response;
import it.com.knits.enterprise.templates.common.EndpointTemplate;
import it.com.knits.enterprise.utils.ItTestConsts;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class LocationTemplate extends EndpointTemplate {

    public LocationDto create(String token, LocationDto expected) {
        Response response = httpPost(token, expected, ItTestConsts.HTTP_CREATED);
        LocationDto actual = response.getBody().as(LocationDto.class);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "address.country").isEqualTo(expected);
        return actual;
    }

    public List<LocationDto> search(String token, String name) throws JsonProcessingException {
        LocationSearchDto searchDto = LocationSearchDto.builder().name(name).build();
        Response response = httpGetQueryBody(token, searchDto, ItTestConsts.HTTP_SUCCESS);
        if (response.getBody().asString().equals("[]")) {
            return new ArrayList<>();
        }
        else {
            TypeReference<List<LocationDto>> typeRef = new TypeReference<>() {};
            ObjectMapper om = new ObjectMapper();
            return om.readValue(response.getBody().asString(), typeRef);
        }
    }

    @Override
    protected String getEndpoint() {
        return "api/locations";
    }
}
