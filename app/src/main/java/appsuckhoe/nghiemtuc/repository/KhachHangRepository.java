package appsuckhoe.nghiemtuc.repository;

import appsuckhoe.nghiemtuc.domain.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

    Optional<KhachHang> findByTen(String username);
}
