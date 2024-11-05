package br.com.atlas.bigodeira.view.cliente;

import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.view.MainLayoutCliente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "cliente/cadastroView", layout = MainLayoutCliente.class)
@PageTitle("Cadastro - Cliente")
public class CadastroClienteView extends VerticalLayout {

    private ClienteService clienteService;
    private TextField nomeField;
    private TextField emailField;
    private PasswordField senhaField;
    private TextField telefoneField;
    private Button cadastrarButton;

    @Autowired
    public CadastroClienteView(ClienteService clienteService) {
        this.clienteService = clienteService;
        criarFormulario();

        setSpacing(true);
        setPadding(true);
        setWidthFull();
        setDefaultHorizontalComponentAlignment(Alignment.START);
    }


    private void criarFormulario() {
        nomeField = new TextField("Nome");
        emailField = new TextField("Email");
        senhaField = new PasswordField("Senha");
        telefoneField = new TextField("Telefone");
        telefoneField.setPlaceholder("(xx)xxxxx-xxxx");

        telefoneField.addValueChangeListener(event -> {
            String formattedValue = clienteService.formatarTelefone(event.getValue());
            telefoneField.setValue(formattedValue);
        });

        cadastrarButton = new Button("Cadastrar", event -> cadastrarCliente());
        cadastrarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout(nomeField, emailField, senhaField, telefoneField);
        formLayout.setColspan(nomeField, 2);
        formLayout.setColspan(emailField, 2);
        formLayout.setColspan(senhaField, 2);
        formLayout.setColspan(telefoneField, 2);

        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2("Insira as informações"), formLayout, cadastrarButton);
        layout.setPadding(true);

        add(layout);
    }


    private void cadastrarCliente() {
        String nome = nomeField.getValue();
        String email = emailField.getValue();
        String senha = senhaField.getValue();
        String telefone = telefoneField.getValue();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || telefone.isEmpty()) {
            Notification.show("Por favor, preencha todos os campos.", 3000, Notification.Position.MIDDLE);
            return;
        }
        if (clienteService.emailExiste(email)) {
            Notification.show("Um cliente com este email já existe.", 3000, Notification.Position.MIDDLE);
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSenha(senha);

        clienteService.save(cliente);

        Notification.show("Cliente cadastrado com sucesso!", 3000, Notification.Position.MIDDLE);
        nomeField.clear();
        emailField.clear();
        senhaField.clear();
        telefoneField.clear();
    }
}
