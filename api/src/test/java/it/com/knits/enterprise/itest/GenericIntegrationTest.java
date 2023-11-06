package it.com.knits.enterprise.itest;

import it.com.knits.enterprise.config.TestConfiguration;
import it.com.knits.enterprise.templates.security.UserTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public abstract class GenericIntegrationTest {

    @Autowired
    protected UserTemplate userTemplate;
}
