package com.example.basicsSpring.config;

import com.example.basicsSpring.*;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.example.basicsSpring")
@PropertySource("classpath:application.properties")
public class DefaultAppConfig {

    @Profile("default")
    @Bean
    public ContactsApp contactsApp() {
        return new ContactsAppDefault();
    }

    @Profile("default")
    @Bean
    public InitialData initialData() {
        return new InitialDataDefaultImpl();
    }
}
