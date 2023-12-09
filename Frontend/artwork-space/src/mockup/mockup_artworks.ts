import {User} from "./mockup_users";
export interface NewRating {
    id: number;
    rate: number;
    user: User;
    comment: string;
}
export interface Rating extends NewRating{
    id: number;
}
export interface NewArtwork {
    title: string;
    technique: string;
    imageURL: string;
    width: number;
    height: number;
    user: User;
    ratings: Rating[];
}
export interface Artwork extends NewArtwork{
    id:number;
}