import axios from "axios";
//don't forget to install axios (try to press on the line above or write something like 'npm install axios' in terminal)
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

    static async getExhibitionByCuratorId(id:string) {
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
            const response = await axios.delete(`http://localhost:8080/exhibitions/${id}`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про видалення виставки:', error);
            throw error;
        }
    }
}