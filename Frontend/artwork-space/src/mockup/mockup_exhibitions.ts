import {Artwork} from "./mockup_artworks";
import {User, users} from "./mockup_users";

export type Exhibition = {
    id: number;
    curator: User;
    title: string;
    description: string;
    artworks: Artwork[];
    startDate: Date;
    endDate: Date;
}

