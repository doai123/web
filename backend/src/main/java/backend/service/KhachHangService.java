package backend.service;

import backend.domain.KhachHang;
import backend.repository.KhachHangRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
@AllArgsConstructor
public class KhachHangService implements UserDetailsService {
    @Autowired
   private KhachHangRepository khachHangRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<KhachHang> user = khachHangRepository.findByTen(username);
        System.out.println(username);
        if(user.isPresent()) {
            System.out.println("user tồn tại");
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getTen())
                    .password(userObj.getMatKhau())
                    .authorities(new SimpleGrantedAuthority("ROLE_" + userObj.getRoles()))
                    .build();
        }else {
            throw new UsernameNotFoundException(username);

        }
    }



}
