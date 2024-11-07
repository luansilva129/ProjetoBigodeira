describe('Página agendamento cliente', {includeShadowDom:true},() => {

    it('Preenche os campos corretamente e cadastra um novo cliente', () => {
        cy.loginCliente("emailcadastrado@email.com", "12345")

        cy.caminhoAgendamentoCliente()

        cy.formAgendamentoCliente(true, true, true, true)

        cy.contains('Agendamento salvo').should('be.visible')
    })
})