describe('Homepage', () => {
    it('should load the homepage correctly', () => {
        // Truy cập vào trang chủ
        cy.visit('/');

        // Kiểm tra tiêu đề trang
        cy.title().should('include', 'DOUBLE SHOP');

        // Kiểm tra sự tồn tại của một phần tử trên trang (ví dụ: tiêu đề)
        cy.contains('- DO THE RIGHT-DO THE BEST -').should('be.visible');
    });
});
