package br.com.atlas.bigodeira.view.colaborador;

import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.ColaboradorService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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

        H2 titulo = new H2 ("Visualizar Colaborador");

        Button cadastrarButton = new Button("Cadastrar Colaborador");
        cadastrarButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("cadastro-colaborador"));
        });
        cadastrarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Pesquisar por nome");
        searchField.addValueChangeListener(event -> filterGrid(event.getValue()));

        HorizontalLayout headerLayout = new HorizontalLayout(titulo, cadastrarButton, searchField);

        add(headerLayout);

        grid = new Grid<>(Colaborador.class, false);
        loadColaboradores();
        setHeight("80%");

        add(grid);
    }

    private void loadColaboradores() {
        List<Colaborador> colaboradores = colaboradorService.findAll();
        grid.setItems(colaboradores);

        grid.addColumn(Colaborador::getNome).setHeader("Nome").setSortable(true).setAutoWidth(true);
        grid.addColumn(Colaborador::getEspecialidade).setHeader("Especialidade").setAutoWidth(true);
        grid.addColumn(Colaborador::getDiasDaSemana).setHeader("Dias Da Semana").setAutoWidth(true);

        grid.addColumn(colaborador ->
                colaborador.getHorarioInicio() != null
                        ? colaborador.getHorarioInicio().format(DateTimeFormatter.ofPattern("HH:mm"))
                        : "Horário não definido"
        ).setHeader("Horário").setAutoWidth(true);

        grid.addComponentColumn(colaborador -> {
            Button button = new Button("Agendar");
            button.addClickListener(event -> openCalendarDialog(colaborador));
            return button;
        }).setHeader("Agendamento").setAutoWidth(true);

        grid.addComponentColumn(cliente ->{
            Icon lapis = new Icon(VaadinIcon.PENCIL);
            lapis.setColor("orange");
            Button abrirEditar = new Button(lapis);
            abrirEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Icon lixeira = new Icon(VaadinIcon.TRASH);
            lixeira.setColor("red");
            Button abrirExcluir = new Button(lixeira);
            abrirExcluir.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            HorizontalLayout buttonsLayout = new HorizontalLayout(abrirEditar, abrirExcluir);
            buttonsLayout.setJustifyContentMode(JustifyContentMode.END);

            return buttonsLayout;
        }).setAutoWidth(true);
    }

    private void filterGrid(String searchTerm) {
        List<Colaborador> filteredList = colaboradorService.findAll().stream()
                .filter(colaborador -> colaborador.getNome().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
        grid.setItems(filteredList);
    }

    private void openCalendarDialog(Colaborador colaborador) {
        Dialog dialog = new Dialog();

        H2 titulo = new H2("Agendar consulta com " + colaborador.getNome());

        DatePicker datePicker = new DatePicker("Escolha a data");
        datePicker.setWidthFull();

        ComboBox<LocalTime> timeComboBox = new ComboBox<>("Escolha o horário");
        timeComboBox.setItems(LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0),
                LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0));
        timeComboBox.setItemLabelGenerator(time -> time.format(DateTimeFormatter.ofPattern("HH:mm")));
        timeComboBox.setWidthFull();

        Button confirmButton = new Button("Confirmar Agendamento");
        confirmButton.addClickListener(e -> {
            String selectedDate = datePicker.getValue() != null ? datePicker.getValue().toString() : "data não selecionada";
            LocalTime selectedTime = timeComboBox.getValue();
            String formattedTime = selectedTime != null ? selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")) : "horário não selecionado";
            Notification.show("Agendamento realizado para: " + colaborador.getNome() + " na data: " + selectedDate + " às " + formattedTime);
            dialog.close();
        });
        confirmButton.getStyle().set("margin-right", "auto");

        Button cancelButton = new Button("Cancelar");
        cancelButton.addClickListener(e -> dialog.close());
        VerticalLayout dialogLayout = new VerticalLayout(titulo, datePicker, timeComboBox);

        dialog.getFooter().add(confirmButton, cancelButton);

        dialog.add(dialogLayout);
        dialog.open();
    }
}
