package br.com.atlas.bigodeira.view.acessos;

import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Consulta Acessos")
@Route(value = "acessos", layout = MainLayout.class)
public class AcessosView extends Composite<VerticalLayout> {
    public AcessosView() {

    }
}
