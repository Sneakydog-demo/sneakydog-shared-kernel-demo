package dog.sneaky.demo.sharedkernel.jackson;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class DefaultObjectMapperConfiguration {

    @Bean
    public DefaultObjectMapper objectMapper() {
        return new DefaultObjectMapper();
    }
}
