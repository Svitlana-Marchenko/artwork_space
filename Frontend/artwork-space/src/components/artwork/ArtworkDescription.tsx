import React from 'react';
import {User} from "../../types/usersTypes";

interface ArtworkDescriptionProps {
    technique: string;
    width: number;
    height: number;
    currentBuyer?: User;
}
const ArtworkDescription:React.FC<ArtworkDescriptionProps> = ({technique, width, height, currentBuyer}) => {
    return (
        <div className="grid grid-cols-2 gap-x-8 my-6">
            <p>Technique</p>
            <p className={"text-gray-400"}>{technique}</p>
            <p>Size</p>
            <p className={"text-gray-400"}>{width}&times;{height} cm</p>
            {
                currentBuyer&&(
                    <>
                        <p>Current buyer</p>
                        <p className={"text-gray-400"}>{currentBuyer.firstName} {currentBuyer.lastName}</p>
                    </>
                )
            }
        </div>
    );
};

export default ArtworkDescription;
