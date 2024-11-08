package br.com.atlas.bigodeira.view.acessos;

import br.com.atlas.bigodeira.backend.controller.agendamento.AgendamentoController;
import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@PageTitle("Consulta de Acessos")
@Route(value = "acessos", layout = MainLayout.class)
public class AcessosView extends VerticalLayout {

    private final Grid<AgendamentoBase> grid;
    private final AgendamentoController agendamentoController;

    public AcessosView(AgendamentoController agendamentoController) {
        this.agendamentoController = agendamentoController;

        HorizontalLayout headerLayout = new HorizontalLayout();

        TextField procurarCliente = new TextField();
        procurarCliente.setPlaceholder("Cliente");
        procurarCliente.setSuffixComponent(VaadinIcon.SEARCH.create());
        procurarCliente.addValueChangeListener(event -> filterGridByCliente(event.getValue()));
        headerLayout.add(procurarCliente);

        TextField procurarColaborador = new TextField();
        procurarColaborador.setPlaceholder("Colaborador");
        procurarColaborador.setSuffixComponent(VaadinIcon.SEARCH.create());
        procurarColaborador.addValueChangeListener(event -> filterGridByColaborador(event.getValue()));
        headerLayout.add(procurarColaborador);

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.setPlaceholder("Status");
        statusComboBox.setItems("CONFIRMADO", "CANCELADO" , "TUDO");
        statusComboBox.addValueChangeListener(event -> {
            String selectedStatus = event.getValue();
            filterGridByStatus(selectedStatus);
        });
        headerLayout.add(statusComboBox);


        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.START);
        headerLayout.setAlignItems(Alignment.CENTER);


        headerLayout.setSpacing(true);
        headerLayout.setPadding(true);

        add(headerLayout);

        grid = new Grid<>(AgendamentoBase.class, false);
        loadAgendamentos();
        setHeight("80%");

        grid.setSelectionMode(Grid.SelectionMode.NONE);

        grid.addColumn(agendamento -> agendamento.getCliente().getNome())
                .setHeader("Nome")
                .setSortable(true)
                .setAutoWidth(true)
                .setKey("nome");

        grid.addColumn(agendamento -> agendamento.getServicosBase().getNome())
                .setHeader("Serviço")
                .setSortable(true)
                .setAutoWidth(true)
                .setKey("servico");

        grid.addColumn(agendamento -> agendamento.getColaborador().getNome())
                .setHeader("Profissional Responsável")
                .setSortable(true)
                .setAutoWidth(true)
                .setKey("colaborador");

        grid.addComponentColumn(agendamento -> {
                    DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    LocalDate data = agendamento.getData();
                    String hora = agendamento.getHorario().toString();

                    String formattedData = formatterData.format(data);
                    String dataHora = formattedData + " " + hora;
                    return new Span(dataHora);
                }).setHeader("Data/Horário")
                .setAutoWidth(true);

        grid.addComponentColumn(agendamento -> {
                    HorizontalLayout statusLayout = new HorizontalLayout();
                    statusLayout.setWidthFull();
                    statusLayout.setJustifyContentMode(JustifyContentMode.CENTER);

                    Span status = new Span(agendamento.getStatus());
                    status.getStyle().setPadding("4px");
                    status.getStyle().setColor("white");
                    status.setMinWidth("125px");
                    status.getStyle().setBorderRadius("8px");

                    switch (agendamento.getStatus()) {
                        case "AGUARDANDO":
                            status.getStyle().set("background-color", "#EFBF14");
                            break;
                        case "CONFIRMADO":
                            status.getStyle().set("background-color", "#22C55E");
                            break;
                        case "CANCELADO":
                            status.getStyle().set("background-color", "#EB3B3B");
                            break;
                        default:
                            statusLayout.getStyle().set("background-color", "black");
                    }

                    statusLayout.add(status);
                    return statusLayout;
                }).setHeader("Status")
                .setSortable(true)
                .setComparator(AgendamentoBase::getStatus)
                .setTextAlign(ColumnTextAlign.CENTER)
                .setAutoWidth(true);

        grid.addColumn(agendamento -> agendamento.getCliente().getTelefone())
                .setHeader("Contato")
                .setAutoWidth(true);

        add(headerLayout, grid);
    }

    private void loadAgendamentos() {
        List<AgendamentoBase> agendamentos = agendamentoController.getAgendamentos();
        grid.setItems(agendamentos); // Carrega os agendamentos no grid
    }


    private void filterGridByCliente(String clienteName) {
        List<AgendamentoBase> filteredList = agendamentoController.getAgendamentosByCliente(clienteName);
        grid.setItems(filteredList); // Atualiza o grid com os dados filtrados
    }

    private void filterGridByColaborador(String colaboradorName) {
        List<AgendamentoBase> filteredList = agendamentoController.getAgendamentosByColaborador(colaboradorName);
        grid.setItems(filteredList); // Atualiza o grid com os dados filtrados
    }

    private void filterGridByStatus(String status) {
        List<AgendamentoBase> filteredList = agendamentoController.getAgendamentosByStatus(status);
        grid.setItems(filteredList);
    }



}


