package br.com.atlas.bigodeira.view.servicos;

import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

@PageTitle("Novo Serviço")
@Route(value = "novo-servico", layout = MainLayout.class)
public class NovoServicoView extends VerticalLayout {

    public NovoServicoView() {

        VerticalLayout verticalLayout = new VerticalLayout();

        setSizeFull();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.START);
        setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        setPadding(true);

        H2 titulo = new H2("Criar Serviço");
        titulo.getStyle().set("text-align", "left");

        TextField servicoField = new TextField("Serviço");
        servicoField.setWidthFull();

        TextField descricaoField = new TextField("Descrição");
        descricaoField.setWidthFull();

        NumberField duracaoServico = new NumberField();
        duracaoServico.setLabel("Duração (Em horas)");
        duracaoServico.setStep(0.5);
        duracaoServico.setValue(1.0);
        duracaoServico.setWidthFull();
        duracaoServico.setStepButtonsVisible(true);
        add(duracaoServico);

        NumberField precoField = new NumberField("Preço");
        precoField.setWidthFull();

        Div realPrefix = new Div();
        realPrefix.setText("R$");
        precoField.setPrefixComponent(realPrefix);
        precoField.setValue(0.0);
        precoField.setStep(0.01);
        precoField.setMin(0.0);
        precoField.setMax(10000.0);

        HorizontalLayout DuracaoEPrecoLayout = new HorizontalLayout(duracaoServico, precoField);
        DuracaoEPrecoLayout.setWidthFull();
        DuracaoEPrecoLayout.setSpacing(true);

        Button confirmarButton = new Button("Confirmar");
        confirmarButton.getStyle().set("align-self", "flex-start");
        confirmarButton.getStyle().set("margin-top", "20px");

        verticalLayout.add(titulo, servicoField, descricaoField, DuracaoEPrecoLayout, confirmarButton);
        add(verticalLayout);
    }
}
