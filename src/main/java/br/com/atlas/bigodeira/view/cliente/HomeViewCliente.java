package br.com.atlas.bigodeira.view.cliente;

import br.com.atlas.bigodeira.backend.controller.cliente.ClienteController;
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


import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.List;

@Route(value = "cliente/home", layout = MainLayoutCliente.class)
@PageTitle("Home/Cliente")
@UIScope
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
        quartaLinha.setWidthFull(); // Opcional: definir a largura total

        layoutPrincipal.add(primeiraLinha, segundaLinha, terceiraLinha, quartaLinha);

        mostrarAgendamentosCliente();

        add(layoutPrincipal);
    }

    private HorizontalLayout criarTextoComBotao() {
        // Título de boas-vindas
        H1 texto = new H1("Bem-vindo, Cliente!");
        texto.getStyle().set("color", "#333"); // Cor do texto
        texto.getStyle().set("margin", "0"); // Remove margem padrão
        texto.getStyle().set("padding", "0px 0"); // Adiciona padding vertical
        texto.getStyle().set("font-weight", "bold"); // Deixa o texto em negrito
        texto.getStyle().set("font-size", "45px"); // Aumenta o tamanho da fonte

        // Layout horizontal
        HorizontalLayout layout = new HorizontalLayout(texto);
        layout.setWidthFull();
        layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        layout.setPadding(false); // Adiciona padding ao layout
        layout.getStyle().set("margin-left", "50px"); // Adiciona margem à direita da coluna da imagem

        return layout;
    }


    private HorizontalLayout criarLinhaComImagemEInformacoes() {
        // Criar a imagem estática
        Image imagem = new Image("https://www.guiadasemana.com.br/contentFiles/image/2017/02/FEA/principal/49393_w840h0_1486764558shutterstock-barbearia.jpg", "Imagem de exemplo");
        imagem.setWidth("100%");
        imagem.setHeight("350px"); // Defina uma altura fixa
        imagem.getStyle().set("border", "2px solid #ccc"); // Adiciona uma borda cinza
        imagem.getStyle().set("border-radius", "8px"); // Arredonda os cantos
        imagem.getStyle().set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.2)"); // Adiciona sombra
        imagem.getStyle().set("transition", "transform 0.3s");
        imagem.getStyle().set("cursor", "pointer");
        imagem.getStyle().set("filter", "brightness(0.9)"); // Diminui um pouco o brilho

        // Coluna para a imagem estática
        VerticalLayout colunaImagem = new VerticalLayout(imagem);
        colunaImagem.setWidth("100%"); // Ajuste conforme necessário
        colunaImagem.setPadding(false); // Remover padding
        colunaImagem.setSpacing(false); // Remover espaçamento
        colunaImagem.setAlignItems(Alignment.CENTER); // Centraliza o conteúdo na coluna

        // Criar a imagem do GIF
        Image gifImage = new Image("pole3.gif", "GIF de exemplo");
        gifImage.setWidth("30%"); // Ajuste a largura conforme necessário
        gifImage.setHeight("150px"); // Defina uma altura fixa

        // Coluna para o GIF e o card
        VerticalLayout colunaCardGif = new VerticalLayout();
        colunaCardGif.setWidth("50%"); // Largura da coluna do GIF e card
        colunaCardGif.setPadding(false); // Remover padding

        // Criar o card de informações
        VerticalLayout cardInformacoes = criarCardInformacoes();
        cardInformacoes.setWidth("70%");
        cardInformacoes.setHeight("350px"); // Defina uma altura fixa para o card também

        // Estilizar o card de informações
        cardInformacoes.getStyle().set("background-color", "#f9f9f9"); // Cor de fundo leve
        cardInformacoes.getStyle().set("border", "1px solid #ccc"); // Borda
        cardInformacoes.getStyle().set("border-radius", "8px"); // Bordas arredondadas
        cardInformacoes.getStyle().set("box-shadow", "0 2px 5px rgba(0, 0, 0, 0.1)"); // Sombra
        cardInformacoes.getStyle().set("padding", "15px"); // Espaçamento interno
        cardInformacoes.setAlignItems(Alignment.START); // Alinha o conteúdo no início

        // Adicionar o GIF e o card ao layout vertical
        colunaCardGif.add(gifImage);
        colunaCardGif.setSpacing(false); // Remover espaçamento entre o GIF e o card
        colunaCardGif.setAlignItems(Alignment.END);

        // Adicionar um espaçamento entre a coluna da imagem e a coluna do GIF e card
        colunaCardGif.getStyle().set("margin-left", "20px"); // Ajuste a margem esquerda para criar espaçamento

        // HorizontalLayout para a imagem e o card
        HorizontalLayout segundaLinha = new HorizontalLayout(colunaImagem, colunaCardGif, cardInformacoes);
        segundaLinha.setWidthFull();
        segundaLinha.setAlignItems(Alignment.START); // Alinha no topo

        // Definindo margens laterais
        segundaLinha.setPadding(true);
        segundaLinha.setMargin(true);
        segundaLinha.getStyle().set("margin-left", "20px"); // Ajuste a margem esquerda
        segundaLinha.getStyle().set("margin-right", "20px"); // Ajuste a margem direita

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
                cardLayout.getStyle().set("padding", "15px"); // Aumentar o padding
                cardLayout.getStyle().set("box-shadow", "0 4px 10px rgba(0, 0, 0, 0.1)"); // Aumentar a sombra
                cardLayout.getStyle().set("background-color", "#FAFAFA"); // Cor de fundo leve
                cardLayout.getStyle().set("margin-bottom", "10px");

                H4 titulo = new H4("Agendamento");
                titulo.getStyle().set("margin", "0");
                titulo.getStyle().set("color", "#4A4A4A"); // Cor do texto do título
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

                // Adiciona estilo ao infoLayout
                infoLayout.getStyle().set("padding", "15px");
                infoLayout.getStyle().set("background-color", "#f9f9f9"); // Fundo leve
                infoLayout.getStyle().set("border-radius", "8px"); // Bordas arredondadas

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
        lblLabel.getStyle().set("color", "#555"); // Cor do texto do label
        lblLabel.getStyle().set("margin-right", "10px"); // Espaçamento à direita do label

        Span lblValue = new Span(value);
        lblValue.getStyle().set("color", "#333"); // Cor do texto do valor

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

        // Estilizando o grid
        grid.getElement().getStyle().set("border", "1px solid #DDDDDD");
        grid.getElement().getStyle().set("border-radius", "8px"); // Bordas arredondadas
        grid.getElement().getStyle().set("overflow", "hidden"); // Ocultar overflow

        // Cabeçalhos estilizados
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

        // Formatação de preços com NumberFormat
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

        // Estilizando o grid
        grid.getElement().getStyle().set("border", "1px solid #DDDDDD");
        grid.getElement().getStyle().set("border-radius", "8px"); // Bordas arredondadas
        grid.getElement().getStyle().set("overflow", "hidden"); // Ocultar overflow

        // Cabeçalhos estilizados
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

        grid.addColumn(Colaborador::getHorario)
                .setHeader("Horário Disponível")
                .setSortable(true)
                .setFlexGrow(1)
                .setHeader(getHeader("Horário Disponível"));

        contentLayout.add(grid);
    }

    // Método para estilizar cabeçalhos
    private Component getHeader(String text) {
        Span span = new Span(text);
        span.getElement().getStyle().set("font-weight", "bold"); // Texto em negrito
        span.getElement().getStyle().set("color", "#333333"); // Cor do texto
        span.getElement().getStyle().set("background-color", "#f8f9fa"); // Fundo do cabeçalho
        span.getElement().getStyle().set("padding", "10px"); // Padding
        return span;
    }



    private void mostrarLocalizacao() {
        Image mapaImagem = new Image("https://www.cnnbrasil.com.br/wp-content/uploads/sites/12/2024/02/google-maps-e1707316052388.png?w=1200&h=900&crop=1", "Mapa");
        mapaImagem.setWidth("100%");

        contentLayout.add(mapaImagem);
    }
}
