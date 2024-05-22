import React, { useState } from 'react';
import DatePicker from 'react-datepicker';

function EventForm() {
    const [startDate, setStartDate] = useState(new Date());

    return (
        <form>
            <div>
                <label>
                    Event Date:
                    <DatePicker selected={startDate} onChange={(date) => setStartDate(date)} />
                </label>
            </div>

        </form>
    );
}

export default ReactForm;