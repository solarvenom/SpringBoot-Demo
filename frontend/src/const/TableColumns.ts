import { TableColumn } from '../types'

export const ProductColumns: TableColumn[] = [
    { header: "Product Name", accessor: "name" },
    { header: "Description", accessor: "description"}
];

export const ProductVariantColumns: TableColumn[] = [
    { header: "Product Name", accessor: "name" },
    { header: "Colour", accessor: "colour"},
    { header: "Size", accessor: "size"},
    { header: "Price", accessor: "price"},
    { header: "Stock", accessor: "stock"},
    { header: "SKU", accessor: "sku"},
    { header: "EAN", accessor: "ean"}
];

export const OrdersColumns: TableColumn[] = [
    { header: "Product Name", accessor: "name" },
    { header: "Mapping", accessor: "mapping" },
    { header: "SKU", accessor: "sku"},
    { header: "Created Date", accessor: "createdDate"},
    { header: "Status", accessor: "status"}
];