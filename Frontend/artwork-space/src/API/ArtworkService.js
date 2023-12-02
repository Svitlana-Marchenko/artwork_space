import axios from "axios";
//don't forget to install axios (try to press on the line above or write something like 'npm install axios' in terminal)
export default class ArtworkService {

    static async getAllArtworks() {
        try {
            const response = await axios.get('http://localhost:8080/artworks');
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера:', error);
            throw error;
        }
    }

    static async getAllArtworksByArtistId(id) {
        try {
            const response = await axios.get(`http://localhost:8080/artworks/artist/${id}`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера:', error);
            throw error;
        }
    }

    static async getArtworkById(id) {
        try {
            const response = await axios.get(`http://localhost:8080/artworks/${id}`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера:', error);
            throw error;
        }
    }

}