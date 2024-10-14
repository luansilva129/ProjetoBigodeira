package br.com.atlas.bigodeira.view.home;

import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Home / Dashboard")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "home", layout = MainLayout.class)
public class HomeView extends Composite<VerticalLayout> {
    public HomeView() {

    }
}
