import { ApiData } from './interfaces';

export type TableColumn = {
    header: string;
    accessor: string;
  };
  
export type TableProps = {
    columns: TableColumn[];
    data: ApiData[] | null;
    deletionHandler: any;
  };