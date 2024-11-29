import React, { useState, useEffect } from "react";
import { Link } from 'react-router-dom';
import "./Header.css";

function Header() {
  const [cartCount, setCartCount] = useState(0);

  useEffect(() => {
    // Update cart count when the component mounts
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    setCartCount(cart.length);
  }, []); // Runs only on initial mount

  return (
    <div className="navbar">
      <div className="navbar-left">
        <Link to="/" className="nav-link"><h3>Trang Chủ</h3></Link>
        <Link to="/giohang" className="nav-link"><h3>Giỏ Hàng</h3></Link>
        <a href="Lienheadmin.html"> <h3 className="nav-link">Liên Hệ</h3></a>
        <a href="GioiThieu.html"><h3 className="nav-link">Giới Thiệu</h3></a>
      </div>
      <div className="navbar-right">
        <a href="https://ditcuchungmay.linkpc.net:8080/req/login"><h3 className="nav-link">Đăng Nhập</h3></a>
        <a href="https://ditcuchungmay.linkpc.net:8080/req/signup"><h3 className="nav-link">Đăng Ký</h3></a>
      </div>
    </div>
  );
}

export default Header;
