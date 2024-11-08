Feature: Criação de serviços

  Cenario: Criar novo serviço inserindo todas as informações necessárias
    Dado Acesso o sistema do operador
    E Clico na opção do menu serviços e novo serviço
    E Acesso a página de novo serviço
    E Preencho todos os campos do formulário
    E Clico no botão confirmar
    Então Devo visualizar a mensagem "Serviço criado com sucesso!"

  Cenario: Não preenche os campos necessários e exibe uma mensagem de erro para o usuário
    Dado Acesso o sistema do operador
    E Clico na opção do menu serviços e novo serviço
    E Acesso a página de novo serviço
    E Clico no botão confirmar
    Então Devo visualizar a mensagem "Preencha todos os campos antes de continuar"

  Cenario: Preenche todos os campos, menos o de preço e exibe uma mensagem de erro para o usuário
    Dado Acesso o sistema do operador
    E Clico na opção do menu serviços e novo serviço
    E Acesso a página de novo serviço
    E Preencho somente os campos serviço e descrição
    E Clico no botão confirmar
    Então Devo visualizar a mensagem "Preencha todos os campos antes de continuar"