import {Artwork} from "./artworkTypes";
import {User} from "./usersTypes";

export interface NewAuction {
    artwork: Artwork;
    startingPrice: number;
    bid: number;
    closingTime: Date;
    user: User;
    currentBid: number;
}
export interface Auction extends NewAuction{
    id: number;
}
