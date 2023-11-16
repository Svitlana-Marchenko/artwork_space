import React from 'react';
import ArtworkCard from "../listings/ArtworkCard";
import {Artwork, artworks} from "../../mockup/mockup_artworks";

interface ArtworksListProps {
    exhibitionId?: string;
}
const ArtworksList:React.FC<ArtworksListProps> = ({exhibitionId}) => {
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
                            id={artwork.id}
                            title={artwork.title}
                            technique={artwork.technique}
                            imageURL={artwork.imageURL}
                            width={artwork.width}
                            height={artwork.height}
                            firstName={artwork.artist.firstName}
                            lastName={artwork.artist.lastName}
                            username={artwork.artist.username}
                            artistId={artwork.artist.id}
                        />
                    ))}
                </div>
            ))}
        </div>
    );
};

export default ArtworksList;
