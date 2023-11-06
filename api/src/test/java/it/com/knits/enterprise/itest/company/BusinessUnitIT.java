package it.com.knits.enterprise.itest.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.BusinessUnitDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.mocks.dto.company.BusinessUnitDtoMock;
import com.knits.enterprise.utils.TestConsts;
import it.com.knits.enterprise.itest.GenericIntegrationTest;
import it.com.knits.enterprise.templates.company.BusinessUnitTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class BusinessUnitIT extends GenericIntegrationTest {

    @Autowired
    private BusinessUnitTemplate businessUnitTemplate;

    private static final String MOCK_NAME="MockBusinessUnitNameItest";
    private static final String MOCK_UPDATED_NAME="MockBusinessUnitNameItest-Update";

    private static final String [] NAMES= {"Name1","Name2","Name3"};
    @Test
    public void testCreateBusinessUnitSuccess () throws Exception{
        String token =userTemplate.loginAndGetToken();
        BusinessUnitDto saved =createFlow(token,MOCK_NAME);

        BusinessUnitDto foundById =businessUnitTemplate.findById(token,saved.getId());
        assertThat(foundById).usingRecursiveComparison().isEqualTo(saved);

        businessUnitTemplate.delete(token,foundById.getId());
    }

    @Test
    public void testPartialUpdate()throws Exception{
        String token =userTemplate.loginAndGetToken();
        BusinessUnitDto saved =createFlow(token,MOCK_NAME);

        saved.setName(MOCK_UPDATED_NAME);
        BusinessUnitDto updated =businessUnitTemplate.partialUpdate(token,saved);
        assertThat(updated.getName()).isEqualTo(MOCK_UPDATED_NAME);

        BusinessUnitDto foundById =businessUnitTemplate.findById(token,updated.getId());
        assertThat(foundById).usingRecursiveComparison().isEqualTo(updated);

        businessUnitTemplate.delete(token,foundById.getId());
    }


    @Test
    public void testSearchByUser()throws Exception{

        UserDto loggedUser =userTemplate.login();
        String token =loggedUser.getToken();
        BusinessUnitDto bunit1 =createFlow(token,NAMES[0]);
        BusinessUnitDto bunit2 =createFlow(token,NAMES[1]);
        BusinessUnitDto bunit3 =createFlow(token,NAMES[2]);

        String queryString="createdById=%s&limit=3&page=0".formatted(loggedUser.getId());
        PaginatedResponseDto<BusinessUnitDto> response= businessUnitTemplate.search(token,queryString);
        assertThat(response.getData().size()).isEqualTo(3);

        for (String name : NAMES){
            cleanUpByName(token,name);
        }
    }


    private BusinessUnitDto createFlow(String token, String name) throws Exception{

        BusinessUnitDto expected = BusinessUnitDtoMock.shallowBusinessUnitDto(TestConsts.NO_COUNTER);
        expected.setName(name);
        cleanUpByName(token,expected.getName());
        return businessUnitTemplate.create(token,expected);

    }


    private void cleanUpByName(String token,String name) throws JsonProcessingException {
        PaginatedResponseDto<BusinessUnitDto> response= businessUnitTemplate.search(token,"name="+name);

        if(!response.getData().isEmpty()){
            assertThat(response.getData().size()).isEqualTo(1);
            businessUnitTemplate.delete(token,response.getData().get(0).getId());
        }
    }
}
