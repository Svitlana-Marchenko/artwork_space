import {Artwork} from "./mockup_artworks";
import {User, users} from "./mockup_users";

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

