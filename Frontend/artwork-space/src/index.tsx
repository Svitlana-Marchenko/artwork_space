import React from 'react';
import './index.css';
import {createRoot} from "react-dom/client";
import {router} from "./routing";
import {RouterProvider} from "react-router-dom";

createRoot(document.getElementById("root")!).render(
    <RouterProvider router={router} />
);
