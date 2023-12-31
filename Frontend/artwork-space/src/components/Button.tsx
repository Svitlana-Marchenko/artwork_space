import React from "react";
import { IconType } from "react-icons";

interface ButtonProps {
    label: string;
    onClick: (e: React.MouseEvent<HTMLButtonElement>) => void;
    disabled?: boolean;
    outline?: boolean;
    small?: boolean;
    icon?: IconType;
    style?: React.CSSProperties;
}

export const Button: React.FC<ButtonProps> = ({
                                                  label,
                                                  onClick,
                                                  disabled,
                                                  outline,
                                                  small,
                                                  icon: Icon,
                                              }) => {
    return (
        <button
            onClick={onClick}
            disabled={disabled}
            className={`
        relative
        disabled:opacity-70
        disabled:cursor-not-allowed
        rounded-lg
        transition
        w-full
        ${outline ? "bg-white" : "bg-[#a62c2a]"}
        ${outline ? "border-black" : "border-[#a62c2a]"}
        ${outline ? "text-black" : "text-white"}
        ${outline ? "hover:bg-[#a62c2a]" : "hover:opacity-80"}
        ${outline ? "hover:border-transparent" : null}
        ${outline ? "hover:text-white" : null}
        ${small ? "text-sm" : "text-md"} 
        ${small ? "py-1" : "py-3"} 
        ${small ? "font-light" : "font-semibold"} 
        ${small ? "border-[1px]" : "border-2"}
      `}
        >
            {Icon && (
                <Icon
                    size={24}
                    className="
            absolute
            left-4
            top-3
          "
                />
            )}
            {label}

        </button>
    );
};
