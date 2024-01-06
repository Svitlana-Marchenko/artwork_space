import {Artwork} from "./artworkTypes";
import {User} from "./usersTypes";

export interface NewCollection {
    owner: User;
    title: string;
    artworks: Artwork[];
}

export interface Collection extends NewCollection {
    id: number;
}

