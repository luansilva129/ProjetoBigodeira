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
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

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
        servicoComboBox.setItems(servicos);
        servicoComboBox.setItemLabelGenerator(ServicosBase::getNome);
        servicoComboBox.setWidthFull();

        ComboBox<Cliente> clienteComboBox = new ComboBox<>("Selecione o Cliente");
        List<Cliente> clientes = clienteService.findAll();
        clienteComboBox.setItems(clientes);
        clienteComboBox.setItemLabelGenerator(Cliente::getNome);
        clienteComboBox.setWidthFull();

        Button confirmarButton = new Button("Confirmar Agendamento", event -> {
            if (clienteComboBox.isEmpty() || servicoComboBox.isEmpty() || colaboradorComboBox.isEmpty()
                    || dataPicker.isEmpty()  || horarioComboBox.isEmpty()) {
                Notification.show("Por favor, preencha todos os campos!");
            } else {
                AgendamentoBase agendamento = new AgendamentoBase(dataPicker.getValue(), horarioComboBox.getValue(),
                        servicoComboBox.getValue(), colaboradorComboBox.getValue(), clienteComboBox.getValue(), "AGUARDANDO");
                agendamentoService.salvarAgendamento(agendamento);

                Notification.show(
                        "Agendamento salvo para " + colaboradorComboBox.getValue() +
                                " no dia " + dataPicker.getValue() + " às " + horarioComboBox.getValue() + " para "
                                + servicoComboBox.getValue() + " com o cliente " + clienteComboBox.getValue(),
                        6000, Notification.Position.MIDDLE);
                clearFields(colaboradorComboBox, dataPicker, horarioComboBox, servicoComboBox, clienteComboBox);
            }
        });
        confirmarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmarButton.getStyle().setMargin("20px 0px 4px");

        H2 title = new H2("Insira as informações");

        HorizontalLayout dataHoraLayout = new HorizontalLayout(dataPicker, horarioComboBox);
        dataHoraLayout.setWidthFull();

        VerticalLayout novoAgendamentoLayout = new VerticalLayout(title, clienteComboBox, servicoComboBox,
                colaboradorComboBox, dataHoraLayout, confirmarButton);

        add(novoAgendamentoLayout);
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
