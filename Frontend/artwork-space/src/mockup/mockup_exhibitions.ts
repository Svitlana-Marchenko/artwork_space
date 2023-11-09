import {Artwork, artworks} from "./mockup_artworks";

export type Exhibition = {
    id: number;
    curatorName: string;
    title: string;
    description: string;
    artworks: Artwork[];
    startDate: Date;
    endDate: Date;
}
function generateCuratorFullName(): string {
    const firstNames = ['John', 'Jane', 'Alice', 'Bob', 'Eva', 'David', 'Sophia', 'Michael', 'Olivia', 'Daniel'];
    const lastNames = ['Smith', 'Johnson', 'Williams', 'Brown', 'Jones', 'Garcia', 'Miller', 'Davis', 'Rodriguez', 'Martinez'];

    const randomFirstName = firstNames[Math.floor(Math.random() * firstNames.length)];
    const randomLastName = lastNames[Math.floor(Math.random() * lastNames.length)];

    return `${randomFirstName} ${randomLastName}`;
}
export const exhibitions: Exhibition[] = [
    {
        id: 1,
        curatorName: generateCuratorFullName(),
        title: 'Art Expo 2023',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-01-10'),
        endDate: new Date('2023-01-20'),
    },
    {
        id: 2,
        curatorName: generateCuratorFullName(),
        title: 'Modern Masters Showcase',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-02-15'),
        endDate: new Date('2023-02-25'),
    },
    {
        id: 3,
        curatorName: generateCuratorFullName(),
        title: 'Sculpture Symposium',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-03-05'),
        endDate: new Date('2023-03-15'),
    },
    {
        id: 4,
        curatorName: generateCuratorFullName(),
        title: 'Abstract Art Extravaganza',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-04-01'),
        endDate: new Date('2023-04-10'),
    },
    {
        id: 5,
        curatorName: generateCuratorFullName(),
        title: 'Impressionist Impressions',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-05-15'),
        endDate: new Date('2023-05-25'),
    },
    {
        id: 6,
        curatorName: generateCuratorFullName(),
        title: 'Photography Focus',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-06-05'),
        endDate: new Date('2023-06-15'),
    },
    {
        id: 7,
        curatorName: generateCuratorFullName(),
        title: 'Cultural Canvas',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-07-01'),
        endDate: new Date('2023-07-10'),
    },
    {
        id: 8,
        curatorName: generateCuratorFullName(),
        title: 'Futuristic Visions',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-08-15'),
        endDate: new Date('2023-08-25'),
    },
    {
        id: 9,
        curatorName: generateCuratorFullName(),
        title: 'Nature\'s Palette',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-09-05'),
        endDate: new Date('2023-09-15'),
    },
    {
        id: 10,
        curatorName: generateCuratorFullName(),
        title: 'Contemporary Creations',
        description: 'Founded in 1986, Swiss Institute (SI) is an independent non-profit space for international contemporary art located in New York City\'s East Village. Committed to the highest standards of curatorial and educational excellence, Swiss Institute serves as a platform for emerging artists, catalyzes new contexts for celebrated work, and fosters appreciation for under-recognized positions.\n' +
            '\n' +
            'SI\'s Annual Benefit Auction is the organization\'s largest fundraising event of the year. Featuring exceptional works by artists including John Armleder, Vivian Suter, Francesco Clemente, Leslie Hewitt, Sanya Kantarovsky, Pamela Rosenkranz, Peter Fischli/David Weiss, amongst others, the 2023 Benefit Auction will help fund exhibitions, public programs and education workshops for children, teens and seniors. Most importantly, it keeps Swiss Institute open to the public, with all exhibitions, events and programs presented entirely free-of-charge.\n' +
            '\n' +
            'In advance of the auction, please browse lots and place max bids before live bidding begins on Saturday, November 11th at 8:00pm EST.\n' +
            '\n' +
            'Registration for this auction will close on Saturday, November 11th at 6:30pm EST.\n' +
            '\n' +
            'Each work ships from a different location, as noted on the individual lot pages. Shipping costs are the responsibility of the buyer.',
        artworks: artworks,
        startDate: new Date('2023-10-01'),
        endDate: new Date('2023-10-10'),
    },
];

