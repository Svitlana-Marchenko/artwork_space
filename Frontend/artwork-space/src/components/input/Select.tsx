import React, { useEffect } from "react";
import {InputProps} from "./Input";

interface SelectProps extends InputProps {
    options: string[];
}

const Select: React.FC<SelectProps> = ({
                                           id,
                                           label,
                                           placeholder,
                                           setValue,
                                           register,
                                           required,
                                           errors,
                                           options,
                                       }) => {
    useEffect(() => {
        register(id, {
            required,
        });
    }, [id, register, required]);

    return (
        <div className="w-full relative">
            <select
                id={id}
                onChange={(e) => setValue(id, e.target.value)}
                className={`
                  peer
                  w-full
                  p-2
                  font-light 
                  bg-white 
                  border-2
                  rounded-md
                  outline-none
                  transition
                  ${errors[id] ? "border-black" : "border-gray-200"}
                  ${errors[id] ? "focus:border-black" : "focus:border-[#a62c2a]"}
                `}
            >
                <option value="" disabled>
                    {placeholder}
                </option>
                {options.map((option) => (
                    <option key={option} value={option}>
                        {option}
                    </option>
                ))}
            </select>
            <label
                className={`
          absolute
          text-transparent
          duration-300 
          bg-transparent
          p-1
          transform 
          rounded-md
          -translate-y-3 
          top-0
          left-2
          z-10 
          origin-[0] 
          peer-placeholder-shown:scale-100 
          peer-placeholder-shown:translate-y-0 
          peer-focus:scale-75
          peer-focus:text-[#a62c2a]
          peer-focus:bg-white
          peer-focus:-translate-y-4
          ${errors[id] ? "text-[#a62c2a]" : null}
        `}
            >
                {label}
            </label>
        </div>
    );
};

export default Select;
