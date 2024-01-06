import React, { CSSProperties } from "react";

interface MenuItemProps {
    onClick: () => void;
    label: string;
    style?: CSSProperties; // Accepts style as an optional prop
}

const MenuItem: React.FC<MenuItemProps> = ({ onClick, label, style }) => {
    return (
        <div
            onClick={onClick}
            style={style} // Apply the passed style to the div
            className="
                px-4
                py-3
                hover:bg-neutral-100
                transition
                font-semibold
            "
        >
            {label}
        </div>
    );
}

export default MenuItem;
