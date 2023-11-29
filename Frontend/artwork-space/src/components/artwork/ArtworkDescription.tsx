import React from 'react';

interface ArtworkDescription {
    technique: string;
    width: number;
    height: number;
}
//todo think/add artwork description field
const ArtworkDescription:React.FC<ArtworkDescription> = ({technique, width, height}) => {
    return (
        <div className="grid grid-cols-2 gap-x-8 my-6">
            <p>Technique</p>
            <p className={"text-gray-400"}>{technique}</p>
            <p>Size</p>
            <p className={"text-gray-400"}>{width}&times;{height} cm</p>
        </div>
    );
};

export default ArtworkDescription;
