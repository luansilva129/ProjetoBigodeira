describe('Página cadastro colaborador', {includeShadowDom:true},() => {

    it('Preenche os campos corretamente e cadastrar um novo colaborador', () => {
        cy.visit('http://localhost:8080')
        //Navega para a página
        cy.get(':nth-child(4) > [icon="vaadin:user"]').click()
        cy.get('[path="cadastro-colaborador"] > vaadin-icon').click()

        cy.formCadastoColaborador("Colaborador Teste", "12345678910", true,
            "10:00", "18", true)

        cy.contains('Colaborador cadastrado com sucesso!').should('be.visible')
    })
})