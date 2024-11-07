#noinspection CucumberUndefinedStep
Feature: Cadastro de colaborador

  Cenario: Cadastrar um novo colaborador inserindo todas as informações necessárias
    Dado Acesso o sistema do operador
    E Clico na opção do menu colaborador e cadastrar colaborador
    E Acesso a página de cadastro colaborador
    E Preencho os campos e seleciono as especialidades, hora de inicio e fim do expediente e dias da semana
    E Clico no botão cadastrar
    Então Devo visualizar a mensagem "Colaborador cadastrado com sucesso!"

  Cenario: Não preenche os campos necessários e exibe uma mensagem de erro para o usuário
    Dado Acesso o sistema do operador
    E Clico na opção do menu colaborador e cadastrar colaborador
    E Acesso a página de cadastro colaborador
    E Clico no botão cadastrar
    Então Devo visualizar a mensagem "Preencha todos os campos antes de continuar"