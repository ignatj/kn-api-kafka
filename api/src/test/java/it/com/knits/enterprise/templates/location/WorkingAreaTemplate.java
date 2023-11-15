package it.com.knits.enterprise.templates.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knits.enterprise.dto.data.location.WorkingAreaDto;
import io.restassured.response.Response;
import it.com.knits.enterprise.templates.common.EndpointTemplate;
import it.com.knits.enterprise.utils.ItTestConsts;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class WorkingAreaTemplate extends EndpointTemplate {

    public WorkingAreaDto create(String token, WorkingAreaDto expected) {
        Response response = httpPost(token, expected, ItTestConsts.HTTP_CREATED);
        WorkingAreaDto actual = response.getBody().as(WorkingAreaDto.class);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "startDate", "createdBy").isEqualTo(expected);
        return actual;
    }

    public List<WorkingAreaDto> search(String token, String queryString) throws JsonProcessingException {
        Response response = httpGetQueryString(token, queryString, ItTestConsts.HTTP_SUCCESS);
        if (response.getBody().asString().equals("[]")) {
            return new ArrayList<>();
        }
        else {
            TypeReference<List<WorkingAreaDto>> typeRef = new TypeReference<>() {};
            ObjectMapper om = new ObjectMapper();
            return om.readValue(response.getBody().asString(), typeRef);
        }
    }

    @Override
    protected String getEndpoint() {
        return "api/working_areas";
    }
}
