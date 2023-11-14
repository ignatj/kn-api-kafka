package it.com.knits.enterprise.templates.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knits.enterprise.dto.data.location.FloorDto;
import io.restassured.response.Response;
import it.com.knits.enterprise.templates.common.EndpointTemplate;
import it.com.knits.enterprise.utils.ItTestConsts;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class FloorTemplate extends EndpointTemplate {

    public FloorDto create(String token, FloorDto expected) {
        Response response = httpPost(token, expected, ItTestConsts.HTTP_CREATED);
        FloorDto actual = response.getBody().as(FloorDto.class);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "startDate", "createdBy").isEqualTo(expected);
        return actual;
    }

    public List<FloorDto> search(String token, String queryString) throws JsonProcessingException {
        Response response = httpGetQueryString(token, queryString, ItTestConsts.HTTP_SUCCESS);
        if (response.getBody().asString().equals("[]")) {
            return new ArrayList<>();
        }
        else {
            TypeReference<List<FloorDto>> typeRef = new TypeReference<>() {};
            ObjectMapper om = new ObjectMapper();
            return om.readValue(response.getBody().asString(), typeRef);
        }
    }

    @Override
    protected String getEndpoint() {
        return "api/floors";
    }
}
