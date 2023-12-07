import axios from "axios";
export default class CollectionService {
    static async getArtworksFromCollection(id:number) {
        try {
            const response = await axios.get(`http://localhost:8080/users/${id}/collection`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про картини з колекції:', error);
            throw error;
        }
    }
    static async deleteArtworkFromCollection(userId:number, artworkId:number) {
        try {
            const response = await axios.put(`http://localhost:8080/users/${userId}/collection/remove`, JSON.stringify(artworkId), {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
    static async addArtworkToCollection(userId:number, artworkId:number) {
        try {
            const response = await axios.put(`http://localhost:8080/users/${userId}/collection/add`, JSON.stringify(artworkId), {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response.data;
    } catch (error) {
        console.error( error);
        throw error;
    }
}
}