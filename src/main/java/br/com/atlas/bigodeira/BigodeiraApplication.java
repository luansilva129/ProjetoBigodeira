package br.com.atlas.bigodeira;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme("bigodeira")
public class
BigodeiraApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(BigodeiraApplication.class, args);
    }

}
