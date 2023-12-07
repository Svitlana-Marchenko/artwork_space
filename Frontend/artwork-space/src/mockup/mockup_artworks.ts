import {User} from "./mockup_users";

export interface NewArtwork {
    title: string;
    technique: string;
    imageURL: string;
    width: number;
    height: number;
    user: User;
}
export interface Artwork extends NewArtwork{
    id:number;
}