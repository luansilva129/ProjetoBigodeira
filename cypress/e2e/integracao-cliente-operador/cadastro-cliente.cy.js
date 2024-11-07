describe('Página cadastro cliente', {includeShadowDom:true},() => {

    beforeEach(() => {
        cy.caminhoCadastroCliente()
    })

    it('Preenche os campos corretamente e cadastra um novo cliente', () => {
        cy.formCadastroCliente("Cliente Teste", "cliente@email.com", "senha123", "84987654321")

        cy.contains('Cliente cadastrado com sucesso!').should('be.visible')
        cy.wait(3000)
    })

    it('Preenche os campos com os dados de um cliente já cadastrado e exibe mensagem para o usuário', () => {
        cy.formCadastroCliente("Cliente Teste", "cliente@email.com", "senha123", "84987654321")

        cy.contains('Um cliente com este email já existe').should('be.visible')
    })
})