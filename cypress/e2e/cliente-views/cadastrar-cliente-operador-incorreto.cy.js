describe('Página cadastro cliente pelo operador',() => {

    beforeEach(() => {
        cy.visit('http://localhost:8080')
        //Navega para a página
        cy.get('vaadin-side-nav > :nth-child(5)').click()
        cy.get('[path="cadastro-cliente"] > vaadin-icon').click()
    })

    it('Não preenche os campos e exibe uma mensagem para o usuário', () => {
        cy.get('vaadin-button').click()

        cy.contains('Preencha todos os campos antes de continuar').should('be.visible')
    })

    it('Preenche o campo email em um formato incorreto e exibe mensagem para o usuário', () => {
        cy.formCadastoCliente("Teste Campo Email", "email@invalido", "99999-9999")

        cy.contains('O E-mail inserido é inválido').should('be.visible')
    })
})