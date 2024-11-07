describe('Página cadastro colaborador', {includeShadowDom:true},() => {

    beforeEach(() => {
        cy.loginOperador()
        //Navega para a página
        cy.get(':nth-child(4) > [icon="vaadin:user"]').click()
        cy.get('[path="cadastro-colaborador"] > vaadin-icon').click()
    })

    it('Preenche os campos corretamente e cadastrar um novo colaborador', () => {
        cy.formCadastoColaborador("Colaborador Teste", "12345678910", true,
            "10:00", "18", true)

        cy.contains('Colaborador cadastrado com sucesso!').should('be.visible')
        cy.wait(3000)
    })

    it('Não preenche os campos e exibe uma mensagem para o usuário', () => {
        cy.wait(3000)
        cy.get('vaadin-button').click()

        cy.contains('Preencha todos os campos antes de continuar').should('be.visible')
    })
})