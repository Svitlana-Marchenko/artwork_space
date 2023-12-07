import {createBrowserRouter} from "react-router-dom";
import Artworks from "./pages/artwork/Artworks";
import Exhibitions from "./pages/exhibition/Exhibitions";
import Auctions from "./pages/auction/Auctions";
import Profile from "./pages/Profile";
import Layout from "./layout";
import Error from "./error";
import App from "./pages/App";
import Artwork from "./pages/artwork/Artwork";
import Exhibition from "./pages/exhibition/Exhibition";
import Auction from "./pages/auction/Auction";
import {NewArtwork} from "./pages/artwork/NewArtwork";
import ExhibitionForm from "./pages/exhibition/ExhibitionForm";

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
                path: "/artworks/:id",
                element:<Artworks/>
            },
            {
                path: "/artwork/:id",
                element: <Artwork/>
            },
            {
                path: "/new-artwork",
                element:<NewArtwork/>
            },
            {
                path: "/exhibitions",
                element: <Exhibitions/>
            },
            {
                path: "/exhibitions/:id",
                element: <Exhibitions/>
            },
            {
                path: "/exhibition/:id",
                element: <Exhibition/>
            },
            {
                path: "/new-exhibition",
                element: <ExhibitionForm/>
            },
            {
                path: "/edit-exhibition/:id",
                element: <ExhibitionForm/>
            },
            {
                path: "/auctions",
                element: <Auctions/>
            },
            {
                path: "/auction/:id",
                element: <Auction/>
            },
            {
                path: "/my",
                element: <Profile/>
            },
            {
                path: "/profile/:id",
                element: <Profile/>
            }
        ]
    }
]);
