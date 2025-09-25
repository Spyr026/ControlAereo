describe('Gestión de Aeropuertos', () => {
  beforeEach(() => {
    cy.visit('/aeropuertos');
  });

  it('muestra la lista de aeropuertos', () => {
    cy.contains('Listado de Aeropuertos').should('be.visible');
    cy.get('table').should('exist');
  });

  it('permite abrir el modal para añadir aeropuerto', () => {
    cy.contains('Añadir Aeropuerto').click();
    cy.get('#modalAeropuerto').should('be.visible');
    cy.contains('Añadir Aeropuerto').should('be.visible');
  });

  it('permite crear un aeropuerto', () => {
    cy.contains('Añadir Aeropuerto').click();
    cy.get('#modalAeropuerto').within(() => {
      cy.get('input[name="nombre"]').type('Aeropuerto Cypress');
      cy.get('input[name="ciudad"]').type('Ciudad Cypress');
      cy.get('input[name="isIataEditable"]').check();
      cy.get('input[name="iata"]').clear().type('CYP');
      cy.get('input[name="capacidad"]').type('10');
      cy.get('button[type="submit"]').click();
    });
    cy.contains('Aeropuerto Cypress').should('exist');
});

  it('permite ver detalles de un aeropuerto', () => {
    cy.get('table tbody tr').first().find('a[title="Ver detalles"]').click();
    cy.contains('Código IATA:').should('be.visible');
    cy.contains('Ciudad:').should('be.visible');
  });

  it('permite editar un aeropuerto', () => {
  cy.get('table tbody tr').first().find('a[title="Editar"]').click();
  cy.url().should('include', '/aeropuertos');
  cy.get('input[name="nombre"]', { timeout: 5000 }).should('be.visible').clear().type('Aeropuerto Editado');
  cy.contains('Editar').click();
  cy.contains('Aeropuerto Editado').should('exist');
  });

  it('permite eliminar un aeropuerto', () => {
    cy.get('table tbody tr').first().find('button[title="Eliminar"]').click();
    cy.get('#modalConfirmarEliminar').should('be.visible');
    cy.get('#modalConfirmarEliminar').within(() => {
      cy.contains('Eliminar').click();
    });
    cy.wait(500); // Espera breve para cierre de modal
    cy.get('#modalConfirmarEliminar').should('not.exist');
  });
});
