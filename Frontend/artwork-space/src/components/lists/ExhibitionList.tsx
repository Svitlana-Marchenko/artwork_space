import React from 'react';
import {Artwork} from "../../mockup/mockup_artworks";
import NavigationLink from "../../header/NavigationLink";
import ScrollCarousel from 'scroll-carousel-react';
import ArtworkCard from "../listings/ArtworkCard";
import {useNavigate} from "react-router-dom";
import {User} from "../../mockup/mockup_users";

interface ExhibitionListProps {
    id: string;
    curator: User;
    title: string;
    artworks: Artwork[];
    startDate: Date;
    endDate: Date;
}
const ExhibitionList:React.FC<ExhibitionListProps> = ({
                                                 id,
                                                 curator,
                                                 title,
                                                 artworks,
                                                 startDate,
                                                 endDate
}) => {
const navigate = useNavigate();
    return (
        <div>
            <div className={"flex flex-row justify-between mt-6 mb-3"}>
                <div>
                    <p className={"text-2xl font-bold"}>{title.toUpperCase()}</p>
                    <p className={"text-gray-400"}>Collected by
                        <span
                            className={'underline underline-offset-2 cursor-pointer ml-1'}
                            onClick={()=>{navigate(`/exhibitions/${curator.id}`)}}
                        >
                            {curator.firstName} {curator.lastName}
                        </span>
                    </p>
                </div>
                <div className="text-right">
                    <p className={"text-2xl font-bold text-gray-400"}>{startDate.toDateString().toUpperCase()} - {endDate.toDateString().toUpperCase()}</p>
                    <NavigationLink title={"View all"} path={`/exhibition/${id}`}/>
                </div>
            </div>
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
                                id={artwork.id}
                                title={artwork.title}
                                technique={artwork.title}
                                firstName={artwork.artist.firstName}
                                lastName={artwork.artist.lastName}
                                username={artwork.artist.username}
                                artistId={artwork.artist.id}
                                imageURL={artwork.imageURL}
                                width={artwork.width}
                                height={artwork.height} sm
                            />
                        )
                    })
                }
            </ScrollCarousel>
        </div>
    );
};

export default ExhibitionList;
