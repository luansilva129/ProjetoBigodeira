package br.com.atlas.bigodeira.view.servicos;

import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import br.com.atlas.bigodeira.backend.service.ServiceBase;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Visualizar Serviços")
@Route(value = "servicos", layout = MainLayout.class)
public class VisualizarServicosView extends VerticalLayout {

    private final Grid<ServicosBase> grid;
    private final ServiceBase serviceBase;

    @Autowired
    public VisualizarServicosView(ServiceBase serviceBase) {
        this.serviceBase = serviceBase;

        HorizontalLayout headerLayout = new HorizontalLayout();

        headerLayout.add(new H2("Serviços"));
        headerLayout.setWidthFull();

        Button novoServicoButton = new Button("Criar Serviço");
        novoServicoButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("novo-servico"));
        });
        novoServicoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        headerLayout.add(novoServicoButton);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Pesquisar Serviço");
        searchField.setSuffixComponent(VaadinIcon.SEARCH.create());
        searchField.addValueChangeListener(event -> filterGrid(event.getValue()));

        HorizontalLayout searchLayout = new HorizontalLayout(searchField);
        searchLayout.setWidthFull();
        searchLayout.addClassName(LumoUtility.JustifyContent.END);
        headerLayout.add(searchLayout);

        add(headerLayout);

        grid = new Grid<>(ServicosBase.class, false);
        loadServicos();

        add(grid);
    }

    private void loadServicos() {
        List<ServicosBase> servicos = serviceBase.findAll();
        grid.setItems(servicos);

        grid.addColumn(ServicosBase::getNome).setHeader("Serviços");
        grid.addColumn(ServicosBase::getDescricao).setHeader("Descrição");
        grid.addColumn(ServicosBase::getDuracao).setHeader("Duração");
        grid.addColumn(new NumberRenderer<>(
                ServicosBase::getPreco, NumberFormat.getCurrencyInstance()
        )).setHeader("Preço");

        grid.addComponentColumn(servicosBase ->{
            HorizontalLayout buttonsLayout = new HorizontalLayout();

            Icon lapis = new Icon(VaadinIcon.PENCIL);
            lapis.setColor("orange");
            Button abrirEditar = new Button(lapis, event -> openDialogEditar(servicosBase));
            abrirEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            buttonsLayout.add(abrirEditar);

            Icon lixeira = new Icon(VaadinIcon.TRASH);
            lixeira.setColor("red");
            Button abrirExcluir = new Button(lixeira, event -> openDialogExcluir(servicosBase));
            abrirExcluir.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            buttonsLayout.add(abrirExcluir);

            return buttonsLayout;
        });
    }

    private void openDialogEditar(ServicosBase servicosBase) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Editar Serviço "+servicosBase.getNome());

        FormLayout editarInputsLayout = new FormLayout();
        TextField servicoField = new TextField("Serviço");
        servicoField.setValue(servicosBase.getNome());
        editarInputsLayout.setColspan(servicoField, 2);

        TextField descricaoField = new TextField("Descrição");
        descricaoField.setValue(servicosBase.getDescricao());
        editarInputsLayout.setColspan(descricaoField, 2);

        NumberField duracaoServico = new NumberField("Duração (Em horas)");
        duracaoServico.setValue(servicosBase.getDuracao());
        duracaoServico.setStep(0.5);
        duracaoServico.setMin(0.0);
        duracaoServico.setMax(10.0);
        duracaoServico.setStepButtonsVisible(true);

        NumberField precoField = new NumberField("Preço");
        precoField.setValue(servicosBase.getPreco());

        Div realPrefix = new Div();
        realPrefix.setText("R$");
        precoField.setPrefixComponent(realPrefix);
        precoField.setMin(0.0);
        precoField.setMax(10000.0);

        editarInputsLayout.add(servicoField, descricaoField, duracaoServico, precoField);
        dialog.add(editarInputsLayout);

        Button salvar = new Button("Salvar", event -> {
            Long id = servicosBase.getId();
            String nome = servicoField.getValue();
            String descricao = descricaoField.getValue();
            Double duracao = duracaoServico.getValue();
            Double preco = precoField.getValue();

            if (nome == null || descricao == null || duracao == null || preco == null) {
                Notification.show("Preencha todos os campos antes de continuar");
            } else {
                ServicosBase servico = new ServicosBase();
                servico.setId(id);
                servico.setNome(nome);
                servico.setDescricao(descricao);
                servico.setDuracao(duracao);
                servico.setPreco(preco);

                serviceBase.save(servico);

                refreshGrid(grid);
                dialog.close();

                Notification.show("Serviço editado com sucesso!");
            }
        });
        salvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvar.getStyle().set("margin-right", "auto");

        Button cancelar = new Button("Cancelar", event -> dialog.close());
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(salvar, cancelar);

        dialog.open();
    }

    private void openDialogExcluir(ServicosBase servicosBase) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Excluir serviço "+servicosBase.getNome()+"?");

        dialog.add("O serviço será excluido permanentemente, tem certeza que deseja continuar?");

        Button excluir = new Button("Excluir", event -> {
            try {
                Long id = servicosBase.getId();
                serviceBase.delete(id);
                refreshGrid(grid);
                dialog.close();
                Notification.show("Serviço excluido com sucesso!");

            } catch (Exception e) {
                Notification.show("Serviço vinculado à um agendamento, portanto não pode ser excluido!");
            }
        });
        excluir.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        excluir.getStyle().set("margin-right", "auto");

        Button cancelar = new Button("Cancelar", event -> dialog.close());
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(excluir, cancelar);

        dialog.open();
    }

    private void filterGrid(String searchTerm) {
        List<ServicosBase> filteredList = serviceBase.findAll().stream()
                .filter(servicosBase -> servicosBase.getNome().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
        grid.setItems(filteredList);
    }

    private void refreshGrid(Grid<ServicosBase> grid) { grid.setItems(serviceBase.findAll());
    }
}

