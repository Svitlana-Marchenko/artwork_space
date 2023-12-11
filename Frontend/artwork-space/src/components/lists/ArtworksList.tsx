import React, {useEffect, useState} from 'react';
import ArtworkCard from "../listings/ArtworkCard";
import {Artwork} from "../../mockup/mockup_artworks";
import ArtworkService from "../../API/ArtworkService";
import Empty from "../../empty";

interface ArtworksListProps {
   artworks: Artwork[];
   onAddToExhibition?: (artwork: Artwork) => void;
}

const ArtworksList:React.FC<ArtworksListProps> = ({artworks, onAddToExhibition}) => {

    if(artworks.length ===0){
        return <Empty message={"Theres no artworks here"} link={`/artworks`} dist={'all artworks'}/>
    }

    const totalHeight = artworks.reduce((sum, artwork) => sum + artwork.height, 0);
    const targetHeight = totalHeight / 3;

    const columns: Array<Array<Artwork>> = [[], [], []];

    let currentColumnIndex = 0;
    let currentColumnHeight = 0;

    artworks.forEach((artwork) => {
        if (currentColumnHeight + artwork.height <= targetHeight*1.2 || currentColumnIndex === 2) {
            columns[currentColumnIndex].push(artwork);
            currentColumnHeight += artwork.height;
        } else {
            currentColumnIndex++;

            if (!columns[currentColumnIndex]) {
                columns[currentColumnIndex] = [];
            }

            columns[currentColumnIndex].push(artwork);
            currentColumnHeight = artwork.height;
        }
    });

    return (
        <div className={"flex flex-row gap-8"}>
            {columns.map((column, columnIndex) => (
                <div key={columnIndex} className="flex flex-col w-1/3 gap-8">
                    {column.map((artwork, index) => (
                        <ArtworkCard
                            key={index}
                            artwork={artwork}
                            onAddToExhibition={onAddToExhibition}
                        />
                    ))}
                </div>
            ))}
        </div>
    );
};

export default ArtworksList;
