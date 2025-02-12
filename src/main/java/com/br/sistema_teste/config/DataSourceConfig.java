package com.br.sistema_teste.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private final Dotenv dotenv = Dotenv.load();


    @Bean
    public DataSource dataSource() {
        String username = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");
        String url = dotenv.get("DB_URL");

        // Adicione logs para verificar as variáveis
        System.out.println("DB_USERNAME: " + username);
        System.out.println("DB_PASSWORD: " + password);
        System.out.println("DB_URL: " + url);

        return DataSourceBuilder.create()
                .username(username)
                .password(password)
                .url(url)
                .build();
    }
}
