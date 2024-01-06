import {User} from "./usersTypes";
import {Collection} from "./collectionTypes";
export interface NewRating {
    rate: number;
    user: User;
    comment: string;
}
export interface Rating extends NewRating{
    id: number;
}
export interface NewArtwork {
    title: string;
    description: string;
    technique: string;
    width: number;
    height: number;
    user: User;
}
export interface Artwork extends NewArtwork{
    id:number;
    imageURL: string;
    ratings: Rating[];
}