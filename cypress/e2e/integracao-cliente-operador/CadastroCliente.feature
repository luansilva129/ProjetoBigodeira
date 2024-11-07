#noinspection CucumberUndefinedStep
Feature: Cadastro de cliente

  Cenario: Cadastrar um novo cliente inserindo todas as informações necessárias
    Dado Acesso á página inicial do cliente
    E Clico na opção do menu Cadastrar
    E Acesso a página de cadastro cliente
    E Preencho os campos todos os campos corretamente
    E Clico no botão cadastrar
    Então Devo visualizar a mensagem "Cliente cadastrado com sucesso!"

  Cenario: Cadastrar um novo cliente inserindo um e-mail já cadastrado
  Dado Acesso á página inicial do cliente
  E Clico na opção do menu Cadastrar
  E Acesso a página de cadastro cliente
  E Preencho os campos todos os campos com informações já cadastradas
  E Clico no botão cadastrar
  Então Devo visualizar a mensagem "Um cliente com este email já existe"