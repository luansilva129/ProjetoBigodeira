describe('Página de criação de serviço', {includeShadowDom:true}, () => {

  beforeEach(() => {
    cy.loginOperador()
    //Navega para a página
    cy.get('vaadin-side-nav > :nth-child(6)').click()
    cy.get('[path="novo-servico"] > vaadin-icon').click()
  })

  it('Dever preencher os campos corretamente e criar um novo serviço', () => {
    cy.formServico("Teste", "Serviço Teste", "100")

    cy.contains('Serviço criado com sucesso!').should('be.visible')
    cy.wait(3000)
  })

  it('Não preenche os campos e tenta enviar os dados', () => {
    cy.wait(3000)
    cy.get('vaadin-button').click() // Botão Confirmar

    cy.contains('Preencha todos os campos antes de continuar').should('be.visible')
    cy.wait(3000)
  })

  it('Deixa somente o campo Preço sem estar preenchido e tenta enviar os dados', () => {
    //Deixa um campo em falta
    cy.formServico("Teste", "Serviços Teste", " ")

    cy.contains('Preencha todos os campos antes de continuar').should('be.visible')
  })

})