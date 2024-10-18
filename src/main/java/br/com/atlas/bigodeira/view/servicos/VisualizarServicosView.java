package br.com.atlas.bigodeira.view.servicos;

import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import br.com.atlas.bigodeira.backend.service.ServicosService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Visualizar Serviços")
@Route(value = "servicos", layout = MainLayout.class)
public class VisualizarServicosView extends VerticalLayout {

    private final Grid<ServicosBase> grid;
    private final ServicosService servicosService;

    @Autowired
    public VisualizarServicosView(ServicosService servicosService) {
        this.servicosService = servicosService;

        HorizontalLayout headerLayout = new HorizontalLayout();

        headerLayout.add(new H2("Serviços"));
        headerLayout.setWidthFull();

        Button novoServicoButton = new Button("Criar Serviço");
        novoServicoButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("novo-servico"));
        });
        novoServicoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        headerLayout.add(novoServicoButton);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Pesquisar Serviço");
        searchField.setSuffixComponent(VaadinIcon.SEARCH.create());
        searchField.addValueChangeListener(event -> filterGrid(event.getValue()));

        HorizontalLayout searchLayout = new HorizontalLayout(searchField);
        searchLayout.setWidthFull();
        searchLayout.addClassName(LumoUtility.JustifyContent.END);
        headerLayout.add(searchLayout);

        add(headerLayout);

        grid = new Grid<>(ServicosBase.class, false);
        loadServicos();

        add(grid);
    }

    private void loadServicos() {
        List<ServicosBase> servicos = servicosService.findAll();
        grid.setItems(servicos);

        grid.addColumn(ServicosBase::getNome).setHeader("Serviços");
        grid.addColumn(ServicosBase::getDescricao).setHeader("Descrição");
        grid.addColumn(ServicosBase::getDuracao).setHeader("Duração");
        grid.addColumn(new NumberRenderer<>(
                ServicosBase::getPreco, NumberFormat.getCurrencyInstance()
        )).setHeader("Preço");

        grid.addComponentColumn(servicosBase ->{
            Icon lixeira = new Icon(VaadinIcon.TRASH);
            lixeira.setColor("red");
            Button abrirDialog = new Button(lixeira, event -> openDialog(servicosBase));
            return abrirDialog;
        });
    }

    private void openDialog (ServicosBase servicosBase) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Excluir serviço "+servicosBase.getNome()+"?");

        dialog.add("O serviço será excluido permanentemente, tem certeza que deseja continuar?");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();

        Button excluir = new Button("Excluir", event -> {
            try {
                Long id = servicosBase.getId();
                servicosService.delete(id);
                refreshGrid(grid);
                dialog.close();
                Notification.show("Serviço excluido com sucesso!");
            } catch (Exception e) {
                Notification.show("Serviço vinculado à um agendamento, portanto não pode ser excluido!");
            }
        });
        excluir.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        Button cancelar = new Button("Cancelar", event -> dialog.close());
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        buttonLayout.add(excluir, cancelar);

        dialog.getFooter().add(buttonLayout);

        dialog.open();
    }

    private void filterGrid(String searchTerm) {
        List<ServicosBase> filteredList = servicosService.findAll().stream()
                .filter(servicosBase -> servicosBase.getNome().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
        grid.setItems(filteredList);
    }

    //Tamo junto Giovani
    private void refreshGrid(Grid<ServicosBase> grid) { grid.setItems(servicosService.findAll());
    }
}