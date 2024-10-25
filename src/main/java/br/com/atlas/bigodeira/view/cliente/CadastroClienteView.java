package br.com.atlas.bigodeira.view.cliente;

import br.com.atlas.bigodeira.view.MainLayoutCliente;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cliente/cadastroView", layout = MainLayoutCliente.class)
@PageTitle("Cadastro - Cliente")
public class CadastroClienteView extends VerticalLayout {
    // Implementação do cadastro do cliente
}
