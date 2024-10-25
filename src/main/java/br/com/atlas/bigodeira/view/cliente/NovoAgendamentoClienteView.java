package br.com.atlas.bigodeira.view.cliente;


import br.com.atlas.bigodeira.view.MainLayoutCliente;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cliete/novo_agendamento", layout = MainLayoutCliente.class)
@PageTitle("Novo Agendamento")
public class NovoAgendamentoClienteView extends VerticalLayout {

    public NovoAgendamentoClienteView() {
        add("Conteúdo da página de novo agendamento");
    }
}
