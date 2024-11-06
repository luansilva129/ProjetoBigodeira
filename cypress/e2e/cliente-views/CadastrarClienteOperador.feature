#noinspection CucumberUndefinedStep
Feature: Cadastro de clientes pelo operador

  Scenario: Cadastrar um novo cliente inserido todas as informações necessárias
    Given Acesso o sistema do operador
    And Clico na opção do menu cliente e cadastrar cliente
    When Acesso a página de cadastro cliente
    And Preencho todos os campos do formulário
    And Clico no botão cadastrar
    Then Devo visualizar a mensagem "Cliente cadastrado com sucesso!"

  Scenario: Não preenche os campos necessários e exibe uma mensagem de erro para o usuário
    Given Acesso o sistema do operador
    And Clico na opção do menu cliente e cadastrar cliente
    When Acesso a página de cadastro cliente
    And Clico no botão cadastrar
    Then Devo visualizar a mensagem "Preencha todos os campos antes de continuar"

  Scenario: Preencho todos os campos, mas o campo e-mail é preenchido em um formato incorreto
    Given Acesso o sistema do operador
    And Clico na opção do menu cliente e cadastrar cliente
    When Acesso a página de cadastro cliente
    And Preencho todos os campos, colocando o email "email@invalido" no campo email
    And Clico no botão cadastrar
    Then Devo visualizar a mensagem "O E-mail inserido é inválido"