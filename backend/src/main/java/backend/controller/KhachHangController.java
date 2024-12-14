    package backend.controller;

    import backend.domain.KhachHang;
    import backend.repository.KhachHangRepository;
    import backend.service.AuthenticationServices;
    import backend.util.Jwt;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Optional;

    @Controller
    public class KhachHangController {
        @Autowired
        private Jwt jwt;
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
        @DeleteMapping("delete/delete-all")
        public ResponseEntity<Map<String, String>> deleteAll() {
            Map<String, String> response = new HashMap<>();
            try {
                khachHangRepository.deleteAll();  // Xóa tất cả khách hàng
                response.put("message", "All customers have been deleted successfully.");
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                response.put("error", "Failed to delete all customers.");
                return ResponseEntity.status(500).body(response);
            }
        }


        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Map<String, String>> deleteById(@PathVariable("id") Long id) {
            Map<String, String> response = new HashMap<>();
            try {
                if (khachHangRepository.existsById(id)) {
                    khachHangRepository.deleteById(id);  // Xóa khách hàng theo ID
                    response.put("message", "Customer with ID " + id + " has been deleted successfully.");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("error", "Customer with ID " + id + " not found.");
                    return ResponseEntity.status(404).body(response);  // Trả về lỗi nếu không tìm thấy ID
                }
            } catch (Exception e) {
                response.put("error", "Failed to delete customer.");
                return ResponseEntity.status(500).body(response);
            }
        }

        @PostMapping(value = "/req/signup", consumes = "application/json")
        public ResponseEntity<Map<String, String>> createKhachhang(@RequestBody KhachHang khachHang) {
            Map<String, String> response = new HashMap<>();

            if (khachHang.getMatKhau() == null) {
                response.put("error", "Password cannot be null");
                return ResponseEntity.badRequest().body(response);
            }

            boolean check = authenticationServices.signup(khachHang.getTen());
            if (check) {
                response.put("error", "Ten already exited.");
                return ResponseEntity.badRequest().body(response);
            } else {
                String encodedPassword = passwordEncoder.encode(khachHang.getMatKhau());
                khachHang.setMatKhau(encodedPassword);

                khachHangRepository.save(khachHang);
                try {
                    String token = jwt.generateToken(khachHang.getTen(), khachHang.getRoles());
                    response.put("message", token);
                } catch (Exception e) {
                    response.put("error", "Error generating JWT token: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }

                return ResponseEntity.ok(response);
            }
        }
        @PostMapping("/admin-login")
        public ResponseEntity<?> generateAdminToken(@RequestParam String username) {
            try {
                // Kiểm tra người dùng trong cơ sở dữ liệu
                Optional<KhachHang> khachHang = khachHangRepository.findByTen(username);

                if (khachHang.isPresent()) {
                    String role = khachHang.get().getRoles();

                    // Kiểm tra nếu người dùng là admin
                    if ("ROLE_ADMIN".equals(role)) {
                        String token = jwt.generateToken(username, role); // Tạo token
                        return ResponseEntity.ok(token); // Trả về token nếu thành công
                    } else {
                        throw new RuntimeException("User is not an admin");
                    }
                } else {
                    throw new RuntimeException("User not found");
                }
            } catch (RuntimeException e) {
                // Bắt lỗi và trả về thông báo rõ ràng
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } catch (Exception e) {
                // Bắt các lỗi không mong muốn
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An unexpected error occurred: " + e.getMessage());
            }
        }

        @GetMapping("/get-all")
        public ResponseEntity<List<KhachHang>> getAll() {
            List<KhachHang> customers = khachHangRepository.findAll();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(customers);
        }

        @GetMapping("/get/{id}")
        public ResponseEntity<KhachHang> getById(@PathVariable("id") Long id) {
            return khachHangRepository.findById(id)
                    .map(customer -> ResponseEntity.ok(customer))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
    }
