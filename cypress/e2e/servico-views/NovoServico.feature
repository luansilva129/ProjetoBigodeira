#noinspection CucumberUndefinedStep
Feature: Criação de serviços

  Scenario: Criar novo serviço inserindo todas as informações necessárias
    Given Acesso o sistema do operador
    And Clico na opção do menu serviços e novo serviço
    When Acesso a página de novo serviço
    And Preencho todos os campos do formulário
    And Clico no botão confirmar
    Then Devo visualizar a mensagem "Serviço criado com sucesso!"

  Scenario: Não preenche os campos necessários e exibe uma mensagem de erro para o usuário
    Given Acesso o sistema do operador
    And Clico na opção do menu serviços e novo serviço
    When Acesso a página de novo serviço
    And Clico no botão confirmar
    Then Devo visualizar a mensagem "Preencha todos os campos antes de continuar"

  Scenario: Preenche todos os campos, menos o de preço e exibe uma mensagem de erro para o usuário
    Given Acesso o sistema do operador
    And Clico na opção do menu serviços e novo serviço
    When Acesso a página de novo serviço
    And Preencho os campos serviço e descrição
    And Clico no botão confirmar
    Then Devo visualizar a mensagem "Preencha todos os campos antes de continuar"