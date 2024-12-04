import { ApiData } from '../interfaces';

export type TableColumn = {
    header: string;
    accessor: string;
  };
  
export type TableProps = {
    columns: TableColumn[];
    data: ApiData[] | null;
    deletionHandler: any;
    updateHandler: (dataToSubmit: any, uuid?: any) => Promise<void>;
    popUpFields: UpdateField[]
  };

export type UpdateField = {
  name: string;
  label: string;
  placeholder: string;
}