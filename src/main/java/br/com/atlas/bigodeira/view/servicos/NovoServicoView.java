package br.com.atlas.bigodeira.view.servicos;

import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Novo Serviço")
@Route(value = "novo-servico", layout = MainLayout.class)
public class NovoServicoView extends Composite<VerticalLayout> {
    public NovoServicoView() {

    }
}
