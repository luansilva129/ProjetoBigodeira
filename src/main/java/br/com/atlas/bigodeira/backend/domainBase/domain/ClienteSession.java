package br.com.atlas.bigodeira.backend.domainBase.domain;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import com.vaadin.flow.server.VaadinServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClienteSession {

    private static final String CLIENTE_LOGADO_ID = "clienteLogadoId";
    private static final String CLIENTE_LOGADO = "clienteLogado";
    private static final String AGENDAMENTOS = "agendamentos";

    private static final String CLIENTE = "cliente";

    private static List<AgendamentoBase> agendamentos = new ArrayList<>();

    private static HttpSession getSession() {
        return VaadinServletRequest.getCurrent().getSession();
    }


    public static void setClienteLogadoId(Long id) {
        getSession().setAttribute(CLIENTE_LOGADO_ID, id);
    }

    public static Long getClienteLogadoId() {
        return (Long) getSession().getAttribute(CLIENTE_LOGADO_ID);
    }

    public static void setClienteLogado(boolean logado) {
        getSession().setAttribute(CLIENTE_LOGADO, logado);
    }

    public static boolean isClienteLogado() {
        Boolean logado = (Boolean) getSession().getAttribute(CLIENTE_LOGADO);
        return logado != null && logado;
    }

    public static void setAgendamentos(List<AgendamentoBase> agendamentos) {
        getSession().setAttribute(AGENDAMENTOS, agendamentos);
    }

    public static List<AgendamentoBase> getAgendamentos() {
        List<AgendamentoBase> agendamentos =
                (List<AgendamentoBase>) getSession().getAttribute(AGENDAMENTOS);
        return agendamentos != null ? agendamentos : new ArrayList<>();
    }

    public static void logout() {
        getSession().invalidate();
    }

    public static ClienteSession getInstance() {
        if (instance == null) {
            instance = new ClienteSession();
        }
        return instance;
    }

    private static ClienteSession instance;


}

