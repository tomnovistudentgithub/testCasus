import React, { useContext } from 'react';
import Button from "../Button/Button.jsx";
import AuthContext from "../../Context/AuthContext.jsx";
import {Link} from "react-router-dom";

const Navbar = () => {
    const { isLoggedIn, login, logout } = useContext(AuthContext);



    return (
        <nav>
            {isLoggedIn ? (
                <Button onClick={logout}>Logout</Button>
            ) : (
                <Link to="/login">Login</Link>
            )}
        </nav>
    );
};

export default Navbar;
