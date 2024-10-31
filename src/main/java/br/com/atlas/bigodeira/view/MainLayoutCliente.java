package br.com.atlas.bigodeira.view;

import br.com.atlas.bigodeira.backend.controller.cliente.ClienteController;
import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.domainBase.domain.ClienteSession;
import br.com.atlas.bigodeira.backend.service.AuthService;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.view.cliente.CadastroClienteView; // Importar as views
import br.com.atlas.bigodeira.view.cliente.HomeViewCliente;
import br.com.atlas.bigodeira.view.cliente.NovoAgendamentoClienteView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Optional;



@PageTitle("Main Layout")
public class MainLayoutCliente extends AppLayout implements RouterLayout {


    private final ClienteService clienteService;
    private final ClienteController clienteController;

    public MainLayoutCliente(ClienteService clienteService, ClienteController clienteController) {
        this.clienteService = clienteService;
        this.clienteController = clienteController;
        Image titleImage = new Image("/logo.png", "Bigodeira");
        titleImage.setWidth("90px");
        titleImage.getStyle()
                .set("position", "absolute")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0");

        HorizontalLayout navigation = getNavigation();
        addToNavbar(titleImage, navigation);
    }

    private HorizontalLayout getNavigation() {
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.addClassNames(LumoUtility.JustifyContent.CENTER,
                LumoUtility.Gap.SMALL, LumoUtility.Height.MEDIUM,
                LumoUtility.Width.FULL);

        navigation.add(createLink("Home", HomeViewCliente.class),
                createLink("Agendar", NovoAgendamentoClienteView.class),
                createLink("Cadastrar", CadastroClienteView.class));

        Button entrarButton;
        if (clienteController.isClienteLogado()) {
            entrarButton = new Button("Sair", e -> {
                ClienteSession.logout();
                clienteController.logout();
                UI.getCurrent().navigate(HomeViewCliente.class);
            });
        } else {

            entrarButton = new Button("Entrar", e -> abrirPopupLogin());
        }

        entrarButton.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontWeight.MEDIUM);
        entrarButton.getStyle().set("text-decoration", "underline");
        entrarButton.getStyle().set("background", "none");
        entrarButton.getStyle().set("border", "none");
        entrarButton.getStyle().set("margin", "0px");
        entrarButton.getStyle().set("padding-left", "16px");
        entrarButton.getStyle().set("padding-right", "16px");


        navigation.add(entrarButton);

        return navigation;
    }


    private RouterLink createLink(String viewName, Class<?> targetClass) {
        RouterLink link = new RouterLink();
        link.add(viewName);

        link.setRoute((Class<? extends Component>) targetClass);

        link.addClassNames(LumoUtility.Display.FLEX,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.TextColor.SECONDARY, LumoUtility.FontWeight.MEDIUM);
        link.getStyle().set("text-decoration", "none");

        return link;
    }

    private void abrirPopupLogin() {
        Dialog loginDialog = new Dialog();
        loginDialog.setWidth("300px");
        loginDialog.setHeight("300px");

        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSpacing(true);
        loginLayout.setPadding(true);
        loginLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        TextField emailField = new TextField("Email");
        emailField.setWidthFull();

        PasswordField passwordField = new PasswordField("Senha");
        passwordField.setWidthFull();

        HorizontalLayout buttonLayout = getHorizontalLayout(emailField, passwordField, loginDialog);
        buttonLayout.setSpacing(true);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        loginLayout.add(emailField, passwordField, buttonLayout);
        loginDialog.add(loginLayout);

        loginDialog.open();
    }

    private HorizontalLayout getHorizontalLayout(TextField emailField, PasswordField passwordField, Dialog loginDialog) {
        Button loginButton = new Button("Entrar", event -> {
            String email = emailField.getValue();
            String senha = passwordField.getValue();

            Optional<Cliente> clienteOptional = clienteService.findByEmail2(email);
            if (clienteOptional.isPresent() && clienteService.autenticar(email, senha)) {
                Cliente cliente = clienteOptional.get();
                ClienteSession.setClienteLogadoId(cliente.getId());
                ClienteSession.setClienteLogado(true);

                AuthService.login();

                Notification.show("Login realizado com sucesso!");

                loginDialog.close();
                UI.getCurrent().getPage().reload();


            } else {
                Notification.show("Email ou senha inválidos.", 3000, Notification.Position.MIDDLE);
            }
        });

        Button cancelButton = new Button("Cancelar", event -> loginDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(loginButton, cancelButton);
        return buttonLayout;
    }


}
