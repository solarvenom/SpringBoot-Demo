import React, { useRef } from 'react';
import { PopUpProps } from '../interfaces'

const PopUp: React.FC<PopUpProps> = ({ isOpen, title, children, onClose, onSubmit }) => {

    const formRef = useRef<HTMLDivElement | null>(null);
    if (!isOpen) return null;

    const handleSubmit = () => {
        if (formRef.current) {
            const inputs = formRef.current.querySelectorAll('input, select, textarea');
            const data: { [key: string]: any } = {};

            inputs.forEach((input) => {
                const inputElement = input as HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement;
                if (inputElement.name) {
                    data[inputElement.name] = inputElement.value;
                }
            });

            onSubmit(data);
            onClose();
        }
    };

    return (
        <div className="popUp">
            <div className="popUpContent">
                <h3>{title}</h3>
                <div ref={formRef}>
                    {children}
                </div>
                <div className="popUpButtons">
                    <button className="submitButton" onClick={() => handleSubmit()}>
                        Submit
                    </button>
                    <button className="cancelButton" onClick={onClose}>
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    );
};

export default PopUp;