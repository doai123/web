    package appsuckhoe.nghiemtuc.controller;

    import appsuckhoe.nghiemtuc.service.KhachHangService;
    import appsuckhoe.nghiemtuc.service.loginServices;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestParam;

    @Controller
    public class KhachHangController {
        @Autowired
        private loginServices loginServices;

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
            boolean login = loginServices.login(username, password);
            if (login) {
                return "redirect:/";
            } else {
                model.addAttribute("error", "Invalid username or password");  // Thông báo lỗi nếu đăng nhập thất bại
                return "login";
            }

        }
    }
