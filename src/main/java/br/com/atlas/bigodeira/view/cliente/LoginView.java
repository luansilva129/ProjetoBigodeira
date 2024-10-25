package br.com.atlas.bigodeira.view.cliente;

import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.view.MainLayout;
import br.com.atlas.bigodeira.view.MainLayoutCliente;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login" , layout = MainLayoutCliente.class)
@PageTitle("Login - Bigodeira")
public class LoginView extends VerticalLayout {

    @Autowired
    ClienteService clienteService;

    @Autowired
    public LoginView(ClienteService clienteService) {
        this.clienteService = clienteService;

        TextField emailField = new TextField("E-mail");
        PasswordField passwordField = new PasswordField("Senha");
        Button loginButton = new Button("Login");

        loginButton.addClickListener(event -> {
            String email = emailField.getValue();
            String senha = passwordField.getValue();

            if (clienteService.autenticar(email, senha)) {
                Notification.show("Login realizado com sucesso!");
                UI.getCurrent().navigate("home-cliente");
            } else {
                Notification.show("Credenciais inválidas!");
            }
        });

        add(emailField, passwordField, loginButton);
    }
}
