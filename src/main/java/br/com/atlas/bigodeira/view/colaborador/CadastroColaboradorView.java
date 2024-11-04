package br.com.atlas.bigodeira.view.colaborador;

import br.com.atlas.bigodeira.backend.controller.colaborador.CadastrarColaboradorController;
import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.ColaboradorService; // Importar seu serviço de colaboradores
import br.com.atlas.bigodeira.backend.service.ServicoService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

import java.util.ArrayList;
import java.util.List;

@PageTitle("Cadastro Colaborador")
@Route(value = "cadastro-colaborador", layout = MainLayout.class)
public class CadastroColaboradorView extends VerticalLayout {

    private final ColaboradorService colaboradorService;
    private final CadastrarColaboradorController cadastrarColaboradorController;
    private final ServicoService servicoService;

    @Autowired
    public CadastroColaboradorView(ColaboradorService colaboradorService, CadastrarColaboradorController cadastrarColaboradorController, ServicoService servicoService) {
        this.colaboradorService = colaboradorService;
        this.cadastrarColaboradorController = cadastrarColaboradorController;
        this.servicoService = servicoService;

        H2 titulo = new H2("Cadastro Colaborador");

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
        especialidadeSelect.setWidthFull();

        List<ServicosBase> servicos = servicoService.findAll();
        List<String> nomeServicos = new ArrayList<>();
        servicos.forEach(servico -> {
            nomeServicos.add(servico.getNome());
        });
        especialidadeSelect.setItems(nomeServicos);

        TimePicker horaInicio = new TimePicker("Horário de Início");
        horaInicio.setWidthFull();

        TimePicker horaFim = new TimePicker("Horário de Fim");
        horaFim.setWidthFull();

        MultiSelectComboBox<String> diasSelect = new MultiSelectComboBox<>("Dias Disponíveis");
        diasSelect.setItems("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira");
        diasSelect.setWidthFull();

        HorizontalLayout horarioDiasLayout = new HorizontalLayout(horaInicio, horaFim, diasSelect);
        horarioDiasLayout.setWidthFull();
        horarioDiasLayout.setSpacing(true);

        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.getStyle().setMargin("20px 0px 4px");
        cadastrarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cadastrarButton.addClickListener(event -> {
            if (nomeField.isEmpty() || cpfField.isEmpty() || especialidadeSelect.isEmpty() || diasSelect.isEmpty()
            || horaInicio.isEmpty() || horaFim.isEmpty()) {
                Notification.show("Preencha todos os campos antes de continuar");
            } else {
                Colaborador colaborador = new Colaborador();
                colaborador.setNome(nomeField.getValue());
                colaborador.setCpf(cpfField.getValue());
                colaborador.setEspecialidade(String.join(", ", especialidadeSelect.getValue()));
                colaborador.setHorarioInicio(horaInicio.getValue());
                colaborador.setHorarioFim(horaFim.getValue());
                colaborador.setDiasDaSemana(String.join(", ", diasSelect.getValue()));

                cadastrarColaboradorController.salvarColaborador(colaborador);

                Notification.show("Colaborador cadastrado com sucesso!");

                nomeField.clear();
                cpfField.clear();
                especialidadeSelect.clear();
                horaInicio.clear();
                horaFim.clear();
                diasSelect.clear();
            }
        });

        VerticalLayout verticalLayout = new VerticalLayout(titulo, nomeField, cpfField, especialidadeSelect, horarioDiasLayout, cadastrarButton);
        add(verticalLayout);
    }

    private String formatCpf(String value) {

        String digits = value.replaceAll("\\D", "");

        if (digits.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 dígitos.");
        }

        return digits.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
