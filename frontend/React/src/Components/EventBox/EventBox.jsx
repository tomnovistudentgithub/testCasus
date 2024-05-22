import styles from './EventBox.module.css';
import sportImages from "../../helper/sportImages.js";
import {useContext, useEffect} from "react";
import AuthContext from "../../Context/AuthContext.jsx";
import handleEventRegistration from "../../api/handleEventRegistration.js";

export default function EventBox({ event, userId, isLoggedIn }) {
    let imageUrl = sportImages[event.sportName];


    useEffect(() => {
        console.log("EventBox useEffect");
        console.log(event);
        console.log(userId);
    }, []);


    useEffect(() => {
        console.log("User ID:", userId);
    }, [userId]);

    const style = {
        backgroundImage: `linear-gradient(rgba(255, 255, 255, 0.8), rgba(255, 255, 255, 0.8)), url(${imageUrl})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center'

    };

    const date = new Date(event.startDate);
    const formattedDate = `${date.getDate()}-${date.getMonth()+1}-${date.getFullYear()}`;

    const startTime =  event.startTime;


    return (
        <div className={styles['eventbox-item']} style={style}>
            <h2>{event.name}</h2>
            <p>{event.description}</p>
            <p>{formattedDate}</p>
            <p>{startTime}</p>
            <p>{event.locationName}</p>
            <p>{event.targetAudience}</p>
            <p>{event.availablePlaces}</p>

            <button id={`register-button-${event.id}`} onClick={() => handleEventRegistration(event, userId)} disabled={!isLoggedIn}>Register</button>
        </div>
    );
}