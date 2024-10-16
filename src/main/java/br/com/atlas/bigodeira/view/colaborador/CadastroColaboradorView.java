package br.com.atlas.bigodeira.view.colaborador;

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

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@PageTitle("Cadastro Colaborador")
@Route(value = "cadastro-colaborador", layout = MainLayout.class)
public class CadastroColaboradorView extends VerticalLayout {

    private final ColaboradorService colaboradorService;

    @Autowired
    public CadastroColaboradorView(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H2 titulo = new H2("Cadastro Colaborador");
        titulo.getStyle().set("margin-bottom", "20px");
        titulo.getStyle().set("text-align", "left");

        TextField nomeField = new TextField("Nome");
        nomeField.setWidthFull();

        TextField cpfField = new TextField("CPF");
        cpfField.setWidthFull();

        MultiSelectComboBox<String> especialidadeSelect = new MultiSelectComboBox<>();
        especialidadeSelect.setLabel("Especialidades");
        especialidadeSelect.setItems("Barba", "Corte", "Sobrancelha");
        especialidadeSelect.setWidthFull();

        HorizontalLayout horarioLayout = new HorizontalLayout();
        horarioLayout.setWidthFull();

        TimePicker horaInicio = new TimePicker("Horário de Início");
        TimePicker horaFim = new TimePicker("Horário de Fim");
        horaInicio.setWidth("30%");
        horaFim.setWidth("30%");

        MultiSelectComboBox<String> diasSelect = new MultiSelectComboBox<>();
        diasSelect.setLabel("Dias Disponíveis");
        List<String> dias = Arrays.asList("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira");
        diasSelect.setItems(dias);

        HorizontalLayout horarioDiasLayout = new HorizontalLayout(horaInicio, horaFim, diasSelect);
        horarioDiasLayout.setWidthFull();
        horarioDiasLayout.setSpacing(true);

        Button confirmarButton = new Button("Confirmar");
        confirmarButton.getStyle().set("margin-top", "20px");
        confirmarButton.getStyle().set("align-self", "flex-start");

        confirmarButton.addClickListener(event -> {
            Colaborador colaborador = new Colaborador();
            colaborador.setNome(nomeField.getValue());
            colaborador.setCpf(cpfField.getValue());
            colaborador.setEspecialidade(String.join(", ", especialidadeSelect.getValue()));
            colaborador.setHorario(horaInicio.getValue()); // Usar LocalTime diretamente
            colaborador.setDiasDaSemana(String.join(", ", diasSelect.getValue()));

            colaboradorService.save(colaborador);

            Notification.show("Colaborador cadastrado com sucesso!");

            nomeField.clear();
            cpfField.clear();
            especialidadeSelect.clear();
            horaInicio.clear();
            horaFim.clear();
            diasSelect.clear();
        });

        add(titulo, nomeField, cpfField, especialidadeSelect, horarioDiasLayout, confirmarButton);
        setPadding(true);
        getStyle().set("max-width", "600px");
    }
}
