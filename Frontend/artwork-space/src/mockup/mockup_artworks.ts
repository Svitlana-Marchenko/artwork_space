export type Artwork = {
    id:number;
    title: string;
    technique: string;
    imageURL: string;
    width: number;
    height: number;
    firstName:string;
    lastName: string;
    username: string;
};
export const artworks: Artwork[] = [
    {
        id: 1,
        title: "Artwork 1",
        technique: "Oil on Canvas",
        firstName: "John",
        lastName: "Doe",
        username: "johndoe",
        imageURL: "../images/flowers.jpg",
        width: 400,
        height: 600,
    },
    {
        id:2,
        title: "Artwork 2",
        technique: "Watercolor",
        firstName: "Jane",
        lastName: "Smith",
        username: "janesmith",
        imageURL: "../images/1.jpg",
        width: 216,
        height: 270,
    },
    {
        id:3,
        title: "Artwork 3",
        technique: "Acrylic on Paper",
        firstName: "Alice",
        lastName: "Johnson",
        username: "alicejohnson",
        imageURL: "../images/2.jpg",
        width: 400,
        height: 300,
    },
    {
        id:4,
        title: "Artwork 4",
        technique: "Digital Art",
        firstName: "Bryan",
        lastName: "Jackson",
        username: "jackieee",
        imageURL: "../images/3.jpg",
        width: 300,
        height: 400,
    },
    {
        id:5,
        title: "Artwork 5",
        technique: "Sculpture",
        firstName: "Robert",
        lastName: "Brown",
        username: "robertbrown",
        imageURL: "../images/4.jpg",
        width: 300,
        height: 170,
    },
    {
        id:6,
        title: "Artwork 6",
        technique: "Mixed Media",
        firstName: "Bryan",
        lastName: "Jackson",
        username: "jackieee",
        imageURL: "../images/flowers.jpg",
        width: 400,
        height: 600,
    },
    {
        id:7,
        title: "Artwork 7",
        technique: "Pen and Ink",
        firstName: "Sarah",
        lastName: "Wilson",
        username: "sarahwilson",
        imageURL: "../images/1.jpg",
        width: 216,
        height: 270,
    },
    {
        id:8,
        title: "Artwork 8",
        technique: "Photography",
        imageURL: "../images/2.jpg",
        firstName: "Bryan",
        lastName: "Jackson",
        username: "jackieee",
        width: 400,
        height: 300,
    },
    {
        id:9,
        title: "Artwork 9",
        technique: "Collage",
        firstName: "Bryan",
        lastName: "Jackson",
        username: "jackieee",
        imageURL: "../images/3.jpg",
        width: 300,
        height: 400,
    },
    {
        id:10,
        title: "Artwork 10",
        technique: "Encaustic",
        firstName: "Michael",
        lastName: "Davis",
        username: "michaeldavis",
        imageURL: "../images/4.jpg",
        width: 300,
        height: 170,
    },


];
