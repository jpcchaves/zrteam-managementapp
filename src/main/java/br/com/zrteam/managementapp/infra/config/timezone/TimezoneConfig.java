package br.com.zrteam.managementapp.infra.config.timezone;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimezoneConfig {
    @Value("${app.timezone}")
    private String timezone;

    @PostConstruct
    public void timezone() {
         TimeZone.setDefault(TimeZone.getTimeZone(timezone));
    }
}
