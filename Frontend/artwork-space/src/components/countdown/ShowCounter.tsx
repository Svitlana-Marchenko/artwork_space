import React from 'react';
import DateTimeDisplay from "./DateTimeDisplay";

interface ShowCounterProps {
    days: number;
    hours: number;
    minutes: number;
    seconds: number
}

const ShowCounter:React.FC<ShowCounterProps> = ({days, hours, seconds, minutes}) => {
    return (
        <div
            className="
                border-[#a62c2a]
                border-2
                rounded-lg
                flex
                flex-row
                items-center
                justify-between
                p-4
                space-x-3
                text-[#a62c2a]
                font-bold
                text-sm">
            <DateTimeDisplay value={days} type={'DAY'} />
            <p>:</p>
            <DateTimeDisplay value={hours} type={'HR'} />
            <p>:</p>
            <DateTimeDisplay value={minutes} type={'MIN'} />
            <p>:</p>
            <DateTimeDisplay value={seconds} type={'SEC'} />
        </div>
    );
};

export default ShowCounter;
