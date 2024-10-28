package br.com.atlas.bigodeira.view.agendamentos;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Validar Agendamentos")
@Route(value = "agendamentos", layout = MainLayout.class)
public class VisualizarAgendamentosView extends VerticalLayout {

    private final Grid<AgendamentoBase> grid;
    private final AgendamentoService agendamentoService;

    @Autowired
    public VisualizarAgendamentosView(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;

        //Novo agendamento e filtro
        HorizontalLayout headerLayout = new HorizontalLayout();

        Button novoServicoButton = new Button("Novo Agendamento");
        novoServicoButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("novo-agendamento"));
        });
        novoServicoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        headerLayout.add(novoServicoButton);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Pesquisar por Nome");
        searchField.setSuffixComponent(VaadinIcon.SEARCH.create());
        searchField.addValueChangeListener(event -> filterGrid(event.getValue()));
        headerLayout.add(searchField);

        //Tabela Agendamentos
        grid = new Grid<>(AgendamentoBase.class, false);
        loadAgendamentos();

        //Botões
        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Button confirmar = new Button("Confirmar");
        confirmar.addThemeVariants(ButtonVariant.LUMO_PRIMARY ,ButtonVariant.LUMO_SUCCESS);
        confirmar.setSuffixComponent(new Icon(VaadinIcon.CHECK));
        confirmar.setWidth("60%");
        confirmar.setEnabled(true);

        confirmar.addClickListener(event -> confirmarAgendamento(grid.getSelectedItems()));

        Button recusar = new Button("Recusar");
        recusar.addThemeVariants(ButtonVariant.LUMO_PRIMARY ,ButtonVariant.LUMO_ERROR);
        recusar.setSuffixComponent(new Icon(VaadinIcon.CLOSE));
        recusar.setWidth("60%");
        recusar.setEnabled(true);

        recusar.addClickListener(event -> recusarAgendamento(grid.getSelectedItems()));

        buttonsLayout.add(confirmar, recusar);

        add(headerLayout, grid, buttonsLayout);
    }

    private void loadAgendamentos() {
        List<AgendamentoBase> agendamentos = agendamentoService.findAllAgendamentos();
        grid.setItems(agendamentos);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        grid.addColumn(agendamento -> agendamento.getCliente().getNome()).setHeader("Nome").setSortable(true);
        grid.addColumn(agendamento -> agendamento.getServicosBase().getNome()).setHeader("Serviço").setSortable(true);
        grid.addColumn(agendamento -> agendamento.getColaborador().getNome()).setHeader("Profissional Responsável").setSortable(true);

        grid.addComponentColumn(agendamento -> {
            DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate data = agendamento.getData();
            String hora = agendamento.getHorario().toString();

            String formattedData = formatterData.format(data);

            String dataHora = formattedData+" "+hora;
            return new Span(dataHora);
        }).setHeader("Data/Horário").setWidth("10rem").setFlexGrow(0);

        grid.addComponentColumn(agendamento -> {
            HorizontalLayout statusLayout = new HorizontalLayout();
            statusLayout.setWidthFull();
            statusLayout.setJustifyContentMode(JustifyContentMode.CENTER);

            Span status = new Span(agendamento.getStatus());
            status.getStyle().setPadding("4px");
            status.getStyle().setColor("white");
            status.setWidth("50%");
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
        }).setHeader("Status").setSortable(true).setComparator(AgendamentoBase::getStatus).setTextAlign(ColumnTextAlign.CENTER).setWidth("15rem").setFlexGrow(0);

        grid.addColumn(agendamento -> agendamento.getCliente().getTelefone()).setHeader("Contato").setWidth("15rem").setFlexGrow(0);
    }

    private void filterGrid(String searchTerm) {
        List<AgendamentoBase> filteredList = agendamentoService.findAllAgendamentos().stream()
                .filter(agendamento -> agendamento.getCliente().getNome().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
        grid.setItems(filteredList);
    }

    private void confirmarAgendamento(Set<AgendamentoBase> selecionados) {
        if (selecionados.isEmpty()) {
            Notification.show("Nenhum agendamento selecionado.");
        } else {
            selecionados.forEach(agendamento -> {
                agendamento.setId(agendamento.getId());
                agendamento.setStatus("CONFIRMADO");
                agendamentoService.salvarAgendamento(agendamento);
            });
            refreshGrid(grid);

            Notification.show(selecionados.size() + " agendamento(s) confirmado(s).");
        }
    }

    private void recusarAgendamento(Set<AgendamentoBase> selecionados) {
        if (selecionados.isEmpty()) {
            Notification.show("Nenhum agendamento selecionado.");
        } else {
            selecionados.forEach(agendamento -> {
                agendamento.setId(agendamento.getId());
                agendamento.setStatus("CANCELADO");
                agendamentoService.salvarAgendamento(agendamento);
            });
            refreshGrid(grid);

            Notification.show(selecionados.size() + " agendamento(s) cancelado(s).");
        }
    }

    // Método para atualizar os itens do grid
    private void refreshGrid(Grid<AgendamentoBase> grid) {
        grid.setItems(agendamentoService.findAllAgendamentos());
    }
}
