import {Artwork} from "./artworkTypes";
import {User} from "./usersTypes";

export type EditExhibition = {
    id: number;
    title: string;
    description: string;
}
export interface NewExhibition {
    curator: User;
    title: string;
    description: string;
    artworks: Artwork[];
    startDate: Date;
    endDate: Date;
}

export interface Exhibition extends NewExhibition {
    id: number;
}

