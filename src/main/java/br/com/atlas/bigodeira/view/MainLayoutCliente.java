package br.com.atlas.bigodeira.view;

import br.com.atlas.bigodeira.backend.service.AuthService;
import br.com.atlas.bigodeira.view.cliente.CadastroClienteView;
import br.com.atlas.bigodeira.view.cliente.HomeViewCliente;
import br.com.atlas.bigodeira.view.cliente.NovoAgendamentoClienteView;
import br.com.atlas.bigodeira.view.servicos.NovoServicoView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayoutCliente extends AppLayout implements RouterLayout {

    private H1 viewTitle;

    public MainLayoutCliente() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("Bigodeira");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.XLARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller);
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Home", HomeViewCliente.class, VaadinIcon.HOME.create()));

        if (AuthService.isLoggedIn()) {
            nav.addItem(new SideNavItem("Agendar", NovoAgendamentoClienteView.class, VaadinIcon.EDIT.create()));
        }

        nav.addItem(new SideNavItem("Cadastrar", CadastroClienteView.class, VaadinIcon.BOMB.create()));

        return nav;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
