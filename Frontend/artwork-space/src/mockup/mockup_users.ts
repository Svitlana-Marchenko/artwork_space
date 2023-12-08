import {Artwork} from "./mockup_artworks";

export type Password = {
    id: number;
    oldPassword: string;
    newPassword: string;
}
export enum UserRole {
    artist = "ARTIST",
    curator = "CURATOR",
    collectioneer = "COLLECTIONEER",
}

export type EditUser = {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
}
export interface NewUser {
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    role: UserRole;
    password: string;
}

export type LoginProps = {
    email: string;
    password: string;
}
export interface User extends NewUser{
    id: number;
    collection?: Artwork[]
}
export const users: User[] = [
    {
        id: 1,
        username: 'user1',
        firstName: 'John',
        lastName: 'Doe',
        email: 'john.doe@example.com',
        role: UserRole.artist,
        password: 'password1',
    },
    {
        id: 2,
        username: 'user2',
        firstName: 'Jane',
        lastName: 'Smith',
        email: 'jane.smith@example.com',
        role: UserRole.collectioneer,
        password: 'password2',
    },
    {
        id: 3,
        username: 'user3',
        firstName: 'Alice',
        lastName: 'Johnson',
        email: 'alice.johnson@example.com',
        role: UserRole.curator,
        password: 'password3',
    },
    {
        id: 4,
        username: 'user4',
        firstName: 'Bob',
        lastName: 'Williams',
        email: 'bob.williams@example.com',
        role: UserRole.artist,
        password: 'password4',
    },
    {
        id: 5,
        username: 'user5',
        firstName: 'Eva',
        lastName: 'Brown',
        email: 'eva.brown@example.com',
        role: UserRole.collectioneer,
        password: 'password5',
    },
    {
        id: 6,
        username: 'user6',
        firstName: 'David',
        lastName: 'Miller',
        email: 'david.miller@example.com',
        role: UserRole.curator,
        password: 'password6',
    },
    {
        id: 7,
        username: 'user7',
        firstName: 'Grace',
        lastName: 'Jones',
        email: 'grace.jones@example.com',
        role: UserRole.artist,
        password: 'password7',
    },
    {
        id: 8,
        username: 'user8',
        firstName: 'Sam',
        lastName: 'Wilson',
        email: 'sam.wilson@example.com',
        role: UserRole.collectioneer,
        password: 'password8',
    },
    {
        id: 9,
        username: 'user9',
        firstName: 'Linda',
        lastName: 'Lee',
        email: 'linda.lee@example.com',
        role: UserRole.curator,
        password: 'password9',
    },
    {
        id: 10,
        username: 'user10',
        firstName: 'Michael',
        lastName: 'Clark',
        email: 'michael.clark@example.com',
        role: UserRole.artist,
        password: 'password10',
    },
];
