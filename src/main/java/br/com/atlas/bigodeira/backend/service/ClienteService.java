package br.com.atlas.bigodeira.backend.service;


import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }



    public void save(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public boolean autenticar(String email, String senha) {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);

        return clienteOptional.isPresent() && clienteOptional.get().getSenha().equals(senha);
    }




}
