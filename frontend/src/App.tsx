import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {AuthProvider} from "./contexts/authContext.tsx";
import {Home} from "./pages/Home/Home.tsx";
import {Login} from "./pages/Login/Login.tsx";
import Register from "./pages/Register/Register.tsx";
import {Navbar} from "./components/Navbar/Navbar.tsx";
import ProtectedRoute from "./components/ProtectedRoute/ProtectedRoute.tsx";
import {Profile} from "./pages/Profile/Profile.tsx";
import {ProtectedComponent} from "./components/ProtectedComponent/ProtectedComponent.tsx";
import React from "react";
import {Feed} from "./pages/Feed/Feed.tsx";
import {FindStartups} from "./pages/FindStartUp/FindStartUp.tsx";

function App() {

  return (
    <>
      <AuthProvider>
        <BrowserRouter>
          <ProtectedComponent><Navbar/></ProtectedComponent>
          <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/feed" element={<ProtectedRoute><Feed/></ProtectedRoute>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/register" element={<Register/>}/>
            <Route path="/findStartUp" element={<ProtectedRoute><FindStartups/></ProtectedRoute>}/>
            <Route path="/profile" element={<ProtectedRoute><Profile/></ProtectedRoute>}/>
          </Routes>
        </BrowserRouter>
      </AuthProvider>
    </>
  )
}

export default App
