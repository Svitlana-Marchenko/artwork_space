import {Artwork} from "./mockup_artworks";
import {User} from "./mockup_users";

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
