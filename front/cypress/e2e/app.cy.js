describe('App principal', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('debe cargar la página principal y mostrar el navbar', () => {
    cy.get('nav.navbar').should('exist');
  });

  it('debe mostrar los enlaces principales en el navbar', () => {
    cy.get('nav.navbar').within(() => {
      cy.contains('a.nav-link.dropdown-toggle', 'Aeropuertos').should('exist');
      cy.contains('a.nav-link.dropdown-toggle', 'Aviones').should('exist');
      cy.contains('a.nav-link.dropdown-toggle', 'Vuelos').should('exist');
    });
  });

  it('debe mostrar las opciones del dropdown Aeropuertos', () => {
    cy.contains('a.nav-link.dropdown-toggle', 'Aeropuertos').click();
    cy.contains('a.dropdown-item', 'Ver Aeropuerto').should('exist');
    cy.contains('a.dropdown-item', 'Editar Aeropuerto').should('exist');
  });

  it('debe mostrar las opciones del dropdown Aviones', () => {
    cy.contains('a.nav-link.dropdown-toggle', 'Aviones').click();
    cy.contains('a.dropdown-item', 'Ver Avión').should('exist');
    cy.contains('a.dropdown-item', 'Editar Avión').should('exist');
  });

  it('debe mostrar las opciones del dropdown Vuelos', () => {
    cy.contains('a.nav-link.dropdown-toggle', 'Vuelos').click();
    cy.contains('a.dropdown-item', 'Ver Vuelo').should('exist');
    cy.contains('a.dropdown-item', 'Editar Vuelo').should('exist');
  });
});