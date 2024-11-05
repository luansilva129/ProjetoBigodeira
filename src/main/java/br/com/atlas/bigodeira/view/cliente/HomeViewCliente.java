package br.com.atlas.bigodeira.view.cliente;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import br.com.atlas.bigodeira.backend.domainBase.domain.ClienteSession;
import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.*;
import br.com.atlas.bigodeira.view.MainLayoutCliente;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.Component;

import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.List;

@Route(value = "cliente/home", layout = MainLayoutCliente.class)
@PageTitle("Home/Cliente")
@UIScope
public class HomeViewCliente extends VerticalLayout {


    private final ServicoService servicoService;
    private final ColaboradorService colaboradorService;
    private final AgendamentoService agendamentoService;
    private VerticalLayout contentLayout;

    private VerticalLayout listaAgendamentos;


    @Autowired
    public HomeViewCliente(ServicoService servicoService,
                           ColaboradorService colaboradorService,
                           AgendamentoService agendamentoService) {
        this.servicoService = servicoService;
        this.colaboradorService = colaboradorService;
        this.agendamentoService = agendamentoService;

        initLayout();
    }

    private void initLayout() {
        VerticalLayout layoutPrincipal = new VerticalLayout();

        layoutPrincipal.setSpacing(true);
        layoutPrincipal.setPadding(true);
        layoutPrincipal.getStyle().set("padding", "40px");

        contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();

        HorizontalLayout primeiraLinha = criarTextoComBotao();

        HorizontalLayout segundaLinha = criarLinhaComImagemEInformacoes();

        listaAgendamentos = new VerticalLayout();
        listaAgendamentos.setVisible(true);

        Tabs tabs = criarMenuServicos();

        HorizontalLayout terceiraLinha = new HorizontalLayout();
        terceiraLinha.add(tabs);
        terceiraLinha.addClassNames(LumoUtility.JustifyContent.CENTER,
                LumoUtility.Gap.SMALL, LumoUtility.Height.MEDIUM,
                LumoUtility.Width.FULL);

        HorizontalLayout quartaLinha = new HorizontalLayout();
        quartaLinha.add(listaAgendamentos, contentLayout);
        quartaLinha.setWidthFull();

        layoutPrincipal.add(primeiraLinha, segundaLinha, terceiraLinha, quartaLinha);

        mostrarAgendamentosCliente();

        add(layoutPrincipal);
    }


    private HorizontalLayout criarTextoComBotao() {

        H1 texto = new H1("Bem-vindo, Cliente!");
        texto.getStyle().set("color", "#333");
        texto.getStyle().set("margin", "0");
        texto.getStyle().set("padding", "0px 0");
        texto.getStyle().set("font-weight", "bold");
        texto.getStyle().set("font-size", "45px");

        HorizontalLayout layout = new HorizontalLayout(texto);
        layout.setWidthFull();
        layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        layout.setPadding(false);
        layout.getStyle().set("margin-left", "50px");

        return layout;
    }


    private HorizontalLayout criarLinhaComImagemEInformacoes() {

        Image imagem = new Image("https://www.guiadasemana.com.br/contentFiles/image/2017/02/FEA/principal/49393_w840h0_1486764558shutterstock-barbearia.jpg", "Imagem de exemplo");
        imagem.setWidth("100%");
        imagem.setHeight("350px");
        imagem.getStyle().set("border", "2px solid #ccc");
        imagem.getStyle().set("border-radius", "8px");
        imagem.getStyle().set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.2)");
        imagem.getStyle().set("transition", "transform 0.3s");
        imagem.getStyle().set("cursor", "pointer");
        imagem.getStyle().set("filter", "brightness(0.9)");


        VerticalLayout colunaImagem = new VerticalLayout(imagem);
        colunaImagem.setWidth("100%");
        colunaImagem.setPadding(false);
        colunaImagem.setSpacing(false);
        colunaImagem.setAlignItems(Alignment.CENTER);

        Image gifImage = new Image("pole3.gif", "GIF de exemplo");
        gifImage.setWidth("20%");
        gifImage.setHeight("130px");

        VerticalLayout colunaCardGif = new VerticalLayout();
        colunaCardGif.setWidth("50%");
        colunaCardGif.setPadding(false);

        VerticalLayout cardInformacoes = criarCardInformacoes();
        cardInformacoes.setWidth("70%");
        cardInformacoes.setHeight("350px");

        cardInformacoes.getStyle().set("background-color", "#f9f9f9");
        cardInformacoes.getStyle().set("border", "1px solid #ccc");
        cardInformacoes.getStyle().set("border-radius", "8px");
        cardInformacoes.getStyle().set("box-shadow", "0 2px 5px rgba(0, 0, 0, 0.1)");
        cardInformacoes.getStyle().set("padding", "15px");
        cardInformacoes.setAlignItems(Alignment.START);

        colunaCardGif.add(gifImage);
        colunaCardGif.setSpacing(false);
        colunaCardGif.setAlignItems(Alignment.END);

        colunaCardGif.getStyle().set("margin-left", "20px");

        HorizontalLayout segundaLinha = new HorizontalLayout(colunaImagem, colunaCardGif, cardInformacoes);
        segundaLinha.setWidthFull();
        segundaLinha.setAlignItems(Alignment.START);

        segundaLinha.setPadding(true);
        segundaLinha.setMargin(true);
        segundaLinha.getStyle().set("margin-left", "20px");
        segundaLinha.getStyle().set("margin-right", "20px");

        return segundaLinha;
    }


    private VerticalLayout criarCardInformacoes() {
        VerticalLayout card = new VerticalLayout();
        card.setSpacing(false);

        H3 titulo = new H3("Informações");
        Paragraph horario = criarParagrafo("Horário de Funcionamento:");
        Paragraph horario2 = criarParagrafo("Segunda à Sexta: 08:00h às 20:00h");
        Paragraph horario3 = criarParagrafo("Sábado: 08:00h às 16:00h");
        Paragraph horario4 = criarParagrafo("Domingo: Fechado");
        H3 texto = new H3("Contato:");

        Icon telefoneIcon = new Icon("vaadin", "phone");
        telefoneIcon.getStyle().set("margin-right", "2px");
        telefoneIcon.setSize("15px");

        Span contatoTexto = new Span("Telefone: (11) 98765-4321");
        HorizontalLayout contatoLayout = new HorizontalLayout(telefoneIcon, contatoTexto);
        contatoLayout.setAlignItems(Alignment.CENTER);

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


    private VirtualList<AgendamentoBase> virtualListAgendamentos;

    private void mostrarAgendamentosCliente() {
        if (ClienteSession.isClienteLogado()) {
            Long clienteLogadoId = ClienteSession.getClienteLogadoId();

            List<AgendamentoBase> agendamentos = ClienteSession.getAgendamentos();
            if (agendamentos == null || agendamentos.isEmpty()) {

                agendamentos = agendamentoService.findByClienteId(clienteLogadoId);
                ClienteSession.setAgendamentos(agendamentos);
            }

            if (virtualListAgendamentos == null) {
                virtualListAgendamentos = new VirtualList<>();
                virtualListAgendamentos.setRenderer(agendamentoCardRenderer);
                listaAgendamentos.add(virtualListAgendamentos);
            }

            virtualListAgendamentos.setItems(agendamentos);
            virtualListAgendamentos.setVisible(true);
        }
    }

    private ComponentRenderer<Component, AgendamentoBase> agendamentoCardRenderer = new ComponentRenderer<>(
            agendamento -> {
                VerticalLayout cardLayout = new VerticalLayout();
                cardLayout.setWidth("100%");
                cardLayout.setPadding(false);
                cardLayout.setMargin(false);
                cardLayout.setSpacing(false);
                cardLayout.getStyle().set("border", "1px solid #DDDDDD");
                cardLayout.getStyle().set("border-radius", "10px");
                cardLayout.getStyle().set("padding", "15px");
                cardLayout.getStyle().set("box-shadow", "0 4px 10px rgba(0, 0, 0, 0.1)");
                cardLayout.getStyle().set("background-color", "#FAFAFA");
                cardLayout.getStyle().set("margin-bottom", "10px");

                H4 titulo = new H4("Agendamento");
                titulo.getStyle().set("margin", "0");
                titulo.getStyle().set("color", "#4A4A4A");
                cardLayout.add(titulo);

                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setPadding(false);
                infoLayout.setSpacing(false);

                if (agendamento != null) {
                    infoLayout.add(criarLinhaInfo("Serviço: ", safeText(agendamento.getServicosBase())));
                    infoLayout.add(criarLinhaInfo("Data: ", safeText(agendamento.getData())));
                    infoLayout.add(criarLinhaInfo("Horário: ", safeText(agendamento.getHorario())));
                    infoLayout.add(criarLinhaInfo("Colaborador: ", safeText(agendamento.getColaborador())));
                    infoLayout.add(criarLinhaInfo("Status: ", safeText(agendamento.getStatus())));

                } else {
                    infoLayout.add(new Text("Nenhum agendamento encontrado."));
                }

                infoLayout.getStyle().set("padding", "15px");
                infoLayout.getStyle().set("background-color", "#f9f9f9");
                infoLayout.getStyle().set("border-radius", "8px");

                cardLayout.add(infoLayout);
                return cardLayout;
            });

    private HorizontalLayout criarLinhaInfo(String label, String value) {
        HorizontalLayout linha = new HorizontalLayout();
        linha.setWidthFull();
        linha.setJustifyContentMode(JustifyContentMode.START);
        linha.setSpacing(false);
        linha.setMargin(false);

        Span lblLabel = new Span(label);
        lblLabel.getStyle().set("font-weight", "bold");
        lblLabel.getStyle().set("color", "#555");
        lblLabel.getStyle().set("margin-right", "10px");

        Span lblValue = new Span(value);
        lblValue.getStyle().set("color", "#333");

        linha.add(lblLabel, lblValue);
        return linha;
    }


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
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);

        grid.getElement().getStyle().set("border", "1px solid #DDDDDD");
        grid.getElement().getStyle().set("border-radius", "8px");
        grid.getElement().getStyle().set("overflow", "hidden");

        grid.addColumn(ServicosBase::getNome)
                .setHeader("Nome do Serviço:")
                .setSortable(true)
                .setFlexGrow(1)
                .setHeader(getHeader("Nome do Serviço:"));

        grid.addColumn(ServicosBase::getDescricao)
                .setHeader("Descrição:")
                .setSortable(true)
                .setFlexGrow(1)
                .setHeader(getHeader("Descrição:"));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        grid.addColumn(new TextRenderer<>(servico -> currencyFormat.format(servico.getPreco())))
                .setHeader("Preço (R$)")
                .setSortable(true)
                .setFlexGrow(1);

        contentLayout.add(grid);
    }


    private void mostrarColaboradores() {
        List<Colaborador> colaboradores = colaboradorService.findAll();

        Grid<Colaborador> grid = new Grid<>(Colaborador.class);
        grid.setItems(colaboradores);
        grid.removeAllColumns();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);

        grid.getElement().getStyle().set("border", "1px solid #DDDDDD");
        grid.getElement().getStyle().set("border-radius", "8px");
        grid.getElement().getStyle().set("overflow", "hidden");

        grid.addColumn(Colaborador::getNome)
                .setHeader("Nome")
                .setSortable(true)
                .setFlexGrow(1)
                .setHeader(getHeader("Nome"));

        grid.addColumn(Colaborador::getEspecialidade)
                .setHeader("Especialidade")
                .setSortable(true)
                .setFlexGrow(1)
                .setHeader(getHeader("Especialidade"));

        grid.addColumn(Colaborador::getHorarioInicio)
                .setHeader("Horário Disponível")
                .setSortable(true)
                .setFlexGrow(1)
                .setHeader(getHeader("Horário Disponível"));

        contentLayout.add(grid);
    }


    private Component getHeader(String text) {
        Span span = new Span(text);
        span.getElement().getStyle().set("font-weight", "bold");
        span.getElement().getStyle().set("color", "#333333");
        span.getElement().getStyle().set("background-color", "#f8f9fa");
        span.getElement().getStyle().set("padding", "10px");
        return span;
    }


    private void mostrarLocalizacao() {
        Image mapaImagem = new Image("https://www.cnnbrasil.com.br/wp-content/uploads/sites/12/2024/02/google-maps-e1707316052388.png?w=1200&h=900&crop=1", "Mapa");
        mapaImagem.setWidth("100%");

        contentLayout.add(mapaImagem);
    }
}
