package backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@Table(name = "khach_hang")
public class KhachHang  {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long maKhachHang;
    private String roles = "ROLE_ADMIN";
    private String ten;
    private String email;
    private String matKhau;
    private String soDienThoai;
    private String diaChiGiaoHang;

}
