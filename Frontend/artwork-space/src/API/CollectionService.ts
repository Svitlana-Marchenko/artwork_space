import axios from "axios";
import {NewAuction} from "../types/auctionsTypes";
import {NewCollection} from "../types/collectionTypes";
import {Artwork} from "../types/artworkTypes";
export default class CollectionService {

    static async getAllCollectionsByUser(userId:string|number) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };
            const response = await axios.get(`http://localhost:8080/collections/user/${userId}`, config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про колекцію:', error);
            throw error;
        }
    }
    static async getArtworksFromAllCollectionsByUser(userId:number) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };
            const response = await axios.get(`http://localhost:8080/collections/user/${userId}/all`, config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про колекцію:', error);
            throw error;
        }
    }
    static async deleteCollectionById(id: string){
        try {
            const token = localStorage.getItem('token');
            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };
            const response = await axios.delete(`http://localhost:8080/collections/${id}`,config)
            return response.data;
        } catch (error) {
            console.error('Помилка при видаленні даних з сервера:', error);
            throw error;
        }
    }
    static async getCollectionById(id: string) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };
            const response = await axios.get(`http://localhost:8080/collections/${id}`, config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про колекцію:', error);
            throw error;
        }
    }
    static async getArtworksByCollectionId(id:number) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            };
            const response = await axios.get(`http://localhost:8080/collections/${id}/artworks`, config);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про колекцію:', error);
            throw error;
        }
    }
    static async createCollection(collection: NewCollection) {
        try {
            const token = localStorage.getItem('token');
            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            }
            const response = await axios.post('http://localhost:8080/collections', collection, config);
            return response.data;
        } catch (e) {
            console.error('Помилка при створенні колекції:', e);
            throw e;
        }
    }
    static async deleteArtworkFromCollection(collectionId:number, artworkId:number) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
            };

            const response = await axios.delete(`http://localhost:8080/collections/${collectionId}/removeArtwork?artworkId=${artworkId}`, config
             );
            return response.data;
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
    static async addArtworkToCollection(collectionId:number, artworkId:number) {
        try {
            const token = localStorage.getItem('token');

            const config = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
            };

            const response = await axios.post(`http://localhost:8080/collections/${collectionId}/addArtwork?artworkId=${artworkId}`, null, config);
        return response.data;
    } catch (error) {
        console.error( error);
        throw error;
    }
}
}