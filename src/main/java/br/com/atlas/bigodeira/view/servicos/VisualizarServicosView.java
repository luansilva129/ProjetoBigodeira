package br.com.atlas.bigodeira.view.servicos;

import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Visualizar Serviços")
@Route(value = "servicos", layout = MainLayout.class)
public class VisualizarServicosView extends Composite<VerticalLayout> {
    public VisualizarServicosView() {

    }
}
