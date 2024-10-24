package br.com.atlas.bigodeira.view.home;

import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Home / Dashboard")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "home", layout = MainLayout.class)
public class HomeView extends VerticalLayout {
    public HomeView() {
        Board board = new Board();
        board.addRow(new H2("Home / Dashboard"),
                new H2("teste"));

        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Reindeer Kills by Predators");
        conf.setSubTitle("Kills Grouped by Counties");

        ListSeries series = new ListSeries("Diameter");
        series.setData(4900,  12100,  12800,
                6800,  143000, 125000,
                51100, 49500);
        conf.addSeries(series);

        Chart chart2 = new Chart(ChartType.PIE);

        Configuration conf2 = chart2.getConfiguration();
        conf2.setTitle("aaaaaaaaaaaaaaaa");
        conf2.setSubTitle("bbbbbbbbbbbbb");
        conf2.addSeries(series);

        board.addRow(chart, chart2);
        add(board);
    }
}
