import axios from 'axios';

async function handleEventRegistration(event, userId) {
    const button = document.getElementById(`register-button-${event.id}`);
    const isRegistered = button.textContent === "Deregister";

    const url = `/api/events/${event.id}/${isRegistered ? 'deregister' : 'register'}/${userId}`;
    const method = isRegistered ? 'delete' : 'post';


    try {
        const response = await axios({ method, url });
        if (response.status === 200) {
            button.textContent = isRegistered ? "Register" : "Deregister";
            const participantsIds = response.data.participantIds;
            console.log(participantsIds);
            const participantsElement = document.getElementById(`participants-${event.id}`);
            participantsElement.textContent = `Participants: ${event.participants.length}`;
        } else {
            alert(`Could not ${isRegistered ? 'deregister' : 'register'} for the event.`);
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

export default handleEventRegistration;