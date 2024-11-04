describe('Página cadastro cliente pelo operador',() => {

    it('Preenche os campos corretamente e cadastrar um novo usuário', () => {
        cy.visit('http://localhost:8080')
        //Navega para a página
        cy.get('vaadin-side-nav > :nth-child(5)').click()
        cy.get('[path="cadastro-cliente"] > vaadin-icon').click()

        cy.formCadastoCliente("Wanfranklin Alves", "email@teste.com.br", "98765-4321")

        cy.contains('Cliente cadastrado com sucesso!').should('be.visible')
    })
})