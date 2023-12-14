import axios from "axios";

export default class SalesService {
    static async getSalesByCollectioneerId(id: number|string) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
                params: {
                    id: id
                }
            };
            const response = await axios.get('http://localhost:8080/sales/byCollectioneer',config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера:', error);
            throw error;
        }
    }

    static async getSalesByArtistId(id: number|string) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
                params: {
                    id: id
                }
            };
            const response = await axios.get('http://localhost:8080/sales/byArtist',config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера:', error);
            throw error;
        }
    }
}