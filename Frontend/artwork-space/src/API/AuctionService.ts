import axios from "axios";
//don't forget to install axios (try to press on the line above or write something like 'npm install axios' in terminal)
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
}