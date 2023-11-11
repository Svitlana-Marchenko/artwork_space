import React from 'react';
import NavigationLink from "./NavigationLink";

const Navigation = () => {
    return (
        <nav>
            <ul className="flex flex-row space-x-10">
                <li>
                    <NavigationLink title={"Artworks"} path={"/artworks"}/>
                </li>
                <li>
                    <NavigationLink title={"Exhibitions"} path={"/exhibitions"}/>
                </li>
                <li>
                    <NavigationLink title={"Auctions"} path={"/auctions"}/>
                </li>
            </ul>
        </nav>
    );
};

export default Navigation;
