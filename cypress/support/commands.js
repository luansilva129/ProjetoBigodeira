Cypress.Commands.add('loginOperador' , () => {
    cy.visit('/')

    cy.get('#input-vaadin-text-field-6').type('email@exemplo.com')
    cy.get('#input-vaadin-password-field-7').type('senhaSegura')
    cy.wait(1000)

    cy.get('vaadin-button').click()
    cy.url().should('include', '/dashboard')
    cy.wait(1000)
})

Cypress.Commands.add('loginCliente', (email,senha) => {
    cy.visit('/cliente/home')
    cy.wait(2000)

    cy.get('vaadin-button.text-secondary').click()
    cy.get('#overlay').should('be.visible')
    cy.wait(1000)

    cy.get('#overlay > vaadin-vertical-layout > :nth-child(1)').type(email)
    cy.get('#overlay > vaadin-vertical-layout > :nth-child(2)').type(senha)
    cy.wait(1000)

    cy.get('#overlay > vaadin-vertical-layout > vaadin-horizontal-layout > :nth-child(1)').click()
    cy.contains('Sair').should('be.visible')
    cy.wait(1000)
})

Cypress.Commands.add('caminhoCadastroCliente', () => {
    cy.visit('/cliente/home')
    cy.wait(2000)

    cy.get('[href="cliente/cadastroView"]').click()
    cy.url().should('include', '/cadastroView')
    cy.wait(1000)
})

Cypress.Commands.add('caminhoAgendamentoCliente', () => {
    cy.get('[href="cliente/novo_agendamento"]').click()
    cy.url().should('include', '/novo_agendamento')
    cy.wait(1000)
})

Cypress.Commands.add('formAgendamentoCliente', (selectServico, selectColaborador, selectData, selectHora) => {
    if (selectServico === true) {
        cy.get('vaadin-form-layout > :nth-child(2)').click()
        cy.wait(1000)
        cy.contains('Corte').click()
    }
    if (selectColaborador === true) {
        cy.get('vaadin-form-layout > :nth-child(3)').click()
        cy.wait(1000)
        cy.contains('Giovani').click()
    }
    if (selectData === true) {
        cy.get('vaadin-date-picker').click()
        cy.wait(1000)
        cy.contains('Today').click()
    }
    if (selectHora === true) {
        cy.get('[style="width: calc(49.95% - 0.75rem); margin-right: 0px;"]').click()
        cy.wait(1000)
        cy.contains('09:00').click()
    }
    cy.wait(1000)

    cy.get('vaadin-vertical-layout > vaadin-button').click()
})

Cypress.Commands.add('formCadastroCliente', (nome, email, senha, telefone) => {
    cy.get('vaadin-form-layout > :nth-child(1)').type(nome)
    cy.get('vaadin-form-layout > :nth-child(2)').type(email)
    cy.get('vaadin-password-field').type(senha)
    cy.get('[colspan="2"][placeholder="(xx)xxxxx-xxxx"]').type(telefone)
    cy.wait(3000)

    cy.get('vaadin-vertical-layout > vaadin-button').click()
})

Cypress.Commands.add('formServico', (nome, descricao, preco) => {
    //Preenche todos os campos
    cy.get('#input-vaadin-text-field-13').type(nome) //Input Serviço
    cy.get('#input-vaadin-text-field-14').type(descricao) //Input Descrição
    cy.get('#input-vaadin-number-field-16').type(preco) //Input Preço

    cy.get('vaadin-button').click() // Botão Confirmar
})

Cypress.Commands.add('formCadastoClienteOperador', (nome, email, telefone) => {
    cy.get('vaadin-vertical-layout > :nth-child(2)').type(nome)
    cy.get('vaadin-email-field').type(email)
    cy.get('vaadin-horizontal-layout > vaadin-text-field').type(telefone)
    cy.wait(3000)

    cy.get('vaadin-button').click()
})

Cypress.Commands.add('formCadastoColaborador', (nome, cpf, selectEspecialidade, horaInicio, horaFim, selectDias) => {
    cy.get('vaadin-vertical-layout > :nth-child(2)').type(nome)
    cy.get('vaadin-vertical-layout > :nth-child(3)').type(cpf)
    if (selectEspecialidade === true) {
        cy.get('vaadin-vertical-layout > :nth-child(4)').click()
        cy.contains('Corte').click()
        cy.contains('Platinar').click()
        cy.get('html').click() //Clica na tela para tirar a seleção do combo box
    }
    cy.get('vaadin-horizontal-layout > :nth-child(1)').type(horaInicio)
    cy.get('html').click({force:true})
    cy.get('vaadin-horizontal-layout > :nth-child(2)').type(horaFim)
    cy.get('html').click()
    if (selectDias === true) {
        cy.get('vaadin-horizontal-layout > :nth-child(3)').click()
        cy.contains('Segunda-feira').click()
        cy.contains('Terça-feira').click()
        cy.get('html').click()
    }
    cy.wait(3000)

    cy.get('vaadin-button').click()
})