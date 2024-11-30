package appsuckhoe.nghiemtuc.service;

import appsuckhoe.nghiemtuc.domain.KhachHang;
import appsuckhoe.nghiemtuc.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class loginServices {
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean login(String username,String password) throws UsernameNotFoundException {
        Optional<KhachHang> user = khachHangRepository.findByTen(username);
        if(user.isPresent()) {
            boolean isPasswordMatch = passwordEncoder.matches(password, user.get().getMatKhau());
            if(isPasswordMatch){
                return true;
            }else {
                return false;
            }
        }else {
            return false;

        }
    }

}
