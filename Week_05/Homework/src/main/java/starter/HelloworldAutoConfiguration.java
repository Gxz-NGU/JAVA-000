package starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({HelloworldService.class})
@EnableConfigurationProperties(HelloworldPropertities.class)
public class    HelloworldAutoConfiguration {
    @Autowired
    private HelloworldPropertities helloworldPropertities;

    @Bean
    @ConditionalOnMissingBean(HelloworldService.class)
    public HelloworldService helloworldService() {
        HelloworldService helloworldService = new HelloworldService();
        helloworldService.setWords(helloworldPropertities.getWords());
        return helloworldService;
    }
}
