describe('Login Form', () => {
    it('should log in with valid credentials', () => {
        // Truy cập trang đăng nhập
        cy.visit('/endpoints/req/login');

        // Nhập thông tin vào các trường form
        cy.get('input[name="username"]').type('user@example.com');
        cy.get('input[name="password"]').type('password123');

        // Click vào nút đăng nhập
        cy.get('button[type="submit"]').click();

        // Kiểm tra xem sau khi đăng nhập có chuyển đến trang dashboard không
        cy.url().should('include', '/dashboard');
        cy.contains('Welcome to your dashboard').should('be.visible');
    });
});
