package it.com.knits.enterprise.templates.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knits.enterprise.dto.data.location.BuildingDto;
import io.restassured.response.Response;
import it.com.knits.enterprise.templates.common.EndpointTemplate;
import it.com.knits.enterprise.utils.ItTestConsts;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class BuildingTemplate extends EndpointTemplate {

    public BuildingDto create(String token, BuildingDto expected) {
        Response response = httpPost(token, expected, ItTestConsts.HTTP_CREATED);
        BuildingDto actual = response.getBody().as(BuildingDto.class);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expected);
        return actual;
    }

    public List<BuildingDto> searchAll(String token) throws JsonProcessingException {
        Response response = httpGet(token, ItTestConsts.HTTP_SUCCESS);
        if (response.getBody().asString().equals("[]")) {
            return new ArrayList<>();
        }
        else {
            TypeReference<List<BuildingDto>> typeRef = new TypeReference<>() {};
            ObjectMapper om = new ObjectMapper();
            return om.readValue(response.getBody().asString(), typeRef);
        }
    }

    @Override
    protected String getEndpoint() {
        return "api/buildings";
    }
}
