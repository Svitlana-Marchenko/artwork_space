import {User} from "./usersTypes";
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
/*
export interface NewArtwork {
    title: string;
    technique: string;
    width: number;
    height: number;
    user: User;
}

export interface Artwork extends NewArtwork{
    id:number;
    ratings: Rating[];
    imageURL: string;
}
 */