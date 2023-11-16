import React from 'react';
import {exhibitions} from "../../mockup/mockup_exhibitions";
import ExhibitionList from "../../components/lists/ExhibitionList";

const Exhibitions = () => {

    return (
        <div className="mx-32">
            {exhibitions.map((exhibition) => {
                return (
                    <ExhibitionList
                        id={exhibition.id.toString()}
                        curatorName={exhibition.curatorName}
                        title={exhibition.title}
                        artworks={exhibition.artworks}
                        startDate={exhibition.startDate}
                        endDate={exhibition.endDate}
                    />
                )
            })}
        </div>
    );
};

export default Exhibitions;
