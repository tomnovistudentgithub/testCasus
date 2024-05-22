import axios from 'axios';

export async function getEventDetails(eventId) {
    return await axios.get(`/api/events/${eventId}/details`)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.error('There was an error!', error);
        });
}

export async function getAllEventList() {
    return await axios.get('/api/events/all')
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.error('There was an error!', error);
        });
}