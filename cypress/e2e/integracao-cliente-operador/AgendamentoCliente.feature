Feature: Agendamento cliente

  Cenario: Cliente realiza um novo agendamento inserindo todas as informações necessárias
  Dado Acesso á página inicial do cliente
  E Clico em entrar para realizar o login
  E Acesso a página de novo agendamento
  E Preencho os campos todos os campos corretamente
  E Clico no botão confirmar agendamento
  Então Devo visualizar a mensagem "Agendamento salvo"

  Cenario: Cliente tenta realizar um novo agendamento sem ter feito login
  Dado Acesso á página inicial do cliente
  E Acesso a página de novo agendamento
  E Preencho os campos todos os campos corretamente
  E Clico no botão confirmar agendamento
  Então Devo visualizar a mensagem "Por favor, preencha todos os campos!"