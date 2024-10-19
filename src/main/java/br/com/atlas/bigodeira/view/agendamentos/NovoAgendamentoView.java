package br.com.atlas.bigodeira.view.agendamentos;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.backend.service.ColaboradorService;
import br.com.atlas.bigodeira.backend.service.ServiceBase;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@PageTitle("Novo Agendamento")
@Route(value = "novo-agendamento", layout = MainLayout.class)
public class NovoAgendamentoView extends VerticalLayout {

    private final ColaboradorService colaboradorService;
    private final AgendamentoService agendamentoService;
    private final ClienteService clienteService;
    private final ServiceBase serviceBase;

    @Autowired
    public NovoAgendamentoView(ColaboradorService colaboradorService, AgendamentoService agendamentoService, ClienteService clienteService, ServiceBase serviceBase) {
        this.colaboradorService = colaboradorService;
        this.agendamentoService = agendamentoService;
        this.clienteService = clienteService;
        this.serviceBase = serviceBase;
        setupUI();
    }

    private void setupUI() {
        ComboBox<Colaborador> colaboradorComboBox = new ComboBox<>("Selecione o Colaborador");
        List<Colaborador> colaboradores = colaboradorService.findAll();
        colaboradorComboBox.setItems(colaboradores);
        colaboradorComboBox.setItemLabelGenerator(Colaborador::getNome);
        colaboradorComboBox.setWidthFull();

        DatePicker dataPicker = new DatePicker("Escolha a Data");
        dataPicker.setWidthFull();

        ComboBox<LocalTime> horarioComboBox = new ComboBox<>("Escolha o Horário");
        horarioComboBox.setItems(
                LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0),
                LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0)
        );
        horarioComboBox.setWidthFull();

        ComboBox<ServicosBase> servicoComboBox = new ComboBox<>("Escolha o Tipo de Serviço");
        List<ServicosBase> servicos = serviceBase.findAll();
        servicoComboBox.setItems(servicos); // Alterado
        servicoComboBox.setItemLabelGenerator(ServicosBase::getNome);
        servicoComboBox.setWidthFull();

        ComboBox<Cliente> clienteComboBox = new ComboBox<>("Selecione o Cliente");
        List<Cliente> clientes = clienteService.findAll();
        clienteComboBox.setItems(clientes);
        clienteComboBox.setItemLabelGenerator(Cliente::getNome);
        clienteComboBox.setWidthFull();

        Button confirmarButton = new Button("Confirmar Agendamento", event -> {
            Colaborador colaborador = colaboradorComboBox.getValue();
            LocalDate data = dataPicker.getValue();
            LocalTime horario = horarioComboBox.getValue();
            ServicosBase servico = servicoComboBox.getValue();
            Cliente cliente = clienteComboBox.getValue();

            if (colaborador == null || data == null || horario == null || servico == null || cliente == null) {
                Notification.show("Por favor, preencha todos os campos!", 3000, Notification.Position.MIDDLE);
            } else {
                // Buscar o serviço existente do banco de dados
                ServicosBase servicoExistente = serviceBase.findById(servico.getId())
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

                AgendamentoBase agendamento = new AgendamentoBase(data, horario, servicoExistente, colaborador, cliente, false);
                agendamentoService.salvarAgendamento(agendamento);

                Notification.show(
                        "Agendamento salvo para " + colaborador.getNome() +
                                " em " + data + " às " + horario + " para " + servico.getNome() +
                                " com o cliente " + cliente.getNome(),
                        3000, Notification.Position.MIDDLE);
                clearFields(colaboradorComboBox, dataPicker, horarioComboBox, servicoComboBox, clienteComboBox);
            }
        });

        add(colaboradorComboBox, dataPicker, horarioComboBox, servicoComboBox, clienteComboBox, confirmarButton);
        setSpacing(true);
        setPadding(true);
        setWidth("50%");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void clearFields(ComboBox<Colaborador> colaboradorComboBox, DatePicker dataPicker,
                             ComboBox<LocalTime> horarioComboBox, ComboBox<ServicosBase> servicoComboBox, ComboBox<Cliente> clienteComboBox) {
        colaboradorComboBox.clear();
        dataPicker.clear();
        horarioComboBox.clear();
        servicoComboBox.clear();
        clienteComboBox.clear();
    }
}
