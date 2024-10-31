package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.domain.ClienteSession;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class AuthService {

    private static boolean loggedIn = false;

    public static boolean isLoggedIn() {
        return true;
    }

    public boolean isClienteLogado() {
        return ClienteSession.isClienteLogado();
    }

    public static void login() {
        loggedIn = true;
    }

    public static void logout() {
        loggedIn = false;

    }
}