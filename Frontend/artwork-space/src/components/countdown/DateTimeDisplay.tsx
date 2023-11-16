import React from 'react';

interface DateTimeDisplay {
    value: number;
    type: string;
}

const DateTimeDisplay:React.FC<DateTimeDisplay> = ({ value, type }) => {
    return (
        <div className={'flex flex-col items-center'}>
            <p>{value}</p>
            <p>{type}</p>
        </div>
    );
};

export default DateTimeDisplay;
