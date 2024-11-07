describe('Página cadastro cliente pelo sistema do operador',() => {

    beforeEach(() => {
        cy.loginOperador()
        //Navega para a página
        cy.get('vaadin-side-nav > :nth-child(5)').click()
        cy.get('[path="cadastro-cliente"] > vaadin-icon').click()
    })

    it('Preenche os campos corretamente e cadastrar um novo usuário', () => {
        cy.formCadastoClienteOperador("Wanfranklin Alves", "email@teste.com.br", "98765-4321")

        cy.contains('Cliente cadastrado com sucesso!').should('be.visible')
        cy.wait(5000)
    })

    it('Não preenche os campos e exibe uma mensagem para o usuário', () => {
        cy.get('vaadin-button').click()

        cy.contains('Preencha todos os campos antes de continuar').should('be.visible')
        cy.wait(5000)
    })

    it('Preenche o campo email em um formato incorreto e exibe mensagem para o usuário', () => {
        cy.formCadastoClienteOperador("Teste Campo Email", "email@invalido", "99999-9999")

        cy.contains('O E-mail inserido é inválido').should('be.visible')
    })
})