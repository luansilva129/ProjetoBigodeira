Cypress.Commands.add('formServico', (nome, descricao, preco) => {
    //Preenche todos os campos
    cy.get('#input-vaadin-text-field-13').type(nome) //Input Serviço
    cy.get('#input-vaadin-text-field-14').type(descricao) //Input Descrição
    cy.get('#input-vaadin-number-field-16').type(preco) //Input Preço
    cy.get('vaadin-button').click() // Botão Confirmar
})

Cypress.Commands.add('formCadastoCliente', (nome, email, telefone) => {
    cy.get('#input-vaadin-text-field-13').type(nome)
    cy.get('#input-vaadin-email-field-14').type(email)
    cy.get('#input-vaadin-text-field-17').type(telefone)
    cy.get('vaadin-button').click()
})

Cypress.Commands.add('formCadastoColaborador', (nome, cpf, selectEspecialidade, horaInicio, horaFim, selectDias) => {
    cy.get('#input-vaadin-text-field-19').type(nome)
    cy.get('#input-vaadin-text-field-20').type(cpf)
    if (selectEspecialidade === true) {
        cy.get('#input-vaadin-multi-select-combo-box-22').click()
        cy.contains('Corte').click()
        cy.contains('Platinar').click()
        cy.get('html').click() //Clica na tela para tirar a seleção do combo box
    }
    cy.get('#input-vaadin-time-picker-24').type(horaInicio)
    cy.get('html').click({force:true})
    cy.get('#input-vaadin-time-picker-26').type(horaFim)
    cy.get('html').click()
    if (selectDias === true) {
        cy.get('#input-vaadin-multi-select-combo-box-28').click()
        cy.contains('Segunda-feira').click()
        cy.contains('Terça-feira').click()
        cy.get('html').click()
    }
    cy.get('vaadin-button').click()
})