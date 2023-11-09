import React from 'react';
import {Artwork} from "../../mockup/mockup_artworks";
import NavigationLink from "../../header/NavigationLink";
import ScrollCarousel from 'scroll-carousel-react';
import ArtworkCard from "../listings/ArtworkCard";

interface ExhibitionListProps {
    id: string;
    curatorName: string;
    title: string;
    artworks: Artwork[];
    startDate: Date;
    endDate: Date;
}
const ExhibitionList:React.FC<ExhibitionListProps> = ({
                                                 id,
                                                 curatorName,
                                                 title,
                                                 artworks,
                                                 startDate,
                                                 endDate
}) => {

    return (
        <div>
            <div className={"flex flex-row justify-between mt-8 mb-3"}>
                <div>
                    <p className={"text-3xl font-bold"}>{title.toUpperCase()}</p>
                    <p className={"text-gray-400"}>Collected by {curatorName}</p>
                </div>
                <div className="text-right">
                    <p className={"text-3xl font-bold text-gray-400"}>{startDate.toDateString().toUpperCase()} - {endDate.toDateString().toUpperCase()}</p>
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
                                firstName={artwork.firstName}
                                lastName={artwork.lastName}
                                username={artwork.username}
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
