export type User = {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    role: string;
    password: string;
}
export const users: User[] = [
    {
        id: 1,
        username: 'user1',
        firstName: 'John',
        lastName: 'Doe',
        email: 'john.doe@example.com',
        role: 'user',
        password: 'password1',
    },
    {
        id: 2,
        username: 'user2',
        firstName: 'Jane',
        lastName: 'Smith',
        email: 'jane.smith@example.com',
        role: 'admin',
        password: 'password2',
    },
    {
        id: 3,
        username: 'user3',
        firstName: 'Alice',
        lastName: 'Johnson',
        email: 'alice.johnson@example.com',
        role: 'user',
        password: 'password3',
    },
    {
        id: 4,
        username: 'user4',
        firstName: 'Bob',
        lastName: 'Williams',
        email: 'bob.williams@example.com',
        role: 'user',
        password: 'password4',
    },
    {
        id: 5,
        username: 'user5',
        firstName: 'Eva',
        lastName: 'Brown',
        email: 'eva.brown@example.com',
        role: 'admin',
        password: 'password5',
    },
    {
        id: 6,
        username: 'user6',
        firstName: 'David',
        lastName: 'Miller',
        email: 'david.miller@example.com',
        role: 'user',
        password: 'password6',
    },
    {
        id: 7,
        username: 'user7',
        firstName: 'Grace',
        lastName: 'Jones',
        email: 'grace.jones@example.com',
        role: 'user',
        password: 'password7',
    },
    {
        id: 8,
        username: 'user8',
        firstName: 'Sam',
        lastName: 'Wilson',
        email: 'sam.wilson@example.com',
        role: 'admin',
        password: 'password8',
    },
    {
        id: 9,
        username: 'user9',
        firstName: 'Linda',
        lastName: 'Lee',
        email: 'linda.lee@example.com',
        role: 'user',
        password: 'password9',
    },
    {
        id: 10,
        username: 'user10',
        firstName: 'Michael',
        lastName: 'Clark',
        email: 'michael.clark@example.com',
        role: 'user',
        password: 'password10',
    },
];
