package br.com.atlas.bigodeira.view;

import br.com.atlas.bigodeira.view.acessos.AcessosView;
import br.com.atlas.bigodeira.view.agendamentos.NovoAgendamentoView;
import br.com.atlas.bigodeira.view.agendamentos.VisualizarAgendamentosView;
import br.com.atlas.bigodeira.view.cadastroUsuario.CadastroUsuarioView;
import br.com.atlas.bigodeira.view.colaborador.CadastroColaboradorView;
import br.com.atlas.bigodeira.view.colaborador.VisualizarColaboradoresView;
import br.com.atlas.bigodeira.view.home.HomeView;
import br.com.atlas.bigodeira.view.servicos.NovoServicoView;
import br.com.atlas.bigodeira.view.servicos.VisualizarServicosView;
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
import com.vaadin.flow.theme.lumo.LumoUtility;


public class MainLayout extends AppLayout {

    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        //Recolher Menu
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        //Nome da página atual
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
        //Rotas de navegação do menu
        SideNav nav = new SideNav();

        //Home / Dashboard
        nav.addItem(new SideNavItem("Home", HomeView.class, VaadinIcon.HOME.create()));


        SideNavItem agendamentosLink = new SideNavItem("Agendamentos");
        agendamentosLink.setPrefixComponent(VaadinIcon.CALENDAR.create());



        agendamentosLink.addItem(new SideNavItem("Novo Agendamento", NovoAgendamentoView.class, VaadinIcon.PLUS.create()));
        //Visualizar/Validar/Tabela Agendamentos
        agendamentosLink.addItem(new SideNavItem("Validar Agendamentos", VisualizarAgendamentosView.class, VaadinIcon.CHECK.create()));
        nav.addItem(agendamentosLink);

        //Consulta Acessos
        nav.addItem(new SideNavItem("Acessos", AcessosView.class, VaadinIcon.CLOCK.create()));

        //Colaborador
        SideNavItem colaboradorLink = new SideNavItem("Colaborador");
        colaboradorLink.setPrefixComponent(VaadinIcon.USER.create());
        //Cadastro Colaborador
        colaboradorLink.addItem(new SideNavItem("Cadastrar Colaborador", CadastroColaboradorView.class, VaadinIcon.USER_STAR.create()));
        //Visualizar/Tabela Colaboradores
        colaboradorLink.addItem(new SideNavItem("Visualizar Colaboradores", VisualizarColaboradoresView.class, VaadinIcon.TABLE.create()));
        nav.addItem(colaboradorLink);

        //Cadastro Cliente
        nav.addItem(new SideNavItem("Cadastrar Cliente", CadastroUsuarioView.class, VaadinIcon.USERS.create()));

        //Serviços
        SideNavItem servicosLink = new SideNavItem("Serviços");
        servicosLink.setPrefixComponent(VaadinIcon.PENCIL.create());
        //Novo Serviço
        servicosLink.addItem(new SideNavItem("Novo Serviço", NovoServicoView.class, VaadinIcon.PLUS.create()));
        //Visualizar/Tabela Serviços
        servicosLink.addItem(new SideNavItem("Visualizar Serviços", VisualizarServicosView.class, VaadinIcon.TABLE.create()));
        nav.addItem(servicosLink);

        return nav;
    }

    //Adição do nome da página no header
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
