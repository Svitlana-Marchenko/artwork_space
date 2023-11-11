import {createBrowserRouter} from "react-router-dom";
import Artworks from "./pages/Artworks";
import Exhibitions from "./pages/Exhibitions";
import Auctions from "./pages/Auctions";
import Profile from "./pages/Profile";
import Layout from "./layout";
import Error from "./error";
import App from "./pages/App";
import Artwork from "./pages/Artwork";
import Exhibition from "./pages/Exhibition";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout/>,
        errorElement: <Error/>,
        children: [
            {
              element: <App/>,
              index: true
            },
            {
                path: "/artworks",
                element:<Artworks/>
            },
            {
                path: "/artwork/:id",
                element: <Artwork/>
            },
            {
                path: "/exhibitions",
                element: <Exhibitions/>
            },
            {
                path: "/exhibition/:id",
                element: <Exhibition/>
            },
            {
                path: "/auctions",
                element: <Auctions/>
            },
            {
                path: "/profile",
                element: <Profile/>
            }
        ]
    }
]);
