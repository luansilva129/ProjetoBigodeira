package br.com.atlas.bigodeira.view.agendamentos;

import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Agendamentos")
@Route(value = "agendamentos", layout = MainLayout.class)
public class VisualizarAgendamentosView extends Composite<VerticalLayout> {
    public VisualizarAgendamentosView() {

    }
}
