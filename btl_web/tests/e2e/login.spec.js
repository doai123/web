describe('Login Form', () => {
    it('should log in with valid credentials', () => {
        // Truy cập trang đăng nhập
        cy.visit('/endpoints/req/signup');
        cy.get('input[name="username"]').type('admin');
        cy.get('input[name="email"]').type('admin@example.com');
        cy.get('input[name="password"]').type('doai123');
        cy.get('input[name="passwordcon"]').type('doai123');
        cy.get('button[type="submit"]').click();
        // cy.visit('/endpoints/req/login');

        // // Nhập thông tin vào các trường form
        cy.get('input[id="ten"]').type('admin');
        cy.get('input[name="password"]').type('doai123');
        //
        // // Click vào nút đăng nhập
        cy.get('button[type="submit"]').click();
        //
        // // Kiểm tra xem sau khi đăng nhập có chuyển đến trang dashboard không
        // cy.url().should('include', '/');
        cy.contains('Trang Chủ').should('be.visible');
    });
});
