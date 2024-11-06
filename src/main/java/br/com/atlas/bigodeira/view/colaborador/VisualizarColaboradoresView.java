package br.com.atlas.bigodeira.view.colaborador;

import br.com.atlas.bigodeira.backend.controller.colaborador.CadastrarColaboradorController;
import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.ColaboradorService;
import br.com.atlas.bigodeira.backend.service.ServicoService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Colaboradores")
@Route(value = "colaboradores", layout = MainLayout.class)
public class VisualizarColaboradoresView extends VerticalLayout {
    private final ColaboradorService colaboradorService;
    private final Grid<Colaborador> grid;
    private final CadastrarColaboradorController cadastrarColaboradorController;
    private final ServicoService servicoService;

    @Autowired
    public VisualizarColaboradoresView(ColaboradorService colaboradorService, CadastrarColaboradorController cadastrarColaboradorController, ServicoService servicoService) {
        this.colaboradorService = colaboradorService;
        this.cadastrarColaboradorController = cadastrarColaboradorController;
        this.servicoService = servicoService;

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
                colaborador.getHorarioInicio()+" às "+colaborador.getHorarioFim()
        ).setHeader("Horário").setAutoWidth(true);

        grid.addComponentColumn(colaborador -> {
            Button button = new Button("Agendar");
            button.addClickListener(event -> openCalendarDialog(colaborador));
            return button;
        }).setHeader("Agendamento").setAutoWidth(true);

        grid.addComponentColumn(colaborador ->{
            Icon lapis = new Icon(VaadinIcon.PENCIL);
            lapis.setColor("orange");
            Button abrirEditar = new Button(lapis, event -> openDialogEditar(colaborador));
            abrirEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Icon lixeira = new Icon(VaadinIcon.TRASH);
            lixeira.setColor("red");
            Button abrirExcluir = new Button(lixeira, event -> openDialogExcluir(colaborador));
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

    private void openDialogEditar(Colaborador colaborador) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Editar Colaborador "+colaborador.getNome());

        TextField nomeField = new TextField("Nome");
        nomeField.setValue(colaborador.getNome());

        TextField cpfField = new TextField("CPF");
        cpfField.setValue(colaborador.getCpf());
        cpfField.setEnabled(false);

        MultiSelectComboBox<String> especialidadeSelect = new MultiSelectComboBox<>("Especialidades");

        List<ServicosBase> servicos = servicoService.findAll();
        List<String> nomeServicos = new ArrayList<>();
        servicos.forEach(servico -> nomeServicos.add(servico.getNome()));
        especialidadeSelect.setItems(nomeServicos);
        especialidadeSelect.select(colaborador.getEspecialidade().split(","));

        FormLayout formLayout = new FormLayout(nomeField, cpfField, especialidadeSelect);
        formLayout.setColspan(nomeField,2);
        formLayout.setColspan(cpfField, 2);
        formLayout.setColspan(especialidadeSelect, 2);

        TimePicker horaInicio = new TimePicker("Horário de Início");
        horaInicio.setMin(LocalTime.of(8, 0));
        horaInicio.setMax(LocalTime.of(22, 0));
        horaInicio.setValue(colaborador.getHorarioInicio());

        TimePicker horaFim = new TimePicker("Horário de Fim");
        horaFim.setMin(horaInicio.getValue());
        horaInicio.addValueChangeListener(e -> horaFim.setMin(horaInicio.getValue()));
        horaFim.setMax(LocalTime.of(22, 0));
        horaFim.setValue(colaborador.getHorarioFim());

        MultiSelectComboBox<String> diasSelect = new MultiSelectComboBox<>("Dias Disponíveis");
        diasSelect.setItems("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira");
        diasSelect.select(colaborador.getDiasDaSemana().split(","));

        HorizontalLayout horarioDiasLayout = new HorizontalLayout(horaInicio, horaFim, diasSelect);
        horarioDiasLayout.setWidthFull();
        horarioDiasLayout.setSpacing(true);

        dialog.add(formLayout, horarioDiasLayout);

        Button salvar = new Button("Salvar", event -> {
            if (nomeField.isEmpty() || cpfField.isEmpty() || especialidadeSelect.isEmpty() || diasSelect.isEmpty()
                    || horaInicio.isEmpty() || horaFim.isEmpty()) {
                Notification.show("Preencha todos os campos antes de continuar");
            } else {
                Colaborador novoColaborador = new Colaborador();
                novoColaborador.setId(colaborador.getId());
                novoColaborador.setNome(nomeField.getValue());
                novoColaborador.setCpf(cpfField.getValue());
                novoColaborador.setEspecialidade(String.join(", ", especialidadeSelect.getValue()));
                novoColaborador.setHorarioInicio(horaInicio.getValue());
                novoColaborador.setHorarioFim(horaFim.getValue());
                novoColaborador.setDiasDaSemana(String.join(", ", diasSelect.getValue()));

                cadastrarColaboradorController.salvarColaborador(novoColaborador);

                refreshGrid(grid);
                dialog.close();

                Notification.show("Colaborador editado com sucesso!");
            }
        });
        salvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvar.getStyle().set("margin-right", "auto");

        Button cancelar = new Button("Cancelar", event -> dialog.close());
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(salvar, cancelar);

        dialog.open();
    }

    private void openDialogExcluir(Colaborador colaborador) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Excluir colaborador "+colaborador.getNome()+"?");

        dialog.add("O colaborador será excluido permanentemente, tem certeza que deseja continuar?");

        Button excluir = new Button("Excluir", event -> {
            try {
                cadastrarColaboradorController.deleteColaborador(colaborador);
                refreshGrid(grid);
                dialog.close();
                Notification.show("Colaborador excluido com sucesso!");

            } catch (Exception e) {
                Notification.show("Colaborador vinculado à um agendamento, portanto não pode ser excluido!");
            }
        });
        excluir.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        excluir.getStyle().set("margin-right", "auto");

        Button cancelar = new Button("Cancelar", event -> dialog.close());
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(excluir, cancelar);

        dialog.open();
    }

    private void refreshGrid(Grid<Colaborador> grid) {
        grid.setItems(colaboradorService.findAll());
    }
}
