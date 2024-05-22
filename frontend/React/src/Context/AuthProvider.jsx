import React, {useEffect, useState} from 'react';
import AuthContext from './AuthContext.jsx';
import {useNavigate} from "react-router-dom";
import {useCookies} from "react-cookie";

const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const [cookies, setCookie, removeCookie] = useCookies(['user']);
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const userCookie = cookies.user;
        if (userCookie) {
            console.log('User cookie:', userCookie);
            console.log('Type of userCookie:', typeof userCookie);
            try {
                if (typeof userCookie === 'object') {
                    setUser(userCookie);
                    console.log('User set in AuthProvider:', userCookie);
                    setIsLoggedIn(true);
                    setLoading(false);
                } else {
                    const parsedUser = JSON.parse(userCookie);
                    setUser(parsedUser);
                    setIsLoggedIn(true);
                }
            } catch (error) {
                console.error('Error parsing user cookie:', error);
            }
        }
    }, []);



    const login = async (email, password) => {
        const response = await fetch('/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({email, password})
        });

        if (response.ok) {
            const loggedInUser = await response.json();
            setUser(loggedInUser);
            console.log(loggedInUser);
            setIsLoggedIn(true);
            setError(null);
            setCookie('user', JSON.stringify(loggedInUser), {path: '/'});
            navigate('/');

            } else {
                const errorMessage = await response.text();
                console.log(errorMessage);
                setError(errorMessage);
            }

    };

    const logout = () => {
        setIsLoggedIn(false);
        removeCookie('user');
    };

    return (
        <AuthContext.Provider value={{isLoggedIn, login, logout, user, error, loading}}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;