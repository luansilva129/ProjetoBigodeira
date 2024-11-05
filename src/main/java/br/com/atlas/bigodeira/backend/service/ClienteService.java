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

    @Autowired
    AgendamentoService agendamentoService;

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


    public boolean emailExiste(String email) {
        return clienteRepository.findByEmail(email).isPresent();
    }


    public void delete(Cliente cliente) {
        agendamentoService.deleteByClienteId(cliente.getId());
        clienteRepository.delete(cliente);
    }


    public Cliente findByEmail(String email) {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);
        return clienteOptional.orElse(null);
    }


    public Optional<Cliente> findByEmail2(String email) {
        return clienteRepository.findByEmail(email);
    }


    public Optional<Cliente> findById(Long clienteId) {
        return clienteRepository.findById(clienteId);
    }


    public String formatarTelefone(String value) {
        String numbers = value.replaceAll("[^0-9]", "");

        if (numbers.length() <= 2) {
            return "(" + numbers;
        } else if (numbers.length() <= 7) {
            return "(" + numbers.substring(0, 2) + ")" + numbers.substring(2);
        } else {
            return "(" + numbers.substring(0, 2) + ")" + numbers.substring(2, 7) + "-" + numbers.substring(7);
        }
    }


}
