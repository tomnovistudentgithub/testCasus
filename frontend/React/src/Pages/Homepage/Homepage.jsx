import React, {useContext, useEffect, useState} from 'react';
import {getAllEventList, getEventDetails} from "../../api/getEvents.js";
import EventBox from "../../Components/EventBox/EventBox.jsx";
import styles from './Homepage.module.css';
import AuthContext from "../../Context/AuthContext.jsx";

function Homepage() {
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const { user, isLoggedIn } = useContext(AuthContext);


    useEffect(() => {
        console.log("User in Homepage:", user);
        const fetchEvents = async () => {
            setLoading(true);
            try {
                const data = await getAllEventList();
                setData(data);
                console.log(data);
                console.log(user);
            } catch (error) {
                setError(error);
            }
            setLoading(false);
        };

        fetchEvents();
    }, []);


    return (
        <div className={styles['homepage-outer-container']}>
            <div className={styles['homepage-inner-container']}>
                <h1>Welcome to the homepage</h1>
                {loading && <p>Loading...</p>}
                <div className={styles['events-container']}>
                    {!loading && data && (
                        console.log('Rendering EventBox components:', user),
                            data.map(event => (
                                <EventBox key={event.id} event={event} userId={user ? user.id : null} isLoggedIn={isLoggedIn}/>
                            ))
                    )}
                </div>
            </div>
        </div>
    );
};

export default Homepage;