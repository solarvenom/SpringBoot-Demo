import { TableProps } from '../types'
import { ApiData } from '../interfaces';

export function TableComponent({ columns, data }: TableProps) {
  return (
    <table style={{ borderCollapse: "collapse", width: "100%" }}>
      <thead>
        <tr>
          {columns.map((col) => (
            <th
              key={col.accessor}
              style={{
                border: "1px solid #ddd",
                padding: "8px",
                textAlign: "center",
              }}
            >
              {col.header}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data ? data.map((row, rowIndex) => (
          <tr key={rowIndex}>
            {columns.map((col) => (
              <td
                key={col.accessor}
                style={{
                  border: "1px solid #ddd",
                  padding: "8px",
                }}
              >
                {String(row[col.accessor as keyof ApiData] ?? "")}
              </td>
            ))}
          </tr>
        )) : null}
      </tbody>
    </table>
  );
}
