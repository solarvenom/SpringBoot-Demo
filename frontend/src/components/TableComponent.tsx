import { TableProps } from '../types'
import { ApiData } from '../interfaces';

export function TableComponent({ columns, data, deletionHandler }: TableProps) {
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
        {data ? data.map((row, rowIndex) => (
          <tr key={rowIndex}>
            {columns.map((col) => (
              <td key={col.accessor}>
                {String(row[col.accessor as keyof ApiData] ?? "")}
              </td>
            ))}
            <td>
              <button disabled={row["deletedDate" as keyof ApiData] ? true : false} onClick={() => deletionHandler(
                row["endpoint" as keyof ApiData],
                row["uuid" as keyof ApiData]
              )}>
                Delete
              </button>
            </td>
          </tr>
        )) : null}
      </tbody>
    </table>
  );
}
