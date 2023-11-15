package it.com.knits.enterprise.itest.location;

import com.knits.enterprise.dto.data.location.WorkingAreaDto;
import com.knits.enterprise.mocks.dto.location.WorkingAreaDtoMock;
import com.knits.enterprise.utils.TestConsts;
import it.com.knits.enterprise.itest.GenericIntegrationTest;
import it.com.knits.enterprise.templates.location.WorkingAreaTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class WorkingAreaIT extends GenericIntegrationTest {

    @Autowired
    private WorkingAreaTemplate workingAreaTemplate;

    @Test
    public void testCreateWorkingAreaSuccess() throws Exception {
        String token = userTemplate.loginAndGetToken();
        WorkingAreaDto saved = createFlow(token);

        List<WorkingAreaDto> workingAreaDtos = workingAreaTemplate.search(token, "");
        Optional<WorkingAreaDto> workingAreaDtoOptional = workingAreaDtos.stream()
                .filter(workingAreaDto -> Objects.equals(workingAreaDto.getId(), saved.getId()))
                .findFirst();
        assertThat(workingAreaDtoOptional).isNotEmpty();
        WorkingAreaDto foundWorkingAreaDto = workingAreaDtoOptional.get();
        assertThat(foundWorkingAreaDto).usingRecursiveComparison().isEqualTo(saved);

        workingAreaTemplate.delete(token, foundWorkingAreaDto.getId());
    }

    private WorkingAreaDto createFlow(String token) {
        Long someFloorId = 11L;
        WorkingAreaDto expected = WorkingAreaDtoMock.shallowWorkingAreaDto(TestConsts.NO_COUNTER, someFloorId);
        return workingAreaTemplate.create(token, expected);
    }
}
