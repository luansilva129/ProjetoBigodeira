package br.com.atlas.bigodeira.view.home;

import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Home / Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "home", layout = MainLayout.class)
public class HomeView extends HorizontalLayout {
    public HomeView() {
        Board board = new Board();
        board.setWidthFull();
        setPadding(true);

        board.addRow(createCards(new Icon(VaadinIcon.CALENDAR), "100", "Agendamentos Realizados"),
                     createCards(new Icon(VaadinIcon.USER), "10", "Colaboradores Ativos"),
                     createCards(new Icon(VaadinIcon.USERS), "230", "Clientes Cadastrados"),
                     createCards(new Icon(VaadinIcon.PENCIL), "12", "Serviços Disponiveis"));
        board.addRow(createLineChart()).getStyle().setHeight("auto");
        board.addRow(createColumChart()).getStyle().setHeight("auto");

        VerticalLayout lateralLayout = new VerticalLayout(
                createAgendamentosProximos(),
                createPieChart()
        );
        lateralLayout.setWidth("50%");
        lateralLayout.getStyle().setPaddingTop("8px");
        lateralLayout.setSpacing(false);

        add(board, lateralLayout);
    }

    //Gráfico que mostra a quantidade de agendamentos por colaborador
    public Component createLineChart() {
        Chart lineChart = new Chart(ChartType.LINE);
        lineChart.getStyle().set("padding-top", "32px");
        lineChart.setMaxHeight("360px");

        Configuration configuration = lineChart.getConfiguration();

        configuration.setTitle("Agendamentos por Colaborador");
        configuration.getTitle().getStyle().setFontSize("22px");

        YAxis yaxis = configuration.getyAxis();
        yaxis.setTitle("Quantidade Agendamentos");

        XAxis xAxis = configuration.getxAxis();
        //Cada quantidade total de agendamentos atrelados à um colaborador é separada por mês
        xAxis.setCategories("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Aug", "Set", "Out", "Nov", "Dez");

        //Cada lista define um colaborador e a quantidade de agendamentos por mês, onde o cada valor retorna um ponto
        //no gráfico referente ao mês e quantidade de agendamentos
        configuration.addSeries(new ListSeries("Luan", 10, 20, 30, 0, 0 ,0 ,0, 0, 0 , 0, 0, 0));
        configuration.addSeries(new ListSeries("Matheus", 20, 40, 25));
        configuration.addSeries(new ListSeries("Giovani", 10, 50, 30));
        configuration.addSeries(new ListSeries("Julio", 3, 7, 16));

        return lineChart;
    }

    //Gráfico que mostra a quantidade de serviços que foram agendados por mês
    public Component createColumChart() {
        Chart columChart = new Chart(ChartType.COLUMN);
        columChart.getStyle().set("padding-top", "32px");
        columChart.setMaxHeight("360px");

        Configuration configuration = columChart.getConfiguration();

        configuration.setTitle("Serviços Agendados por Mês");
        configuration.getTitle().getStyle().setFontSize("22px");

        YAxis yaxis = configuration.getyAxis();
        yaxis.setTitle("Quantidade Serviços");

        XAxis xAxis = configuration.getxAxis();
        xAxis.setCrosshair(new Crosshair());
        xAxis.setCategories("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Aug", "Set", "Out", "Nov", "Dez");

        PlotOptionsColumn columnOptions = new PlotOptionsColumn();
        columnOptions.setStacking(Stacking.NORMAL);
        configuration.setPlotOptions(columnOptions);

        Tooltip tooltip = new Tooltip();
        tooltip.setShared(true);
        configuration.setTooltip(tooltip);

        configuration.addSeries(new ListSeries("Corte", 10, 40, 10, 40, 10, 40, 10, 40, 10, 40, 10, 0));
        configuration.addSeries(new ListSeries("Barba", 20, 40, 25));
        configuration.addSeries(new ListSeries("Nevou", 10, 50, 30));
        configuration.addSeries(new ListSeries("Sobrancelha", 3, 7, 16));

        return columChart;
    }

    //Gráfico de pizza que separa os agendamentos por status, mostrando os agendamentos por status do agendamentos
    public Component createPieChart() {
        Chart pieChart = new Chart(ChartType.PIE);
        pieChart.setVisibilityTogglingDisabled(true);
        pieChart.getStyle().setPadding("0px");

        Configuration configuration = pieChart.getConfiguration();
        configuration.setTitle("Status Agendamentos");
        configuration.getTitle().getStyle().setFontSize("22px");

        Tooltip tooltip = new Tooltip();
        configuration.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        configuration.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries("Total");

        DataSeriesItem aguardando = new DataSeriesItem("Aguardando", 30);
        aguardando.setColor(SolidColor.ORANGE);
        series.add(aguardando);

        DataSeriesItem confirmado = new DataSeriesItem("Confirmado", 20);
        confirmado.setColor(SolidColor.LAWNGREEN);
        series.add(confirmado);

        DataSeriesItem cancelado = new DataSeriesItem("Cancelado", 10);
        cancelado.setColor(SolidColor.RED);
        series.add(cancelado);

        configuration.setSeries(series);

        return pieChart;
    }

    //Cards com quantidades totais de agendamentos, colaboradores, clientes e serviços
    public Component createCards(Icon icon, String quantidade, String descricao) {
        this.getElement().setAttribute("icon", icon.getIcon())
                .setAttribute("quantidade", quantidade)
                .setAttribute("descricao", descricao);

        icon.setSize("52px");
        icon.setColor("#1E293B");
        icon.getStyle().set("box-shadow", "4px 4px 4px rgba(0, 0, 0, 0.25)");
        icon.getStyle().set("border-radius", "64px");
        icon.getStyle().set("padding", "8px");

        Span quantidadeSpan = new Span(quantidade);
        quantidadeSpan.getStyle().set("font-size", "24px");
        quantidadeSpan.getStyle().set("font-weight", "500");

        Span descricaoSpan = new Span(descricao);
        descricaoSpan.getStyle().set("font-size", "12px");
        descricaoSpan.getStyle().set("font-weight", "700");

        VerticalLayout textLayout = new VerticalLayout(quantidadeSpan, descricaoSpan);
        textLayout.setSpacing(false);
        textLayout.setPadding(false);

        HorizontalLayout cardLayout = new HorizontalLayout(icon, textLayout);
        cardLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        cardLayout.setPadding(true);
        cardLayout.setWidthFull();
        cardLayout.getStyle().set("box-shadow", "2px 2px 10px rgba(0, 0, 0, 0.25)");
        cardLayout.getStyle().set("border-radius", "16px");

        HorizontalLayout cardContainer = new HorizontalLayout(cardLayout);
        cardContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        cardContainer.setWidth("auto");
        cardContainer.getStyle().set("padding", "8px");

        return cardContainer;
    }

    //Scroller lateral que mostra os agendamentos por ordem de proximidade da data, onde os agendamentos que
    //já passaram da data/hora atual não apareçam mais
    public Component createAgendamentosProximos() {
        H3 titulo = new H3("Agendamentos Próximos");

        VerticalLayout cardsAgendamentoContainer = new VerticalLayout(
                createCardAgendamento("Wanfranklin", "10/10/2024", "12:00"),
                createCardAgendamento("Wanfranklin", "10/10/2024", "12:00"),
                createCardAgendamento("Wanfranklin", "10/10/2024", "12:00"),
                createCardAgendamento("Wanfranklin", "10/10/2024", "12:00"),
                createCardAgendamento("Wanfranklin", "10/10/2024", "12:00"),
                createCardAgendamento("Wanfranklin", "10/10/2024", "12:00")
        );
        cardsAgendamentoContainer.setPadding(false);
        cardsAgendamentoContainer.getStyle().set("gap", "10px");
        cardsAgendamentoContainer.setWidthFull();

        Scroller agendamentosScroller = new Scroller(
                cardsAgendamentoContainer,
                Scroller.ScrollDirection.VERTICAL
        );
        agendamentosScroller.setWidthFull();

        VerticalLayout agendamentosLayout = new VerticalLayout(titulo, agendamentosScroller);
        agendamentosLayout.setPadding(true);
        agendamentosLayout.setAlignItems(Alignment.START);
        agendamentosLayout.getStyle().set("box-shadow", "2px 2px 10px rgba(0, 0, 0, 0.25)");
        agendamentosLayout.getStyle().set("border-radius", "16px");
        agendamentosLayout.setHeight("50%");
        agendamentosLayout.setMaxHeight("50%");

        return agendamentosLayout;
    }

    //Cards com as informações dos agendamentos, presente dentro do scroller
    public Component createCardAgendamento(String nomeCliente, String data, String hora){
        this.getElement().setAttribute("nomeCliente", nomeCliente)
                .setAttribute("data", data)
                .setAttribute("hora", hora);

        Span clientes = new Span("Cliente: "+nomeCliente);
        clientes.getStyle().setFontWeight(500);
        clientes.getStyle().setFontSize("14px");

        Span dataHora = new Span("Data: "+data+" || Horário: "+hora);
        dataHora.getStyle().setFontWeight(500);
        dataHora.getStyle().setFontSize("14px");

        VerticalLayout infoContainer = new VerticalLayout(clientes, dataHora);
        infoContainer.setAlignItems(Alignment.START);
        infoContainer.setSpacing(false);
        infoContainer.setPadding(false);

        Span status = new Span("CONFIRMADO");
        status.getStyle().setFontSize("14px");
        status.getStyle().setPadding("4px 8px");
        status.getStyle().setColor("white");
        status.getStyle().set("background-color", "#22C55E");
        status.setWidth("min-content");
        status.setHeight("min-content");
        status.getStyle().setBorderRadius("8px");

        HorizontalLayout cardsAgendamentosLayout = new HorizontalLayout(infoContainer, status);
        cardsAgendamentosLayout.setAlignItems(Alignment.CENTER);
        cardsAgendamentosLayout.getStyle().setBorder("1px solid black");
        cardsAgendamentosLayout.getStyle().setBorderRadius("8px");
        cardsAgendamentosLayout.getStyle().setPadding("8px 16px");
        cardsAgendamentosLayout.setWidthFull();

        return cardsAgendamentosLayout;
    }
}
