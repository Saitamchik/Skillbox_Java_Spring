package com.example.basicsSpring.config;

import com.example.basicsSpring.*;
import org.springframework.context.annotation.*;

@Profile("init")
@PropertySource("classpath:application.properties")
@Configuration
public class InitAppConfig {

    @Profile("init")
    @Bean
    public ContactsApp contactsApp() {
        return new ContactsAppDefault();
    }

    @Profile("init")
    @Bean
    public InitialData initialData() {
        return new InitialDataImpl();
    }
}

