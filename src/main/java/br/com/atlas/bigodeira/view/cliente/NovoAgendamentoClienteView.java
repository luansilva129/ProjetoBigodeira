package br.com.atlas.bigodeira.view.cliente;



import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.domainBase.domain.ClienteSession;
import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.backend.service.ColaboradorService;
import br.com.atlas.bigodeira.backend.service.ServiceBase;
import br.com.atlas.bigodeira.view.MainLayoutCliente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Route(value = "cliente/novo_agendamento", layout = MainLayoutCliente.class)
@PageTitle("Novo Agendamento")
public class NovoAgendamentoClienteView extends VerticalLayout {

    private final ColaboradorService colaboradorService;
    private final AgendamentoService agendamentoService;
    private final ClienteService clienteService;
    private final ServiceBase serviceBase;

    @Autowired
    public NovoAgendamentoClienteView(ColaboradorService colaboradorService, AgendamentoService agendamentoService, ClienteService clienteService, ServiceBase serviceBase) {
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
        servicoComboBox.setItems(servicos);
        servicoComboBox.setItemLabelGenerator(ServicosBase::getNome);
        servicoComboBox.setWidthFull();

        // ComboBox do cliente
        ComboBox<Cliente> clienteComboBox = new ComboBox<>("Cliente");
        Long clienteId = obterIdClienteLogado(); // Obtenha o ID do cliente logado

        if (clienteId != null) {
            clienteService.findById(clienteId).ifPresent(cliente -> {
                clienteComboBox.setItems(cliente); // Defina o cliente logado
                clienteComboBox.setValue(cliente); // Selecione o cliente no ComboBox
                clienteComboBox.setEnabled(false); // Desabilite para que o cliente não possa ser alterado
            });
        } else {
            Notification.show("Nenhum cliente logado. Por favor, faça login.", 3000, Notification.Position.MIDDLE);
        }

        Button confirmarButton = new Button("Confirmar Agendamento", event -> {
            Colaborador colaborador = colaboradorComboBox.getValue();
            LocalDate data = dataPicker.getValue();
            LocalTime horario = horarioComboBox.getValue();
            ServicosBase servico = servicoComboBox.getValue();
            Cliente cliente = clienteComboBox.getValue();

            if (colaborador == null || data == null || horario == null || servico == null || cliente == null) {
                Notification.show("Por favor, preencha todos os campos!", 3000, Notification.Position.MIDDLE);
            } else {
                ServicosBase servicoExistente = serviceBase.findById(servico.getId())
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

                AgendamentoBase agendamento = new AgendamentoBase(data, horario, servicoExistente, colaborador, cliente, "AGUARDANDO");
                agendamentoService.salvarAgendamento(agendamento);

                Notification.show(
                        "Agendamento salvo para " + colaborador.getNome() +
                                " em " + data + " às " + horario + " para " + servico.getNome() +
                                " com o cliente " + cliente.getNome(),
                        3000, Notification.Position.MIDDLE);
                clearFields(colaboradorComboBox, dataPicker, horarioComboBox, servicoComboBox);
            }
        });
        confirmarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        H2 title = new H2("Insira as informações");

        FormLayout formLayout = new FormLayout(clienteComboBox, servicoComboBox, colaboradorComboBox, dataPicker, horarioComboBox);
        formLayout.setColspan(clienteComboBox, 2);
        formLayout.setColspan(servicoComboBox, 2);
        formLayout.setColspan(colaboradorComboBox, 2);

        VerticalLayout novoAgendamentoLayout = new VerticalLayout(title, formLayout, confirmarButton);
        novoAgendamentoLayout.setPadding(true);

        add(novoAgendamentoLayout);
        setSpacing(true);
        setPadding(true);
        setWidthFull();
        setDefaultHorizontalComponentAlignment(Alignment.START);
    }

    private Long obterIdClienteLogado() {
        // Obtém o ID do cliente logado a partir da sessão
        return ClienteSession.getInstance().getClienteLogadoId();
    }

    private void clearFields(ComboBox<Colaborador> colaboradorComboBox, DatePicker dataPicker,
                             ComboBox<LocalTime> horarioComboBox, ComboBox<ServicosBase> servicoComboBox) {
        colaboradorComboBox.clear();
        dataPicker.clear();
        horarioComboBox.clear();
        servicoComboBox.clear();
    }
}