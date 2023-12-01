import {Artwork, artworks} from "./mockup_artworks";
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

export const auctions: Auction[] = [
    {
        id: 1,
        title: 'Auction 1',
        description: 'Description for Auction 1',
        artwork: artworks[0],
        startingPrice: 100,
        bid: 0,
        closingTime: new Date('2023-12-01'),
        currentBuyer: users[0],
        currentBid: 0,
    },
    {
        id: 2,
        title: 'Auction 2',
        description: 'Description for Auction 2',
        artwork: artworks[1],
        startingPrice: 150,
        bid: 0,
        closingTime: new Date('2023-12-02'),
        currentBuyer: users[1],
        currentBid: 0,
    },
    {
        id: 3,
        title: 'Auction 3',
        description: 'Description for Auction 3',
        artwork: artworks[2],
        startingPrice: 120,
        bid: 0,
        closingTime: new Date('2023-12-03'),
        currentBuyer: users[2],
        currentBid: 0,
    },
    {
        id: 4,
        title: 'Auction 4',
        description: 'Description for Auction 4',
        artwork: artworks[3],
        startingPrice: 180,
        bid: 0,
        closingTime: new Date('2023-12-04'),
        currentBuyer: users[3],
        currentBid: 0,
    },
    {
        id: 5,
        title: 'Auction 5',
        description: 'Description for Auction 5',
        artwork: artworks[4],
        startingPrice: 250,
        bid: 0,
        closingTime: new Date('2023-12-05'),
        currentBuyer: users[4],
        currentBid: 0,
    },
    {
        id: 6,
        title: 'Auction 6',
        description: 'Description for Auction 6',
        artwork: artworks[5],
        startingPrice: 130,
        bid: 0,
        closingTime: new Date('2023-12-06'),
        currentBuyer: users[5],
        currentBid: 0,
    },
    {
        id: 7,
        title: 'Auction 7',
        description: 'Description for Auction 7',
        artwork: artworks[6],
        startingPrice: 170,
        bid: 0,
        closingTime: new Date('2023-12-07'),
        currentBuyer: users[6],
        currentBid: 0,
    },
    {
        id: 8,
        title: 'Auction 8',
        description: 'Description for Auction 8',
        artwork: artworks[7],
        startingPrice: 200,
        bid: 0,
        closingTime: new Date('2023-12-08'),
        currentBuyer: users[7],
        currentBid: 0,
    },
    {
        id: 9,
        title: 'Auction 9',
        description: 'Description for Auction 9',
        artwork: artworks[8],
        startingPrice: 160,
        bid: 0,
        closingTime: new Date('2023-12-09'),
        currentBuyer: users[8],
        currentBid: 0,
    },
    {
        id: 10,
        title: 'Auction 10',
        description: 'Description for Auction 10',
        artwork: artworks[9],
        startingPrice: 200,
        bid: 0,
        closingTime: new Date('2023-12-10'),
        currentBuyer: users[9],
        currentBid: 0,
    },
];
