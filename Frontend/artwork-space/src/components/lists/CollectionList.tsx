import React from 'react';
import { Artwork as ArtworkType, Artwork } from '../../types/artworkTypes';
import NavigationLink from '../../header/NavigationLink';
import ArtworkCard from '../listings/ArtworkCard';
import { useNavigate } from 'react-router-dom';
import { User } from '../../types/usersTypes';
import { Collection } from '../../types/collectionTypes';

interface CollectionListProps {
    id: string;
    owner: User;
    title: string;
    artworks: Artwork[];
    collections: Collection[];
}

const CollectionList: React.FC<CollectionListProps> = ({
                                                           id,
                                                           owner,
                                                           title,
                                                           artworks,
                                                           collections,
                                                       }) => {
    const navigate = useNavigate();
    const visibleArtworks = artworks.slice(0, 5);

    return (
        <div>
            <div className={'flex flex-row justify-between mt-6 mb-3'}>
                <div>
                    <p className={'text-2xl font-bold'}>{title.toUpperCase()}</p>
                    <p className={'text-gray-400'}>
                        Collected by
                        <span
                            className={'underline underline-offset-2 cursor-pointer ml-1'}
                            onClick={() => {
                                navigate(`/profile/${owner.id}`);
                            }}
                        >
              {owner.firstName} {owner.lastName}
            </span>
                        , artworks collected: {artworks.length}
                    </p>
                </div>
                <div className="text-right">
                    <NavigationLink title={'View all'} path={`/collection/${id}`} />
                </div>
            </div>
            <div className="flex overflow-x-auto">
                {visibleArtworks.map((artwork) => (
                    <ArtworkCard
                        key={artwork.id}
                        artwork={artwork}
                        sm
                        disabled
                        favoriteArtworks={collections}

                    />
                ))}
            </div>
        </div>
    );
};

export default CollectionList;
