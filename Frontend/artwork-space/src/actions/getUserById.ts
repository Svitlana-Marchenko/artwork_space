import {User, users} from "../mockup/mockup_users";
import {convertToInt} from "./functions";

export const getUserByIdAsync = async (id: string): Promise<User> => {
    return users[convertToInt(id) - 1];
};
