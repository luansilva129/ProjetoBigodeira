package br.com.atlas.bigodeira.view.cliente;

import br.com.atlas.bigodeira.backend.controller.cliente.ClienteController;
import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.domainBase.domain.ClienteSession;
import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.*;
import br.com.atlas.bigodeira.view.MainLayoutCliente;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Route(value = "cliente/home", layout = MainLayoutCliente.class)
@PageTitle("Home/Cliente")
@UIScope
@Component
public class HomeViewCliente extends VerticalLayout {

    @Autowired
    ServicoService servicoService;

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteController clienteController;

    @Autowired
    AgendamentoService agendamentoService;

    private VerticalLayout contentLayout;
    private VerticalLayout listaAgendamentos;


    @Autowired
    public HomeViewCliente(ServicoService servicoService,
                           ColaboradorService colaboradorService,
                           ClienteService clienteService,
                           ClienteController clienteController,
                           AgendamentoService agendamentoService) {
        this.servicoService = servicoService;
        this.colaboradorService = colaboradorService;
        this.clienteService = clienteService;
        this.clienteController = clienteController;
        this.agendamentoService = agendamentoService;

        initLayout();
    }

    private void initLayout() {
        VerticalLayout layoutPrincipal = new VerticalLayout();

        layoutPrincipal.setSpacing(true);
        layoutPrincipal.setPadding(true);

        contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();

        HorizontalLayout primeiraLinha = criarTextoComBotao();
        HorizontalLayout segundaLinha = criarLinhaComImagemEInformacoes();

        listaAgendamentos = new VerticalLayout();
        listaAgendamentos.setVisible(true);

        Tabs tabs = criarMenuServicos();

        layoutPrincipal.add(primeiraLinha, segundaLinha, listaAgendamentos, tabs, contentLayout);

        mostrarAgendamentosCliente();

        add(layoutPrincipal);
    }



    private HorizontalLayout criarTextoComBotao() {
        H1 texto = new H1("Bem-vindo, Cliente!");

        Button actionButton;
        if (clienteController.isClienteLogado()) {
            actionButton = new Button("Sair", e -> clienteController.logout());
        } else {
            actionButton = new Button("Entrar", e -> abrirPopupLogin());
        }

        HorizontalLayout layout = new HorizontalLayout(texto, actionButton);
        layout.setWidthFull();
        layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        layout.setSpacing(false);
        layout.setPadding(false);

        return layout;
    }


    private HorizontalLayout criarLinhaComImagemEInformacoes() {

        Image imagem = new Image("https://www.guiadasemana.com.br/contentFiles/image/2017/02/FEA/principal/49393_w840h0_1486764558shutterstock-barbearia.jpg", "Imagem de exemplo");
        imagem.setWidth("90%");
        imagem.setHeight("350px");

        VerticalLayout colunaImagem = new VerticalLayout(imagem);
        colunaImagem.setWidth("56%");
        colunaImagem.setPadding(false);
        colunaImagem.setSpacing(false);

        VerticalLayout cardInformacoes = criarCardInformacoes();
        cardInformacoes.setWidth("100%");
        cardInformacoes.setHeight("100%");

        VerticalLayout colunaCard = new VerticalLayout(cardInformacoes);
        colunaCard.setWidth("30%");
        colunaCard.setHeight("400px");
        colunaCard.setAlignItems(Alignment.CENTER);

        HorizontalLayout segundaLinha = new HorizontalLayout(colunaImagem, colunaCard);
        segundaLinha.setWidthFull();
        segundaLinha.setSpacing(true);
        segundaLinha.setAlignItems(Alignment.CENTER);

        return segundaLinha;
    }

    private VerticalLayout criarCardInformacoes() {
        VerticalLayout card = new VerticalLayout();
        card.setSpacing(false);

        // Título e Parágrafos
        H3 titulo = new H3("Informações");
        Paragraph horario = criarParagrafo("Horário de Funcionamento:");
        Paragraph horario2 = criarParagrafo("Segunda à Sexta: 08:00h às 20:00h");
        Paragraph horario3 = criarParagrafo("Sábado: 08:00h às 16:00h");
        Paragraph horario4 = criarParagrafo("Domingo: Fechado");
        H3 texto = new H3("Contato:");

        // Ícone de telefone + contato
        Icon telefoneIcon = new Icon("vaadin", "phone");
        telefoneIcon.getStyle().set("margin-right", "2px"); // Espaço entre ícone e texto
        telefoneIcon.setSize("15px");

        Span contatoTexto = new Span("Telefone: (11) 98765-4321");
        HorizontalLayout contatoLayout = new HorizontalLayout(telefoneIcon, contatoTexto);
        contatoLayout.setAlignItems(Alignment.CENTER);

        // Adicionando componentes ao Card
        card.add(titulo, horario, horario2, horario3, horario4, texto, contatoLayout);
        card.getStyle().set("border", "1px solid #DDDDDD");
        card.getStyle().set("border-radius", "10px");
        card.getStyle().set("padding", "20px");
        card.getStyle().set("box-shadow", "0 2px 5px rgba(0, 0, 0, 0.1)");

        return card;
    }

    private Paragraph criarParagrafo(String texto) {
        Paragraph paragrafo = new Paragraph(texto);
        paragrafo.getStyle().set("margin", "10px 0");
        return paragrafo;
    }

    private void mostrarAgendamentosCliente() {

        System.out.println("Aqui2");
        if (ClienteSession.isClienteLogado()) {
            listaAgendamentos.removeAll();
            System.out.println("Aqui3");
            List<AgendamentoBase> agendamentos = ClienteSession.getAgendamentos();
            System.out.println("Aqui4");

            if (agendamentos.isEmpty()) {
                System.out.println("Aqui5");
                Long clienteLogadoId = ClienteSession.getClienteLogadoId();
                System.out.println("Aqui6");
                agendamentos = agendamentoService.findByClienteId(clienteLogadoId);
                System.out.println("Aqui7");

                ClienteSession.setAgendamentos(agendamentos); // Armazena os agendamentos
                System.out.println("Aqui8");
            }

            for (AgendamentoBase agendamento : agendamentos) {
                VerticalLayout cardAgendamento = criarCardAgendamento(agendamento);
                listaAgendamentos.add(cardAgendamento);
            }

            listaAgendamentos.setVisible(true);
        }
    }


    private VerticalLayout criarCardAgendamento(AgendamentoBase agendamento) {
        VerticalLayout cardLayout = new VerticalLayout();
        cardLayout.setWidth("30%");
        cardLayout.setAlignItems(Alignment.CENTER);
        cardLayout.setAlignSelf(Alignment.CENTER);
        cardLayout.setPadding(false);
        cardLayout.setMargin(false);
        cardLayout.setSpacing(false);
        cardLayout.getStyle().set("border", "1px solid #DDDDDD");
        cardLayout.getStyle().set("border-radius", "10px");
        cardLayout.getStyle().set("padding", "20px");
        cardLayout.getStyle().set("box-shadow", "0 2px 5px rgba(0, 0, 0, 0.1)");
        cardLayout.getStyle().set("background-color", "#FAFAFA"); // Cor de fundo leve

        H4 titulo = new H4("Agendamento");
        titulo.getStyle().set("margin", "0");
        cardLayout.add(titulo);

        // Layout para as informações do agendamento
        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setPadding(false);
        infoLayout.setSpacing(false);


        if (agendamento != null) {
            infoLayout.add(criarLinhaInfo("Serviço", safeText(agendamento.getServicosBase())));
            infoLayout.add(criarLinhaInfo("Data", safeText(agendamento.getData())));
            infoLayout.add(criarLinhaInfo("Horário", safeText(agendamento.getHorario())));
            infoLayout.add(criarLinhaInfo("Colaborador", safeText(agendamento.getColaborador())));
            infoLayout.setAlignItems(Alignment.CENTER);
        } else {
            infoLayout.add(new Div(new Text("Nenhum agendamento encontrado.")));
        }

        cardLayout.add(infoLayout);
        return cardLayout;
    }


    private HorizontalLayout criarLinhaInfo(String label, String value) {
        HorizontalLayout linha = new HorizontalLayout();
        linha.setWidthFull();
        linha.setJustifyContentMode(JustifyContentMode.BETWEEN);
        linha.setSpacing(false);
        linha.setMargin(false);

        Span lblLabel = new Span(label + ":");
        lblLabel.getStyle().set("font-weight", "bold");

        Span lblValue = new Span(value);

        linha.add(lblLabel, lblValue);
        return linha;
    }


    // Método seguro para evitar null pointer
    private String safeText(Object value) {
        return value != null ? value.toString() : "Não disponível";
    }


    private Tabs criarMenuServicos() {
        Tab servicosTab = new Tab("Serviços");
        Tab colaboradoresTab = new Tab("Colaboradores");
        Tab localizacaoTab = new Tab("Localização");

        Tabs tabs = new Tabs(servicosTab, colaboradoresTab, localizacaoTab);
        tabs.setWidthFull();
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            atualizarConteudo(selectedTab);
        });

        return tabs;
    }

    private void atualizarConteudo(Tab selectedTab) {
        contentLayout.removeAll();

        if (selectedTab.getLabel().equals("Serviços")) {
            mostrarServicos();
        } else if (selectedTab.getLabel().equals("Colaboradores")) {
            mostrarColaboradores();
        } else if (selectedTab.getLabel().equals("Localização")) {
            mostrarLocalizacao();
        } else {
            Notification.show("Aba desconhecida.");
        }
    }

    private void mostrarServicos() {
        List<ServicosBase> servicos = servicoService.findAll();

        Grid<ServicosBase> grid = new Grid<>(ServicosBase.class);
        grid.setItems(servicos);
        grid.removeAllColumns();
        grid.addColumn(ServicosBase::getNome).setHeader("Nome do Serviço:").setSortable(true);
        grid.addColumn(ServicosBase::getDescricao).setHeader("Descrição:").setSortable(true);
        grid.addColumn(ServicosBase::getPreco).setHeader("Preço (R$)").setSortable(true);


        contentLayout.add(grid);
    }

    private void mostrarColaboradores() {
        List<Colaborador> colaboradores = colaboradorService.findAll(); // Busca colaboradores do banco

        Grid<Colaborador> grid = new Grid<>(Colaborador.class);
        grid.setItems(colaboradores);
        grid.removeAllColumns();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);

        grid.addColumn(Colaborador::getNome).setHeader("Nome").setSortable(true);
        grid.addColumn(Colaborador::getEspecialidade).setHeader("Especialidade").setSortable(true);
        grid.addColumn(Colaborador::getHorario).setHeader("Horário Disponível").setSortable(true);

        contentLayout.add(grid);
    }


    private void mostrarLocalizacao() {
        Image mapaImagem = new Image("https://www.cnnbrasil.com.br/wp-content/uploads/sites/12/2024/02/google-maps-e1707316052388.png?w=1200&h=900&crop=1", "Mapa");
        mapaImagem.setWidth("100%");

        contentLayout.add(mapaImagem);
    }

    private void abrirPopupLogin() {
        Dialog loginDialog = new Dialog();
        loginDialog.setWidth("300px");
        loginDialog.setHeight("300px");

        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSpacing(true);
        loginLayout.setPadding(true);
        loginLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        TextField emailField = new TextField("Email");
        emailField.setWidthFull();

        PasswordField passwordField = new PasswordField("Senha");
        passwordField.setWidthFull();

        HorizontalLayout buttonLayout = getHorizontalLayout(emailField, passwordField, loginDialog);
        buttonLayout.setSpacing(true);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        loginLayout.add(emailField, passwordField, buttonLayout);
        loginDialog.add(loginLayout);

        loginDialog.open();
    }

    private HorizontalLayout getHorizontalLayout(TextField emailField, PasswordField passwordField, Dialog loginDialog) {
        Button loginButton = new Button("Entrar", event -> {
            String email = emailField.getValue();
            String senha = passwordField.getValue();

            Optional<Cliente> clienteOptional = clienteService.findByEmail2(email);
            if (clienteOptional.isPresent() && clienteService.autenticar(email, senha)) {
                Cliente cliente = clienteOptional.get();
                ClienteSession.setClienteLogadoId(cliente.getId());
                ClienteSession.setClienteLogado(true);

                AuthService.login();

                Notification.show("Login realizado com sucesso!");

                loginDialog.close();
                UI.getCurrent().getPage().reload();
                mostrarAgendamentosCliente();

            } else {
                Notification.show("Email ou senha inválidos.", 3000, Notification.Position.MIDDLE);
            }
        });

        Button cancelButton = new Button("Cancelar", event -> loginDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(loginButton, cancelButton);
        return buttonLayout;
    }


}
