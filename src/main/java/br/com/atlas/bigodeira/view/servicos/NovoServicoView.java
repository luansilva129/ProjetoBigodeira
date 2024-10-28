package br.com.atlas.bigodeira.view.servicos;

import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import br.com.atlas.bigodeira.backend.service.ServiceBase;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
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

    private final ServiceBase serviceBase;

    public NovoServicoView(ServiceBase serviceBase) {
        this.serviceBase = serviceBase;

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
        duracaoServico.setMin(0.0);
        duracaoServico.setMax(10.0);
        duracaoServico.setWidthFull();
        duracaoServico.setStepButtonsVisible(true);

        NumberField precoField = new NumberField("Preço");
        precoField.setWidthFull();

        Div realPrefix = new Div();
        realPrefix.setText("R$");
        precoField.setPrefixComponent(realPrefix);
        precoField.setMin(0.0);
        precoField.setMax(10000.0);

        HorizontalLayout DuracaoEPrecoLayout = new HorizontalLayout(duracaoServico, precoField);
        DuracaoEPrecoLayout.setWidthFull();
        DuracaoEPrecoLayout.setSpacing(true);

        Button confirmarButton = new Button("Confirmar");
        confirmarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmarButton.getStyle().set("margin-top", "20px");

        confirmarButton.addClickListener(event -> {
            String nome = servicoField.getValue();
            String descricao = descricaoField.getValue();
            Double duracao = duracaoServico.getValue();
            Double preco = precoField.getValue();

            if (nome == "" || descricao == "" || duracao == null || preco == null) {
                Notification.show("Preencha todos os campos antes de continuar");
            } else {
                ServicosBase servico = new ServicosBase();
                servico.setNome(nome);
                servico.setDescricao(descricao);
                servico.setDuracao(duracao);
                servico.setPreco(preco);

                serviceBase.save(servico);

                Notification.show("Serviço criado com sucesso!");

                servicoField.clear();
                descricaoField.clear();
                duracaoServico.setValue(1.0);
                precoField.clear();
            }
        });

        verticalLayout.add(titulo, servicoField, descricaoField, DuracaoEPrecoLayout, confirmarButton);
        add(verticalLayout);
    }
}
