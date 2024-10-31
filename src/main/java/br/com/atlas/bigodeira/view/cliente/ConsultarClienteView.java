package br.com.atlas.bigodeira.view.cliente;

import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.view.MainLayoutCliente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "cliente/consultarView", layout = MainLayoutCliente.class)
@PageTitle("Consultar Clientes")
public class ConsultarClienteView extends VerticalLayout {

    private final ClienteService clienteService;
    private Grid<Cliente> grid;

    @Autowired
    public ConsultarClienteView(ClienteService clienteService) {
        this.clienteService = clienteService;
        this.grid = new Grid<>(Cliente.class);

        configurarGrid();
        adicionarFiltros();
        adicionarButtons();
        listarClientes();
    }

    private void configurarGrid() {
        grid.setColumns("nome", "email", "telefone");
        grid.addComponentColumn(cliente -> {
            Button editButton = new Button("Editar", event -> editarCliente(cliente));
            Button deleteButton = new Button("Excluir", event -> excluirCliente(cliente));
            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Ações");
        grid.setWidth("100%");
        add(grid);
    }

    private void adicionarFiltros() {
        TextField filterText = new TextField();
        filterText.setPlaceholder("Filtrar por nome...");
        filterText.addValueChangeListener(e -> listarClientes(filterText.getValue()));
        add(filterText);
    }

    private void adicionarButtons() {
        Button refreshButton = new Button("Atualizar", event -> listarClientes());
        add(refreshButton);
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteService.findAll();
        grid.setItems(clientes);
    }

    private void listarClientes(String filter) {
        List<Cliente> clientes = clienteService.findAll();
        grid.setItems((Cliente) clientes.stream()
                .filter(cliente -> cliente.getNome().toLowerCase().contains(filter.toLowerCase())));
    }

    private void editarCliente(Cliente cliente) {
        // Implementar lógica para editar o cliente
        Notification.show("Editar cliente: " + cliente.getNome());
    }

    private void excluirCliente(Cliente cliente) {
        clienteService.delete(cliente); // Adicione um método de exclusão no ClienteService
        Notification.show("Cliente excluído: " + cliente.getNome());
        listarClientes(); // Atualiza a lista após exclusão
    }
}
