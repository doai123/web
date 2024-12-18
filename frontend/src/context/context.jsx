import React, { createContext, useContext, useState, useEffect } from 'react';

// Tạo context
const AuthContext = createContext();

// Hook để sử dụng context
export const useAuth = () => {
  return useContext(AuthContext);
};

// Provider chứa trạng thái makhachhang
export const AuthProvider = ({ children }) => {
  const [makhachhang, setMakhachhang] = useState(null);
  const [ten, setTen] = useState(null);
  const [isLogin, setIsLogin] = useState(false);

  // Khôi phục trạng thái từ localStorage khi trang được tải lại
  useEffect(() => {
    const storedMakhachhang = localStorage.getItem("makhachhang");
    const storedTen = localStorage.getItem("ten");
    const storedToken = localStorage.getItem("token");
    
    if (storedMakhachhang && storedTen && storedToken) {
      setMakhachhang(storedMakhachhang);
      setTen(storedTen);
      setIsLogin(true);
    }
  }, []);

  // Hàm đăng nhập, lưu makhachhang vào context và localStorage
  const login = (makhachhangId, token, ten, islogin) => {
    setMakhachhang(makhachhangId);
    setTen(ten);
    setIsLogin(islogin);
    console.log(ten, islogin);

    // Lưu thông tin vào localStorage
    localStorage.setItem("makhachhang", makhachhangId);
    localStorage.setItem("ten", ten);
    localStorage.setItem("token", token);
  };

  // Hàm đăng xuất, xóa makhachhang khỏi context và localStorage
  const logout = () => {
    setMakhachhang(null);
    setTen(null);
    setIsLogin(false);

    // Xóa thông tin khỏi localStorage khi đăng xuất
    localStorage.removeItem("makhachhang");
    localStorage.removeItem("ten");
    localStorage.removeItem("token");
  };

  return (
    <AuthContext.Provider value={{ makhachhang, ten, isLogin, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
