package br.com.atlas.bigodeira.view.colaborador;

import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.ColaboradorService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Colaboradores")
@Route(value = "colaboradores", layout = MainLayout.class)
public class VisualizarColaboradoresView extends VerticalLayout {
    private final ColaboradorService colaboradorService;
    private final Grid<Colaborador> grid;

    @Autowired
    public VisualizarColaboradoresView(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;

        HorizontalLayout headerLayout = new HorizontalLayout();

        headerLayout.add(new com.vaadin.flow.component.html.H2("Visualizar Colaborador"));

        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("cadastro-colaborador"));
        });
        headerLayout.add(cadastrarButton);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Pesquisar por nome");
        searchField.addValueChangeListener(event -> filterGrid(event.getValue()));
        headerLayout.add(searchField);

        add(headerLayout);

        grid = new Grid<>(Colaborador.class);
        loadColaboradores();

        add(grid);
    }

    private void loadColaboradores() {
        List<Colaborador> colaboradores = colaboradorService.findAll();
        grid.setItems(colaboradores);

        grid.setColumns("nome", "especialidade", "diasDaSemana");

        grid.addColumn(colaborador ->
                colaborador.getHorario() != null
                        ? colaborador.getHorario().format(DateTimeFormatter.ofPattern("HH:mm"))
                        : "Horário não definido"
        ).setHeader("Horário");

        grid.addComponentColumn(colaborador -> {
            Button button = new Button("Agendar");
            button.addClickListener(event -> openCalendarDialog(colaborador));
            return button;
        }).setHeader("Agendamento");
    }

    private void filterGrid(String searchTerm) {
        List<Colaborador> filteredList = colaboradorService.findAll().stream()
                .filter(colaborador -> colaborador.getNome().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
        grid.setItems(filteredList);
    }

    private void openCalendarDialog(Colaborador colaborador) {
        Dialog dialog = new Dialog();

        VerticalLayout dialogLayout = new VerticalLayout();

        dialogLayout.add(new com.vaadin.flow.component.html.H2("Agendar consulta com " + colaborador.getNome()));

        DatePicker datePicker = new DatePicker("Escolha a data");
        dialogLayout.add(datePicker);

        ComboBox<LocalTime> timeComboBox = new ComboBox<>("Escolha o horário");
        timeComboBox.setItems(LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0),
                LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0));
        timeComboBox.setItemLabelGenerator(time -> time.format(DateTimeFormatter.ofPattern("HH:mm")));
        dialogLayout.add(timeComboBox);

        Button confirmButton = new Button("Confirmar Agendamento");
        confirmButton.addClickListener(e -> {
            String selectedDate = datePicker.getValue() != null ? datePicker.getValue().toString() : "data não selecionada";
            LocalTime selectedTime = timeComboBox.getValue();
            String formattedTime = selectedTime != null ? selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")) : "horário não selecionado";
            Notification.show("Agendamento realizado para: " + colaborador.getNome() + " na data: " + selectedDate + " às " + formattedTime);
            dialog.close();
        });
        dialogLayout.add(confirmButton);

        Button cancelButton = new Button("Cancelar");
        cancelButton.addClickListener(e -> dialog.close());
        dialogLayout.add(cancelButton);

        dialog.add(dialogLayout);
        dialog.open();
    }
}
