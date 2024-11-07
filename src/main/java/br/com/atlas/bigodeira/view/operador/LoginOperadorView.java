package br.com.atlas.bigodeira.view.operador;

import br.com.atlas.bigodeira.backend.controller.operador.OperadorController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class LoginOperadorView extends VerticalLayout {

    private final OperadorController operadorController;

    public LoginOperadorView(OperadorController operadorController) {
        this.operadorController = operadorController;

        H1 titulo = new H1("Login do Operador");

        TextField emailField = new TextField("E-mail");
        emailField.setWidthFull();

        PasswordField senhaField = new PasswordField("Senha");
        senhaField.setWidthFull();

        Button loginButton = new Button("Entrar", event -> {
            String email = emailField.getValue();
            String senha = senhaField.getValue();

            boolean autenticado = operadorController.autenticarOperador(email, senha);
            if (autenticado) {
                Notification.show("Login realizado com sucesso!");

                getUI().ifPresent(ui -> ui.navigate("dashboard"));
            } else {
                Notification.show("E-mail ou senha incorretos.", 3000, Notification.Position.MIDDLE);
            }
        });

        VerticalLayout layout = new VerticalLayout(titulo, emailField, senhaField, loginButton);
        layout.setAlignItems(Alignment.CENTER);
        layout.setWidth("300px");

        add(layout);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();
    }
}