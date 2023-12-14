import {Artwork} from "./artworkTypes";
import {User} from "./usersTypes";

export type Sales = {
    id: number;
    artwork: Artwork;
    price: number;
    dateOfBuying: Date;
    seller: User;
    buyer: User;
}