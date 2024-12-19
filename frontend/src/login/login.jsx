import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './login.css';
import { useAuth } from "../context/context";

const LoginForm = () => {
  const { login } = useAuth();  // Lấy hàm login từ context
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      const response = await fetch('https://doubleshop.linkpc.net/endpoints/req/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams(formData),
      });

      const result = await response.json();
      if (response.ok) {
        // Xử lý đăng nhập thành công
        setSuccess('Login successful!');
        const { makhachhang, token, ten } = result;
        const isLogin = true;

        // Lưu makhachhang và token vào context
        login(makhachhang, token, ten, isLogin);
        console.log(isLogin,ten);

        // Chuyển hướng ngay lập tức sau khi đăng nhập thành công
        navigate('/');  // Chuyển hướng đến trang chủ hoặc bất kỳ trang nào bạn muốn
      } else {
        // Xử lý lỗi đăng nhập
        setError(result.error || 'Login failed. Please try again.');
      }
    } catch (err) {
      setError('An error occurred. Please try again later.');
    }
  };

  return (<div>
  <img src='/background_login.webp' className='image_login_auth' ></img>
    <div className="auth-container">
      
      <h2 className='h2_input-group'>Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="input-group">
          <input
            type="text"
            id="username"
            name="username"
            value={formData.username}
            onChange={handleInputChange}
            placeholder=" "
            required
          />
          <label htmlFor="username" className='username_au'>Username</label>
        </div>
        <div className="input-group">
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            placeholder=" "
            required
          />
          <label htmlFor="password" className='password_au'>Password</label>
        </div>
        <button type="submit" className="auth-button" >Login</button>
      </form>
      {error && <p className="error-message">{error}</p>}
      {success && <p className="success-message">{success}</p>}
    </div></div>
  );
};

export default LoginForm;
