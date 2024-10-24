package br.com.atlas.bigodeira.view.acessos;

import br.com.atlas.bigodeira.backend.domainBase.AcessoBase;
import br.com.atlas.bigodeira.backend.service.AcessosService;
import br.com.atlas.bigodeira.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.function.Consumer;

@PageTitle("Consulta de Acessos")
@Route(value = "acessos", layout = MainLayout.class)
public class AcessosView extends VerticalLayout {

    private final Grid<AcessoBase> grid;
    private final AcessosService acessosService;

    public AcessosView(AcessosService acessosService) {
        this.acessosService = acessosService;

        grid = new Grid<>(AcessoBase.class, false);
        loadAcessos();

        add(grid);

        //TEMPORARIO
        Button deletarTudo = new Button("LIMPAR (BOTÃO TEMPORARIO)", event -> acessosService.deletarAcessos());
        add(deletarTudo);
    }

    private void loadAcessos() {
        List<AcessoBase> acessos = acessosService.findAll();
        GridListDataView<AcessoBase> dataView = grid.setItems(acessos);
        AcessosFilter acessosFilter = new AcessosFilter(dataView);

        Grid.Column<AcessoBase> idColum = grid.addColumn(AcessoBase::getId);

        Grid.Column<AcessoBase> dataHoraColum = grid.addColumn(new LocalDateTimeRenderer<>(
                AcessoBase::getCreateDate,
                () -> DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.SHORT,
                        FormatStyle.MEDIUM)));

        Grid.Column<AcessoBase> acaoColum = grid.addColumn(AcessoBase::getAcao);

        Grid.Column<AcessoBase> statusColum = grid.addComponentColumn(acessoBase ->{
            HorizontalLayout statusLayout = new HorizontalLayout();
            Span status = new Span(acessoBase.getStatus());
            status.getStyle().setPadding("4px");
            status.getStyle().setColor("white");

            statusLayout.add(status);
            statusLayout.setWidth("30%");
            statusLayout.getStyle().setBorderRadius("8px");
            statusLayout.setJustifyContentMode(JustifyContentMode.CENTER);


            switch (acessoBase.getStatus()) {
                case "CONCLUIDO":
                    statusLayout.getStyle().set("background-color", "#22C55E");
                    break;
                case "EDITADO":
                    statusLayout.getStyle().set("background-color", "#EFBF14");
                    break;
                case "EXCLUIDO":
                    statusLayout.getStyle().set("background-color", "#EB3B3B");
                    break;
                default:
                    statusLayout.getStyle().set("background-color", "black");
            }

            return statusLayout;
        });

        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(idColum).setComponent(
                createFilterHeader("Código Acesso", acessosFilter::setId));
        headerRow.getCell(dataHoraColum).setComponent(
                createFilterHeader("Data/Horário", acessosFilter::setDataHorario));
        headerRow.getCell(acaoColum).setComponent(
                createFilterHeader("Ação", acessosFilter::setAcao));
        headerRow.getCell(statusColum).setComponent(
                createFilterHeader("Status", acessosFilter::setStatus));
    }

    private static Component createFilterHeader(String labelText, Consumer<String> filterChangeConsumer) {
        NativeLabel label = new NativeLabel(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)").set("font-size", "var(--lumo-font-size-s)");

        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue()));

        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();

        return layout;
    }

    private static class AcessosFilter {
        private final GridListDataView<AcessoBase> dataView;

        private String id;
        private String dataHorario;
        private String acao;
        private String status;

        public AcessosFilter(GridListDataView<AcessoBase> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setId(String id) {
            this.id = id;
            this.dataView.refreshAll();
        }

        public void setDataHorario(String dataHorario) {
            this.dataHorario = dataHorario;
            this.dataView.refreshAll();
        }

        public void setAcao(String acao) {
            this.acao = acao;
            this.dataView.refreshAll();
        }

        public void setStatus(String status) {
            this.status = status;
            this.dataView.refreshAll();
        }

        public boolean test(AcessoBase acessoBase) {
            String idAcesso = acessoBase.getId().toString();
            String dataHoraAcesso = acessoBase.getCreateDate().toString();

            boolean matchesId = matches(idAcesso, id);
            boolean matchesDataHora = matches(dataHoraAcesso, dataHorario);
            boolean matchesAcao = matches(acessoBase.getAcao(), acao);
            boolean matchesStatus = matches(acessoBase.getStatus(), status);

            return matchesId && matchesDataHora && matchesAcao && matchesStatus;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }
}
