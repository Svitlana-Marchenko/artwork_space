import axios from "axios";
import {NewRating} from "../mockup/mockup_artworks";
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

    static async getAllArtworksByArtistId(id:string|number) {
        try {
            const response = await axios.get(`http://localhost:8080/artworks/artist/${id}`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера:', error);
            throw error;
        }
    }

    static async getArtworkById(id:string) {
        try {
            const response = await axios.get(`http://localhost:8080/artworks/${id}`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера:', error);
            throw error;
        }
    }

    static async addRating(artworkId:number,rating:NewRating){
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };

            const response = await axios.post(`http://localhost:8080/artworks/${artworkId}/ratings`, rating, config);
            console.log(`Added rating with ID ${response.data.id} to artwork with ID: ${artworkId}`);
        } catch (error) {
            console.error('Error while adding rating:', error);
            throw error;
        }
    }

}