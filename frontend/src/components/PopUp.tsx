import React from 'react';
import { PopUpProps } from '../interfaces'

const PopUp: React.FC<PopUpProps> = ({ isOpen, title, children, onClose }) => {
  if (!isOpen) return null;

    return (
        <div className="popUp">
            <div className='popUpContent'>
                <h3>{title}</h3>
                {children}
                <button onClick={onClose}>
                    Close
                </button>
            </div>
        </div>
    );
};

export default PopUp;