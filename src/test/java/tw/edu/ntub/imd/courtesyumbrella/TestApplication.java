package tw.edu.ntub.imd.courtesyumbrella;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import tw.edu.ntub.imd.config.Config;
import tw.edu.ntub.imd.courtesyumbrella.util.http.ResponseUtils;

@SpringBootApplication
@Import(Config.class)
public class TestApplication {
    @Bean
    public ObjectMapper objectMapper() {
        return ResponseUtils.createMapper();
    }
}
