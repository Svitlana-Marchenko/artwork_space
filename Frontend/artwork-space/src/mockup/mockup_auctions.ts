import {Artwork} from "./mockup_artworks";
import {User, users} from "./mockup_users";

export type Auction = {
    id: number;
    title: string;
    description: string;
    artwork: Artwork;
    startingPrice: number;
    bid: number;
    closingTime: Date;
    currentBuyer: User;
    currentBid: number;
}
