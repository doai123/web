describe('Homepage', () => {
    it('should load the homepage correctly', () => {
        // Truy cập vào trang chủ
        cy.visit('/');

        // Kiểm tra tiêu đề trang
        cy.title().should('include', 'Home Page');

        // Kiểm tra sự tồn tại của một phần tử trên trang (ví dụ: tiêu đề)
        cy.contains('Welcome to my app').should('be.visible');
    });
});
