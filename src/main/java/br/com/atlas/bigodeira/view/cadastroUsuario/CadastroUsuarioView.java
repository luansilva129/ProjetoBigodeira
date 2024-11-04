package br.com.atlas.bigodeira.view.cadastroUsuario;

import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cadastro Cliente")
@Route(value = "cadastro-cliente", layout = MainLayout.class)
public class CadastroUsuarioView extends VerticalLayout {

    private final ClienteService clienteService;

    public CadastroUsuarioView(ClienteService clienteService) {
        this.clienteService = clienteService;

        H2 titulo = new H2("Insira as Informações");

        TextField nomeField = new TextField("Nome");
        EmailField emailField = new EmailField("Email");

        ComboBox<String> estadoTelefone = new ComboBox<>("Estado");
        TextField numeroTelefone = new TextField("Telefone");
        HorizontalLayout telefoneLayout = new HorizontalLayout(estadoTelefone, numeroTelefone);

        Button cadastrarButton = new Button("Cadastrar");

        VerticalLayout verticalLayout = new VerticalLayout(titulo, nomeField, emailField, telefoneLayout, cadastrarButton);

        nomeField.setWidthFull();

        emailField.setWidthFull();
        emailField.setErrorMessage("Insira um e-mail válido");

        telefoneLayout.setWidthFull();

        estadoTelefone.setItems("(84)", "(11)", "(12)", "(13)", "(14)",
                "(15)", "(16)", "(17)", "(18)", "(19)");
        estadoTelefone.setValue("(84)");
        estadoTelefone.setWidth("5%");
        estadoTelefone.setMinWidth("80px");
        estadoTelefone.setAllowedCharPattern("[()0-9]");

        numeroTelefone.setAllowedCharPattern("[0-9()+-]");
        numeroTelefone.setWidth("100%");
        numeroTelefone.setMaxLength(10);

        cadastrarButton.setWidth("min-content");
        cadastrarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cadastrarButton.getStyle().setMargin("20px 0px 4px");

        cadastrarButton.addClickListener(event -> {
            String nome = nomeField.getValue();
            String email = emailField.getValue();
            String telefone  = estadoTelefone.getValue()+" "+numeroTelefone.getValue();

            if (nome.isEmpty() || email.isEmpty() || numeroTelefone.isEmpty()) {
                Notification.show("Preencha todos os campos antes de continuar");
            } else if (emailField.isInvalid()) {
                Notification.show("E-mail inválido");
            } else {
                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setEmail(email);
                cliente.setTelefone(telefone);

                clienteService.save(cliente);

                Notification.show("Cliente cadastrado com sucesso!");

                nomeField.clear();
                emailField.clear();
                estadoTelefone.setValue("(84)");
                numeroTelefone.clear();
            }
        });

        add(verticalLayout);
    }
}