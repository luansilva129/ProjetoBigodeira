package br.com.atlas.bigodeira.backend.service;


import br.com.atlas.bigodeira.backend.domainBase.domain.Operador;
import br.com.atlas.bigodeira.backend.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperadorService {

    @Autowired
    OperadorRepository operadorRepository;

    public boolean autenticarOperador(String email, String senha) {
        Operador operador = operadorRepository.findByEmail(email);

        if (operador != null && operador.getSenha().equals(senha)) {
            return true;
        }

        return false;
    }

}
