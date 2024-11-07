#noinspection CucumberUndefinedStep
Feature: Cadastro de clientes pelo operador

  Cenario: Cadastrar um novo cliente inserindo todas as informações necessárias
    Dado Acesso o sistema do operador
    E Clico na opção do menu cliente e cadastrar cliente
    E Acesso a página de cadastro cliente
    E Preencho todos os campos do formulário
    E Clico no botão cadastrar
    Então Devo visualizar a mensagem "Cliente cadastrado com sucesso!"

  Cenario: Não preenche os campos necessários e exibe uma mensagem de erro para o usuário
    Dado Acesso o sistema do operador
    E Clico na opção do menu cliente e cadastrar cliente
    E Acesso a página de cadastro cliente
    E Clico no botão cadastrar
    Então Devo visualizar a mensagem "Preencha todos os campos antes de continuar"

  Cenario: Preencho todos os campos, mas o campo e-mail é preenchido em um formato incorreto
    Dado Acesso o sistema do operador
    E Clico na opção do menu cliente e cadastrar cliente
    E Acesso a página de cadastro cliente
    E Preencho todos os campos, colocando o email "email@invalido" no campo email
    E Clico no botão cadastrar
    Então Devo visualizar a mensagem "O E-mail inserido é inválido"