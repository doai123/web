import React from "react";
import Header from "./Header/Header";
import Banner from "./banner/Banner";
import MainThanWeb from "./slideSanPham/MainThanWeb";
import './App.css'
import Footer from "./footer/Footer";
import GetThuongHieu from "./slideSanPham/GetThuongHieu";
import SlideAnh from "./slide/SlideAnh";
function App() {
  return (
    <>
<Header />
<Banner />
<MainThanWeb />
<SlideAnh />
<GetThuongHieu />
<Footer />
     </>
  )
}

export default App;
