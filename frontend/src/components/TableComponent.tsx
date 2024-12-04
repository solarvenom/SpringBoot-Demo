import { useState } from 'react';
import { TableProps } from '../types'
import { ApiData } from '../interfaces';
import PopUp from './PopUp';

export function TableComponent({ columns, data, deletionHandler, updateHandler, popUpFields }: TableProps) {
  const [isPopupOpen, setIsPopupOpen] = useState<boolean>(false);
  const [entity, setEntity] = useState<any>({});
  const togglePopup = () => setIsPopupOpen(!isPopupOpen);

  return (
    <table>
      <thead>
        <tr>
          {columns.map((col) => (
            <th
              key={col.accessor}
            >
              {col.header}
            </th>
          ))}
          <th></th>
        </tr>
      </thead>
      <tbody>
        <PopUp 
          isOpen={isPopupOpen} 
          title="Update form"
          onClose={togglePopup}
          onSubmit={(data:any) => updateHandler(data, entity.uuid)}
        >
          {popUpFields.map((field) => (
            <div>
              <label>{field.label}:</label>
              <input
                type="text"
                name={field.name}
                placeholder={field.placeholder}
                defaultValue={`${entity[field.name]}`}
              />
            </div>
          ))}
        </PopUp>
        {data ? data.map((row, rowIndex) => (
          <tr key={rowIndex}>
            {columns.map((col) => (
              <td key={col.accessor}>
                {String(row[col.accessor as keyof ApiData] ?? "")}
              </td>
            ))}
            <td>
              <button 
                className="tableUpdateButton"
                onClick={() => {
                  setEntity(row);
                  togglePopup()
                }}
              >
                Update
              </button>
              <button
                className="tableDeleteButton"
                disabled={row["deletedDate" as keyof ApiData] ? true : false} 
                onClick={() => deletionHandler(
                  row["endpoint" as keyof ApiData],
                  row["uuid" as keyof ApiData]
                )}
              >
                Delete
              </button>
            </td>
          </tr>
        )) : null}
      </tbody>
    </table>
  );
}
