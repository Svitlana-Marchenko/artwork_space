import {User, users} from "./mockup_users";

export type Artwork = {
    id:number;
    title: string;
    technique: string;
    imageURL: string;
    width: number;
    height: number;
    user: User;
};

export const artworks: Artwork[] = [
    {
        id: 1,
        title: "Artwork 1",
        technique: "Oil on Canvas",
        user: users[0],
        imageURL: "../images/flowers.jpg",
        width: 400,
        height: 600,
    },
    {
        id:2,
        title: "Artwork 2",
        technique: "Watercolor",
        user: users[1],
        imageURL: "../images/1.jpg",
        width: 216,
        height: 270,
    },
    {
        id:3,
        title: "Artwork 3",
        technique: "Acrylic on Paper",
        user: users[2],
        imageURL: "../images/2.jpg",
        width: 400,
        height: 300,
    },
    {
        id:4,
        title: "Artwork 4",
        technique: "Digital Art",
        user: users[3],
        imageURL: "../images/3.jpg",
        width: 300,
        height: 400,
    },
    {
        id:5,
        title: "Artwork 5",
        technique: "Sculpture",
        user: users[4],
        imageURL: "../images/4.jpg",
        width: 300,
        height: 170,
    },
    {
        id:6,
        title: "Artwork 6",
        technique: "Mixed Media",
        user: users[5],
        imageURL: "../images/flowers.jpg",
        width: 400,
        height: 600,
    },
    {
        id:7,
        title: "Artwork 7",
        technique: "Pen and Ink",
        user: users[6],
        imageURL: "../images/1.jpg",
        width: 216,
        height: 270,
    },
    {
        id:8,
        title: "Artwork 8",
        technique: "Photography",
        imageURL: "../images/2.jpg",
        user: users[7],
        width: 400,
        height: 300,
    },
    {
        id:9,
        title: "Artwork 9",
        technique: "Collage",
        user: users[8],
        imageURL: "../images/3.jpg",
        width: 300,
        height: 400,
    },
    {
        id:10,
        title: "Artwork 10",
        technique: "Encaustic",
        user: users[9],
        imageURL: "../images/4.jpg",
        width: 300,
        height: 170,
    },


];
