describe('Página agendamento cliente', {includeShadowDom:true},() => {

    it('Preenche os campos corretamente e cadastra um novo cliente', () => {
        cy.loginCliente("emailcadastrado@email.com", "12345")

        cy.caminhoAgendamentoCliente()

        cy.formAgendamentoCliente(true, true, true, true)

        cy.contains('Agendamento salvo').should('be.visible')
        cy.wait(5000)
    })

    it('Acessa a página de novo agendamento sem realizar login e tenta agendar', () => {
        cy.visit('/cliente/home')
        cy.wait(1000)

        cy.caminhoAgendamentoCliente()

        cy.contains('Nenhum cliente logado. Por favor, faça login.').should('be.visible')
        cy.wait(3000)

        cy.formAgendamentoCliente(true, true, true, true)

        cy.contains('Por favor, preencha todos os campos!').should('be.visible')
    })
})