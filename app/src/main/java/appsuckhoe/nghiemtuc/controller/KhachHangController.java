    package appsuckhoe.nghiemtuc.controller;

    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestParam;

    @Controller
    public class KhachHangController {
        @GetMapping("/req/login")
        public String login(){
            return "login";
        }
    @GetMapping("/req/signup")
        public String signup(){
            return "signup";
    }
    @GetMapping("/index")
        public String home(){
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
            // Tiến hành xác thực người dùng (sử dụng Spring Security hoặc phương pháp tùy chỉnh)
            // Nếu đăng nhập thành công, chuyển hướng đến trang chính
            return "redirect:/home";
        }

    }
