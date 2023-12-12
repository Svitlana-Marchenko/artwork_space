import axios from "axios";
import {Auction, NewAuction} from "../types/auctionsTypes";

export default class AuctionService {
    static async getAllAuctions() {
        try {
            const response = await axios.get('http://localhost:8080/collectioneer/auctions/available');
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про аукціон:', error);
            throw error;
        }
    }

    static async getAuctionById(id:string) {
        try {
            const response = await axios.get(`http://localhost:8080/collectioneer/auctions/${id}`);
            return response.data;
        } catch (error) {
            console.error('Помилка при отриманні даних з сервера про аукціон:', error);
            throw error;
        }
    }

    static async createAuction(auction: NewAuction) {
        try {
            const token = localStorage.getItem('token');
            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            }
            const response = await axios.post('http://localhost:8080/auctions', auction, config);
            return response.data;
        } catch (e) {
            console.error('Помилка при створенні аукціону:', e);
            throw e;
        }
    }

    static async placeBid(id: string | number, bid: number) {
        try {
            const token = localStorage.getItem('token');
            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            }
            const response = await axios.put(`http://localhost:8080/collectioneer/auctions/${id}/placeBid`,  JSON.stringify(bid), config);
            return response.data;
        } catch (e) {
            console.error('Помилка при створенні ставки на аукціон:', e);
            throw e;
        }
    }
}