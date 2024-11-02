package br.com.atlas.bigodeira.view.colaborador;

import br.com.atlas.bigodeira.backend.controller.colaborador.CadastrarColaboradorController;
import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.ColaboradorService; // Importar seu serviço de colaboradores
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.List;

@PageTitle("Cadastro Colaborador")
@Route(value = "cadastro-colaborador", layout = MainLayout.class)
public class CadastroColaboradorView extends VerticalLayout {

    private final ColaboradorService colaboradorService;
    private final CadastrarColaboradorController cadastrarColaboradorController;

    @Autowired
    public CadastroColaboradorView(ColaboradorService colaboradorService, CadastrarColaboradorController cadastrarColaboradorController) {
        this.colaboradorService = colaboradorService;

        VerticalLayout verticalLayout = new VerticalLayout();

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.START);
        setJustifyContentMode(JustifyContentMode.START);
        setPadding(true);

        H2 titulo = new H2("Cadastro Colaborador");
        titulo.getStyle().set("text-align", "left");

        TextField nomeField = new TextField("Nome");
        nomeField.setWidthFull();

        TextField cpfField = new TextField("CPF");
        cpfField.setWidthFull();

        cpfField.addValueChangeListener(event -> {
            String value = event.getValue().replaceAll("[^0-9]", "");
            if (value.length() > 11) {
                value = value.substring(0, 11);
            }
            cpfField.setValue(formatCpf(value));
        });

        MultiSelectComboBox<String> especialidadeSelect = new MultiSelectComboBox<>();
        especialidadeSelect.setLabel("Especialidades");
        especialidadeSelect.setItems("Barba", "Corte", "Sobrancelha");
        especialidadeSelect.setWidthFull();

        TimePicker horaInicio = new TimePicker("Horário de Início");
        horaInicio.setWidthFull();
        horaInicio.setMinWidth("0px");
        TimePicker horaFim = new TimePicker("Horário de Fim");
        horaFim.setWidthFull();
        horaFim.setMinWidth("0px");

        MultiSelectComboBox<String> diasSelect = new MultiSelectComboBox<>();
        diasSelect.setLabel("Dias Disponíveis");
        List<String> dias = Arrays.asList("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira");
        diasSelect.setItems(dias);
        diasSelect.setWidthFull();
        diasSelect.setMinWidth("0px");

        HorizontalLayout horarioDiasLayout = new HorizontalLayout(horaInicio, horaFim, diasSelect);
        horarioDiasLayout.setWidthFull();
        horarioDiasLayout.setSpacing(true);

        Button confirmarButton = new Button("Confirmar");
        confirmarButton.getStyle().set("align-self", "flex-start");

        confirmarButton.addClickListener(event -> {
            Colaborador colaborador = new Colaborador();
            colaborador.setNome(nomeField.getValue());
            colaborador.setCpf(cpfField.getValue());
            colaborador.setEspecialidade(String.join(", ", especialidadeSelect.getValue()));
            colaborador.setHorario(horaInicio.getValue());
            colaborador.setDiasDaSemana(String.join(", ", diasSelect.getValue()));

            cadastrarColaboradorController.salvarColaborador(colaborador);

            Notification.show("Colaborador cadastrado com sucesso!");

            nomeField.clear();
            cpfField.clear();
            especialidadeSelect.clear();
            horaInicio.clear();
            horaFim.clear();
            diasSelect.clear();
        });

        verticalLayout.add(titulo, nomeField, cpfField, especialidadeSelect, horarioDiasLayout, confirmarButton);
        add(verticalLayout);
        this.cadastrarColaboradorController = cadastrarColaboradorController;
    }

    private String formatCpf(String value) {

        String digits = value.replaceAll("\\D", "");

        if (digits.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 dígitos.");
        }

        return digits.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
