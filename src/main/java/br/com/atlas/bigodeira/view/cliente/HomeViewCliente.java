package br.com.atlas.bigodeira.view.cliente;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.view.MainLayoutCliente;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "cliente/home", layout = MainLayoutCliente.class)
@PageTitle("Home")
public class HomeViewCliente extends VerticalLayout {

    private VerticalLayout layoutPrincipal;
    private boolean usuarioLogado = false;
    private VerticalLayout listaAgendamentos; // Usando VerticalLayout para a lista de cards
    private Tabs tabs; // Para as abas

    public HomeViewCliente() {
        layoutPrincipal = new VerticalLayout();
        layoutPrincipal.setSizeFull();
        layoutPrincipal.setSpacing(true);
        layoutPrincipal.setPadding(true);

        // Primeira linha: Texto e Botão
        HorizontalLayout primeiraLinha = criarTextoComBotao();

        // Segunda linha: Imagem
        HorizontalLayout segundaLinha = criarLinhaComImagemEInformacoes();

        // Terceira linha: Lista de Agendamentos (inicialmente oculta)
        listaAgendamentos = new VerticalLayout();
        listaAgendamentos.setVisible(false); // Escondido até o login

        // Quarta linha: Menu de Opções (agora usando Tabs)
        tabs = criarMenuServicos();

        // Adicionando as seções ao layout principal
        layoutPrincipal.add(primeiraLinha, segundaLinha, listaAgendamentos, tabs);

        // Adicionando o layout principal à interface
        add(layoutPrincipal);
    }

    private HorizontalLayout criarTextoComBotao() {
        H1 texto = new H1("Bem-vindo, Cliente!");
        Button loginButton = new Button("Login", e -> abrirPopupLogin());

        HorizontalLayout layout = new HorizontalLayout(texto, loginButton);
        layout.setWidthFull();
        layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        layout.setSpacing(true);
        layout.setPadding(true);

        return layout;
    }

    private HorizontalLayout criarLinhaComImagemEInformacoes() {
        // Coluna 1: Imagem
        Image imagem = new Image("https://via.placeholder.com/400x300", "Imagem de exemplo");
        imagem.setWidth("100%");
        imagem.setHeight("400px");

        VerticalLayout colunaImagem = new VerticalLayout(imagem);
        colunaImagem.setWidth("60%");
        colunaImagem.setPadding(false);
        colunaImagem.setSpacing(false);

        // Coluna 2: Card de Informações
        VerticalLayout cardInformacoes = criarCardInformacoes();
        cardInformacoes.setWidth("100%");
        cardInformacoes.setHeight("100%");

        VerticalLayout colunaCard = new VerticalLayout(cardInformacoes);
        colunaCard.setWidth("30%");
        colunaCard.setHeight("400px"); // Altura fixa para coincidir com a imagem
        colunaCard.setAlignItems(Alignment.CENTER); // Centraliza o card dentro da coluna

        // Layout Horizontal contendo ambas as colunas
        HorizontalLayout segundaLinha = new HorizontalLayout(colunaImagem, colunaCard);
        segundaLinha.setWidthFull();
        segundaLinha.setSpacing(true);
        segundaLinha.setAlignItems(Alignment.CENTER); // Alinha imagem e card no centro verticalmente

        return segundaLinha;
    }

    private VerticalLayout criarCardInformacoes() {
        VerticalLayout card = new VerticalLayout();
        card.setSpacing(false); // Remove espaçamento extra entre componentes

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
        paragrafo.getStyle().set("margin", "10px 0"); // Margem vertical pequena entre parágrafos
        return paragrafo;
    }

    private void mostrarAgendamentosCliente() {
        if (usuarioLogado) {
            // Limpar a lista existente
            listaAgendamentos.removeAll();

            // Exemplo: Carregar agendamentos fictícios do cliente logado
            List<AgendamentoBase> agendamentos = List.of(
                    new AgendamentoBase(), // Adicione instâncias reais conforme necessário
                    new AgendamentoBase()
            );

            // Adicionando cards para cada agendamento
            for (AgendamentoBase agendamento : agendamentos) {
                VerticalLayout cardAgendamento = criarCardAgendamento(agendamento);
                listaAgendamentos.add(cardAgendamento);
            }

            listaAgendamentos.setVisible(true); // Mostrar a lista de agendamentos
        }
    }

    private VerticalLayout criarCardAgendamento(AgendamentoBase agendamento) {
        VerticalLayout cardLayout = new VerticalLayout();
        cardLayout.setMargin(true);
        cardLayout.setSpacing(true);
        cardLayout.getStyle().set("border", "1px solid #DDDDDD");
        cardLayout.getStyle().set("border-radius", "10px");
        cardLayout.getStyle().set("padding", "20px");
        cardLayout.getStyle().set("box-shadow", "0 2px 5px rgba(0, 0, 0, 0.1)");

        // Avatar ou imagem do agendamento
        Avatar avatar = new Avatar("A"); // Substitua pelo nome ou pela imagem do colaborador
        avatar.setHeight("64px");
        avatar.setWidth("64px");

        // Layout de informações do agendamento
        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);
        infoLayout.add(new Div(new Text("Serviço: " + agendamento.getServicosBase()))); // Exemplo, substitua com dados reais
        infoLayout.add(new Div(new Text("Data: " + agendamento.getData()))); // Exemplo, substitua com dados reais
        infoLayout.add(new Div(new Text("Horário: " + agendamento.getHorario()))); // Exemplo, substitua com dados reais
        infoLayout.add(new Div(new Text("Colaborador: " + agendamento.getColaborador()))); // Exemplo, substitua com dados reais

        // Detalhes adicionais
        VerticalLayout contactLayout = new VerticalLayout();
        contactLayout.setSpacing(false);
        contactLayout.setPadding(false);
        contactLayout.add(new Div(new Text("Contato: (11) 98765-4321"))); // Exemplo de contato

        infoLayout.add(new Details("Mais informações", contactLayout));

        // Adiciona o avatar e as informações ao layout do cartão
        cardLayout.add(avatar, infoLayout);
        return cardLayout;
    }

    private Tabs criarMenuServicos() {
        Tab servicosTab = new Tab("Serviços");
        Tab colaboradoresTab = new Tab("Colaboradores");
        Tab localizacaoTab = new Tab("Localização");

        tabs = new Tabs(servicosTab, colaboradoresTab, localizacaoTab);
        tabs.setWidthFull();
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);



        // Adicionando a ação para quando uma aba for selecionada
        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab == servicosTab) {
                Notification.show("Serviços selecionados.");
            } else if (selectedTab == colaboradoresTab) {
                Notification.show("Colaboradores selecionados.");
            } else if (selectedTab == localizacaoTab) {
                Notification.show("Localização selecionada.");
            }
        });

        return tabs;
    }

    private void abrirPopupLogin() {
        Dialog loginDialog = new Dialog();
        loginDialog.setWidth("300px");
        loginDialog.setCloseOnOutsideClick(true);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);

        TextField emailField = new TextField("E-mail");
        PasswordField senhaField = new PasswordField("Senha");
        Button loginButton = new Button("Login", e -> realizarLogin(loginDialog));

        dialogLayout.add(emailField, senhaField, loginButton);
        dialogLayout.setSpacing(true);

        loginDialog.add(dialogLayout);
        loginDialog.open();
    }

    private void realizarLogin(Dialog dialog) {
        // Aqui você faria a lógica de autenticação
        usuarioLogado = true; // Definindo como logado
        dialog.close(); // Fecha o diálogo de login
        mostrarAgendamentosCliente(); // Mostra os agendamentos após login
    }
}
