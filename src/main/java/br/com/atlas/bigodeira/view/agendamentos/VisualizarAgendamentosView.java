package br.com.atlas.bigodeira.view.agendamentos;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Agendamentos")
@Route(value = "agendamentos", layout = MainLayout.class)
public class  VisualizarAgendamentosView extends VerticalLayout {

    private final AgendamentoService agendamentoService;

    @Autowired
    public VisualizarAgendamentosView(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
        setupUI();
    }

    private void setupUI() {
        Grid<AgendamentoBase> grid = new Grid<>(AgendamentoBase.class, false);
        grid.setItems(agendamentoService.findAllAgendamentos());

        grid.addColumn("data").setHeader("Data");
        grid.addColumn("horario").setHeader("Horário");

        grid.addColumn(agendamento -> {
            Colaborador colaborador = agendamento.getColaborador();
            return colaborador != null ? colaborador.getNome() : "Colaborador não disponível";
        }).setHeader("Colaborador");

        grid.addColumn(agendamento ->
                agendamento.getStatus() ? "Confirmado" : "Pendente"
        ).setHeader("Status");

        grid.addComponentColumn(agendamento -> createActionButtons(agendamento, grid))
                .setHeader("Ações");

        add(grid);
        setSpacing(true);
        setPadding(true);
        setWidth("100%");
    }

    private HorizontalLayout createActionButtons(AgendamentoBase agendamento, Grid<AgendamentoBase> grid) {
        Button confirmarButton = new Button("Confirmar", event -> {
            agendamentoService.confirmarAgendamento(agendamento.getId());
            refreshGrid(grid);
            Notification.show("Agendamento confirmado!", 3000, Notification.Position.MIDDLE);
        });

        Button cancelarButton = new Button("Cancelar", event -> {
            agendamentoService.cancelarAgendamento(agendamento.getId());
            refreshGrid(grid);
            Notification.show("Agendamento cancelado!", 3000, Notification.Position.MIDDLE);
        });

        return new HorizontalLayout(confirmarButton, cancelarButton);
    }

    private void refreshGrid(Grid<AgendamentoBase> grid) {
        grid.setItems(agendamentoService.findAllAgendamentos());
    }
}
