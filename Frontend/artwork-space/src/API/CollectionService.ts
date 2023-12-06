import axios from "axios";
export default class CollectionService {
    static async getArtworksFromCollection(id:number) {
        try {
            const response = await axios.get('http://localhost:8080/exhibitions/active');
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про виставки:', error);
            throw error;
        }
    }
}