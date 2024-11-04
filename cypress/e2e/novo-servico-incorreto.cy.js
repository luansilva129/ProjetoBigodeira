describe('Página de criação de serviço', {includeShadowDom:true}, () => {

  beforeEach(() => {
    cy.visit('http://localhost:8080')
    //Navega para a página
    cy.get('vaadin-side-nav > :nth-child(6)').click()
    cy.get('[path="novo-servico"] > vaadin-icon').click()
  })

  it('Não preenche os campos e tenta enviar os dados', () => {
    //Envia os dados
    cy.get('vaadin-button').click() // Botão Confirmar
    cy.contains('Preencha todos os campos antes de continuar').should('be.visible') //Verifica se a mensagem aparece para o usuário
  })

  it('Deixa somente o campo Preço sem estar preenchido e tenta enviar os dados', () => {
    //Deixa um campo em falta
    cy.formServico("Teste", "Serviços Teste", " ")
    //Envia os dados preenchidos
    cy.get('vaadin-button').click() // Botão Confirmar
    cy.contains('Preencha todos os campos antes de continuar').should('be.visible') //Verifica se a mensagem aparece para o usuário
  })

})