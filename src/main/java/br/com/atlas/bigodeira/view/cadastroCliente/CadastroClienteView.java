package br.com.atlas.bigodeira.view.cadastroCliente;

import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cadastro Cliente")
@Route(value = "cadastro-cliente", layout = MainLayout.class)
public class CadastroClienteView extends Composite<VerticalLayout> {

    private final ClienteService clienteService;

    public CadastroClienteView(ClienteService clienteService) {
        this.clienteService = clienteService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h2 = new H2();

        FormLayout formLayout2Col = new FormLayout();
        TextField nomeField = new TextField();
        HorizontalLayout telefoneLayout = new HorizontalLayout();
        ComboBox<String> estadoTelefone = new ComboBox<>();
        TextField numeroTelefone = new TextField();
        EmailField emailField = new EmailField();

        Button buttonPrimary = new Button();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.START);

        layoutColumn2.setWidth("100%");
        layoutColumn2.setHeight("min-content");

        h2.setText("Insira as Informações");
        h2.setWidth("100%");

        telefoneLayout.setAlignItems(Alignment.START);
        telefoneLayout.setWidthFull();

        formLayout2Col.setWidth("100%");
        formLayout2Col.setColspan(nomeField, 2);
        formLayout2Col.setColspan(numeroTelefone, 2);
        formLayout2Col.setColspan(emailField, 2);

        nomeField.setLabel("Nome");

        emailField.setLabel("Email");
        emailField.setErrorMessage("Insira um e-mail válido");

        estadoTelefone.setLabel("Estado");
        estadoTelefone.setItems("(84)", "(11)", "(12)", "(13)", "(14)",
                "(15)", "(16)", "(17)", "(18)", "(19)");
        estadoTelefone.setValue("(84)");
        estadoTelefone.setWidth("5%");
        estadoTelefone.setMinWidth("80px");
        estadoTelefone.setAllowedCharPattern("[()0-9]");

        numeroTelefone.setLabel("Telefone");
        numeroTelefone.setAllowedCharPattern("[0-9()+-]");
        numeroTelefone.setWidth("100%");
        numeroTelefone.setMaxLength(10);

        buttonPrimary.setText("Cadastrar");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonPrimary.addClickListener(event -> {
            Cliente cliente = new Cliente();
            cliente.setNome(nomeField.getValue());
            cliente.setEmail(emailField.getValue());
            cliente.setTelefone(estadoTelefone.getValue()+" "+numeroTelefone.getValue());

            clienteService.save(cliente);

            Notification.show("Cliente cadastrado com sucesso!");

            nomeField.clear();
            emailField.clear();
            estadoTelefone.setValue("(84)");
            numeroTelefone.clear();
        });

        telefoneLayout.add(estadoTelefone, numeroTelefone);

        formLayout2Col.add(nomeField, emailField);

        layoutColumn2.add(h2, formLayout2Col, telefoneLayout, buttonPrimary);

        getContent().add(layoutColumn2);
    }
}