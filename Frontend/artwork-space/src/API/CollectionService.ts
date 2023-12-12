import axios from "axios";
export default class CollectionService {
    static async getArtworksFromCollection(id:number) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };
            const response = await axios.get(`http://localhost:8080/users/${id}/collection`, config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про картини з колекції:', error);
            throw error;
        }
    }
    static async deleteArtworkFromCollection(userId:number, artworkId:number) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
            };

            const response = await axios.put(`http://localhost:8080/users/${userId}/collection/remove`, JSON.stringify(artworkId), config
             );
            return response.data;
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
    static async addArtworkToCollection(userId:number, artworkId:number) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
            };

            const response = await axios.put(`http://localhost:8080/users/${userId}/collection/add`, JSON.stringify(artworkId), config);
        return response.data;
    } catch (error) {
        console.error( error);
        throw error;
    }
}
}