import './App.css'
import 'react-datepicker/dist/react-datepicker.css';
import {BrowserRouter, Route, Router, Routes} from "react-router-dom";
import Homepage from "./Pages/Homepage/Homepage.jsx";
import AuthProvider from "./Context/AuthProvider.jsx";
import Navbar from "./Components/Navbar/Navbar.jsx";
import LoginPage from "./Pages/LoginPage/LoginPage.jsx";

function App() {


    return (
        <BrowserRouter>
        <AuthProvider>
            <Navbar />

            <Routes>
                <Route path="/" element={<Homepage />} />
                <Route path="/login" element={<LoginPage />} />
            </Routes>
        </AuthProvider>
        </BrowserRouter>

    )
}

export default App
