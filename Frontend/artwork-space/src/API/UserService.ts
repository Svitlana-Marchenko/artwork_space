import axios from "axios";
import {NewUser} from "../mockup/mockup_users";
//don't forget to install axios (try to press on the line above or write something like 'npm install axios' in terminal)
export default class UserService {
    static async getUserById(id:string) {
        try {
            const response = await axios.get(`http://localhost:8080/users/${id}`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера:', error);
            throw error;
        }
    }

    static async createUser(user:NewUser) {
        try {
            const response = await axios.post('http://localhost:8080/users/new', user);
            return response.data;
        } catch (error) {
            console.error('Помилка при створенні користувача:', error);
            throw error;
        }
    }
}