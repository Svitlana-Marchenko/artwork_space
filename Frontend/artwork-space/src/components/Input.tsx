import {
    FieldErrors,
    FieldValues,
    UseFormRegister, UseFormSetValue
} from "react-hook-form";
import React, {useEffect} from "react";

interface InputProps {
    id: string;
    label: string;
    placeholder: string;
    type?: string;
    required?: boolean;
    register: UseFormRegister<FieldValues>;
    setValue: UseFormSetValue<FieldValues>;
    errors: FieldErrors;
}

const Input: React.FC<InputProps> = ({
                                         id,
                                         label,
                                         type = "text",
                                         placeholder,
                                         setValue,
                                         register,
                                         required,
                                         errors,
                                     }) => {
    useEffect(() => {
        register(id, {
            required,
            validate: (value) => parseFloat(value) > 0,
        });
    }, [id, register, required]);

    return (
        <div className="w-full relative">
            <input
                id={id}
                onChange={(e) => setValue(id, e.target.value)}
                placeholder={placeholder}
                type={type}
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
                      ${errors[id] ? 'border-black' : 'border-gray-200'}
                      ${errors[id] ? 'focus:border-black' : 'focus:border-[#a62c2a]'}
                    `}
            />
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
                      ${errors[id] ? 'text-[#a62c2a]' : null}
                    `}
            >
                {label}
            </label>
        </div>
    );
}

export default Input;
