package br.com.atlas.bigodeira.view.cadastroUsuario;

import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Clientes")
@Route(value = "visualizar-clientes", layout = MainLayout.class)
public class VisualizarUsuarioView extends VerticalLayout {

    private final Grid<Cliente> grid;
    private final ClienteService clienteService;

    public VisualizarUsuarioView(ClienteService clienteService) {
        this.clienteService = clienteService;

        H2 titulo = new H2("Visualizar Clientes");

        Button novoServicoButton = new Button("Cadastrar Cliente");
        novoServicoButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("cadastro-cliente"));
        });
        novoServicoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Pesquisar");
        searchField.setSuffixComponent(VaadinIcon.SEARCH.create());

        HorizontalLayout headerLayout = new HorizontalLayout(titulo, novoServicoButton, searchField);

        grid = new Grid<>(Cliente.class, false);
        loadCliente();
        setHeight("80%");

        add(headerLayout, grid);
    }

    private void loadCliente() {
        List<Cliente> clientes = clienteService.findAll();
        grid.setItems(clientes);

        grid.addColumn(Cliente::getNome).setHeader("Nome").setSortable(true);
        grid.addColumn(Cliente::getEmail).setHeader("E-mail");
        grid.addColumn(Cliente::getTelefone).setHeader("Telefone");

        grid.addComponentColumn(cliente ->{
            Icon lapis = new Icon(VaadinIcon.PENCIL);
            lapis.setColor("orange");
            Button abrirEditar = new Button(lapis, event -> openDialogEditar(cliente));
            abrirEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Icon lixeira = new Icon(VaadinIcon.TRASH);
            lixeira.setColor("red");
            Button abrirExcluir = new Button(lixeira, event -> openDialogExcluir(cliente));
            abrirExcluir.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            HorizontalLayout buttonsLayout = new HorizontalLayout(abrirEditar, abrirExcluir);
            buttonsLayout.setJustifyContentMode(JustifyContentMode.END);

            return buttonsLayout;
        });
    }

    private void openDialogEditar(Cliente cliente) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Editar Cliente "+cliente.getNome());

        TextField nomeField = new TextField("Nome");
        nomeField.setValue(cliente.getNome());

        EmailField emailField = new EmailField("E-mail");
        emailField.setValue(cliente.getEmail());
        emailField.setEnabled(false);

        TextField telefoneField = new TextField("Telefone");
        telefoneField.setValue(cliente.getTelefone());

        FormLayout formLayout = new FormLayout(nomeField, emailField, telefoneField);
        formLayout.setColspan(nomeField,2);
        formLayout.setColspan(emailField, 2);
        formLayout.setColspan(telefoneField, 2);

        dialog.add(formLayout);

        Button salvar = new Button("Salvar", event -> {
            if (nomeField.isEmpty() ||  telefoneField.isEmpty()) {
                Notification.show("Preencha todos os campos antes de continuar");
            } else {
                Cliente novoCliente = new Cliente();
                novoCliente.setId(cliente.getId());
                novoCliente.setNome(nomeField.getValue());
                novoCliente.setEmail(emailField.getValue());
                novoCliente.setTelefone(telefoneField.getValue());

                clienteService.save(novoCliente);

                refreshGrid(grid);
                dialog.close();

                Notification.show("Cliente editado com sucesso!");
            }
        });
        salvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvar.getStyle().set("margin-right", "auto");

        Button cancelar = new Button("Cancelar", event -> dialog.close());
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(salvar, cancelar);

        dialog.open();
    }

    private void openDialogExcluir(Cliente cliente) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Excluir cliente "+cliente.getNome()+"?");

        dialog.add("O cliente será excluido permanentemente, tem certeza que deseja continuar?");

        Button excluir = new Button("Excluir", event -> {
            try {
                clienteService.delete(cliente);
                refreshGrid(grid);
                dialog.close();
                Notification.show("Cliente excluido com sucesso!");

            } catch (Exception e) {
                Notification.show("Serviço vinculado à um agendamento, portanto não pode ser excluido!");
            }
        });
        excluir.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        excluir.getStyle().set("margin-right", "auto");

        Button cancelar = new Button("Cancelar", event -> dialog.close());
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(excluir, cancelar);

        dialog.open();
    }

    private void refreshGrid(Grid<Cliente> grid) {
        grid.setItems(clienteService.findAll());
    }
}
