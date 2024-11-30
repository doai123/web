package appsuckhoe.nghiemtuc.controller;

import appsuckhoe.nghiemtuc.domain.KhachHang;
import appsuckhoe.nghiemtuc.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class RegistrationController {
@Autowired
private KhachHangRepository khachHangRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;




    @PostMapping(value = "/req/signup", consumes = "application/json")
    public String createKhachhang(@RequestBody KhachHang khachHang, RedirectAttributes redirectAttributes) {
        // Kiểm tra mật khẩu (matKhau) không null
        if (khachHang.getMatKhau() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(khachHang.getMatKhau());
        khachHang.setMatKhau(encodedPassword);

        // Lưu KhachHang vào cơ sở dữ liệu
        khachHangRepository.save(khachHang);

        // Thêm thông báo cho redirect
        redirectAttributes.addFlashAttribute("message", "Registration successful. Please log in.");

        return "redirect:/endpoints/req/login";
    }



}
