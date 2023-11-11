import React from 'react';
import Logo from "./Logo";
import Navigation from "./Navigation";
import UserMenu from "./UserMenu";

const Header = () => {
    return (
        <header className="mx-14 my-4 flex flex-row justify-between">
            <Logo name={"ExhibitPro"}/>
            <div className="flex flex-row justify-between w-1/3 items-center">
                <Navigation/>
                <UserMenu/>
            </div>
        </header>
    );
};

export default Header;
