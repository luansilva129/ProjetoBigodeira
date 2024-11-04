describe('Página de criação de serviço', {includeShadowDom:true}, () => {

  it('Dever preencher os campos corretamente e criar um novo serviço', () => {
    cy.visit('http://localhost:8080')
    //Navega para a página
    cy.get('vaadin-side-nav > :nth-child(6)').click()
    cy.get('[path="novo-servico"] > vaadin-icon').click()

    cy.formServico("Teste", "Serviço Teste", "100")

    cy.contains('Serviço criado com sucesso!').should('be.visible') //Verifica se a mensagem aparece para o usuário
  })

})