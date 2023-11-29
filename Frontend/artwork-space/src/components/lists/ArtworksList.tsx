import React, {useEffect, useState} from 'react';
import ArtworkCard from "../listings/ArtworkCard";
import {Artwork} from "../../mockup/mockup_artworks";
import ArtworkService from "../../API/ArtworkService";

interface ArtworksListProps {
    exhibitionId?: string;
    artistId?: string;
}

//todo think about import {Artwork} from "../../mockup/mockup_artworks";

const ArtworksList:React.FC<ArtworksListProps> = ({exhibitionId, artistId}) => {

    const [artworks, setArtworks] = useState<Artwork[]>([]);

    useEffect(() => {
        ArtworkService.getAllArtworks()
            .then(data => setArtworks(data))
            .catch(error => console.error('Помилка при отриманні даних:', error));
    }, []);

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
                            firstName={artwork.user.firstName}
                            lastName={artwork.user.lastName}
                            username={artwork.user.username}
                            artistId={artwork.user.id}
                        />
                    ))}
                </div>
            ))}
        </div>
    );
};

export default ArtworksList;
