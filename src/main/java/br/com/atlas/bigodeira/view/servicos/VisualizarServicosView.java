package br.com.atlas.bigodeira.view.servicos;

import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Visualizar Serviços")
@Route(value = "servicos", layout = MainLayout.class)
public class VisualizarServicosView extends VerticalLayout {
    public VisualizarServicosView() {
        HorizontalLayout headerLayout = new HorizontalLayout();

        headerLayout.add(new H2("Serviços"));

        Button novoServicoButton = new Button("Criar Serviço");
        novoServicoButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("novo-servico"));
        });
        novoServicoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        headerLayout.add(novoServicoButton);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Pesquisar por nome");
        searchField.getStyle().set("align-self", "flex-end");
        headerLayout.add(searchField);

        add(headerLayout);
    }
}
