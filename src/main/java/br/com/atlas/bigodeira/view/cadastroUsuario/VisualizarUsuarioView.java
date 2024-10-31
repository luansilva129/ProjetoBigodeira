package br.com.atlas.bigodeira.view.cadastroUsuario;

import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
            Button abrirEditar = new Button(lapis);
            abrirEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Icon lixeira = new Icon(VaadinIcon.TRASH);
            lixeira.setColor("red");
            Button abrirExcluir = new Button(lixeira);
            abrirExcluir.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            HorizontalLayout buttonsLayout = new HorizontalLayout(abrirEditar, abrirExcluir);
            buttonsLayout.setJustifyContentMode(JustifyContentMode.END);

            return buttonsLayout;
        });
    }

    private void refreshGrid(Grid<Cliente> grid) {
        grid.setItems(clienteService.findAll());
    }
}
