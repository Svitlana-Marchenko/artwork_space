import {
    FieldErrors,
    FieldValues,
    UseFormRegister
} from "react-hook-form";
import React from "react";

export interface InputProps {
    id: string;
    label: string;
    placeholder: string;
    type?: string;
    required?: boolean;
    register: UseFormRegister<FieldValues>;
    errors: FieldErrors;
    isTextArea?: boolean;
    minValue?: number;
}

const Input: React.FC<InputProps> = ({
                                         id,
                                         label,
                                         type = "text",
                                         placeholder,
                                         register,
                                         required,
                                         errors,
                                         isTextArea=false
                                     }) => {

    const inputClasses = `
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
                    ${isTextArea ? 'h-48' : null}
                  `;
    return (
        <div className="w-full relative">
            {isTextArea ? (
                <textarea
                    id={id}
                    {...register(id, { required })}
                    placeholder={placeholder}
                    className={inputClasses}
                    rows={4}
                />
            ) : (
                <input
                    id={id}
                    {...register(id, { required })}
                    placeholder={placeholder}
                    type={type}
                    className={inputClasses}
                />
            )}
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
