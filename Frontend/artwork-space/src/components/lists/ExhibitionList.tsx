import React, {useEffect, useState} from 'react';
import {Artwork as ArtworkType, Artwork} from "../../types/artworkTypes";
import NavigationLink from "../../header/NavigationLink";
import ScrollCarousel from 'scroll-carousel-react';
import ArtworkCard from "../listings/ArtworkCard";
import {useNavigate} from "react-router-dom";
import {User} from "../../types/usersTypes";
import CollectionService from "../../API/CollectionService";
import {Collection} from "../../types/collectionTypes";

interface ExhibitionListProps {
    id: string;
    curator: User;
    title: string;
    artworks: Artwork[];
    startDate: Date;
    endDate: Date;
    collections:Collection[]
}
const ExhibitionList:React.FC<ExhibitionListProps> = ({
                                                 id,
                                                 curator,
                                                 title,
                                                 artworks,
                                                 startDate,
                                                 endDate,
    collections
}) => {
   // const [favoriteArtworksByCollection, setFavoriteArtworksByCollection] = useState<Record<number, ArtworkType[]>>({});
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;

    const navigate = useNavigate();
    return (
        <div>
            <div className={"flex flex-row justify-between mt-6 mb-3"}>
                <div>
                    <p className={"text-2xl font-bold"}>{title.toUpperCase()}</p>
                    <p className={"text-gray-400"}>Collected by
                        <span
                            className={'underline underline-offset-2 cursor-pointer ml-1'}
                            onClick={()=>{navigate(`/profile/${curator.id}`)}}
                        >
                            {curator.firstName} {curator.lastName}
                        </span>
                        , artworks collected: {artworks.length}
                    </p>
                </div>
                <div className="text-right">
                    <p className={"text-2xl font-bold text-gray-400"}>{new Date(startDate).toDateString()} - {new Date(endDate).toDateString()}</p>
                    <NavigationLink title={"View all"} path={`/exhibition/${id}`}/>
                </div>
            </div>
            {
                artworks.length>2&&(
                    <ScrollCarousel
                        autoplay
                        autoplaySpeed={1}
                        smartSpeed
                        direction="rtl"
                        speed={5}
                        margin={20}
                    >
                        {
                            artworks.map((artwork) => {
                                return (
                                    <ArtworkCard
                                        key={artwork.id}
                                        artwork={artwork}
                                        sm
                                        disabled
                                        favoriteArtworks={collections}
                                    />
                                )
                            })
                        }
                    </ScrollCarousel>
                )
            }
        </div>
    );
};

export default ExhibitionList;
