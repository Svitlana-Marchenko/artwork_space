import axios from "axios";
import {EditExhibition, NewExhibition} from "../mockup/mockup_exhibitions";

export default class ExhibitionService {
    static async getAllActiveExhibitions() {
        try {
            const response = await axios.get('http://localhost:8080/exhibitions/active');
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про виставки:', error);
            throw error;
        }
    }

    static async getExhibitionById(id:string) {
        try {
            const response = await axios.get(`http://localhost:8080/exhibitions/${id}`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про виставку:', error);
            throw error;
        }
    }

    static async getExhibitionByCuratorId(id:string|number) {
        try {
            const response = await axios.get(`http://localhost:8080/exhibitions/curator/${id}`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про виставки куратора:', error);
            throw error;
        }
    }

    static async deleteExhibition(id:string) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };

            const response = await axios.delete(`http://localhost:8080/exhibitions/${id}`, config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про видалення виставки:', error);
            throw error;
        }
    }

    static async createExhibition(exhibition:NewExhibition) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };

            const response = await axios.post('http://localhost:8080/exhibitions', exhibition, config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про створення виставки:', error);
            throw error;
        }
    }

    static async editExhibition(exhibition:EditExhibition) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };

            const response = await axios.put('http://localhost:8080/exhibitions', exhibition, config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про редагування виставки:', error);
            throw error;
        }
    }
}