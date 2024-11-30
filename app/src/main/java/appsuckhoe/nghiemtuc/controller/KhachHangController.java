    package appsuckhoe.nghiemtuc.controller;

    import appsuckhoe.nghiemtuc.domain.KhachHang;
    import appsuckhoe.nghiemtuc.repository.KhachHangRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestParam;
    import appsuckhoe.nghiemtuc.service.AuthenticationServices;

    @Controller
    public class KhachHangController {
        @Autowired
        private AuthenticationServices authenticationServices;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private KhachHangRepository khachHangRepository;
        @GetMapping("/req/login")
        public String login() {
            return "login";
        }

        @GetMapping("/req/signup")
        public String signup() {
            return "signup";
        }

        @GetMapping("/index")
        public String home() {
            return "index";
        }

        @PostMapping("/req/login")
        public String login(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
            if (username.isEmpty() || password.isEmpty()) {
                model.addAttribute("error", "Username and password are required");
                return "login";  // Trả lại trang login nếu thông tin không hợp lệ
            }
            boolean login = authenticationServices.login(username, password);
            if (login) {
                return "redirect:/";
            } else {
                model.addAttribute("error", "Invalid username or password");  // Thông báo lỗi nếu đăng nhập thất bại
                return "login";
            }

        }
        @PostMapping(value = "/req/signup", consumes = "application/json")
        public String createKhachhang(@RequestBody KhachHang khachHang) {
            if (khachHang.getMatKhau() == null) {
                throw new IllegalArgumentException("Password cannot be null");
            }
            boolean check = authenticationServices.signup(khachHang.getTen());
                if (check) {
                    // Trả về thông báo lỗi nếu tên người dùng đã tồn tại
                    return "Failed.";
                }else {

                    String encodedPassword = passwordEncoder.encode(khachHang.getMatKhau());
                    khachHang.setMatKhau(encodedPassword);

                    // Lưu KhachHang vào cơ sở dữ liệu
                    khachHangRepository.save(khachHang);

                    // Trả về thông báo đăng ký thành công
                    return "/endpoints/req/login";
                }
        }
    }
