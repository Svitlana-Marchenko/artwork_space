import axios from "axios";
import {EditUser, NewUser, Password} from "../mockup/mockup_users";
//don't forget to install axios (try to press on the line above or write something like 'npm install axios' in terminal)
export default class UserService {
    static async getUserById(id:number|string) {
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

    static async changePassword(password: Password) {
        try {
            const response = await axios.put('http://localhost:8080/users/password', password)
            return response.data;
        } catch (error) {
            console.error('Помилка при зміні пароля:', error);
            throw error;
        }
    }

    static async changeProfile(user: EditUser) {
        try {
            const response = await axios.put('http://localhost:8080/users', user)
            return response.data;
        } catch (error) {
            console.error('Помилка при зміні профілю:', error);
            throw error;
        }
    }

    static async deleteUserById(id:number) {
        try {
            return await axios.delete(`http://localhost:8080/users/${id}`);
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера:', error);
            throw error;
        }
    }

    static async signIn(username: string, password: string) {
        try {
            const response = await axios.post('http://localhost:8080/signin', {username, password});
            return response.data;
        } catch (error) {
            console.error('Помилка авторизації з сервера:', error);
            throw error;
        }

    }
}