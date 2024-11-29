package appsuckhoe.nghiemtuc.controller;

import appsuckhoe.nghiemtuc.domain.KhachHang;
import appsuckhoe.nghiemtuc.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
@Autowired
private KhachHangRepository khachHangRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;




    @PostMapping(value = "/req/signup", consumes = "application/json")
    public KhachHang createKhachhang(@RequestBody KhachHang khachHang) {
        // Kiểm tra mật khẩu (matKhau) không null
        if (khachHang.getMatKhau() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(khachHang.getMatKhau());
        khachHang.setMatKhau(encodedPassword);

        // Lưu KhachHang
        return khachHangRepository.save(khachHang);
    }



}
