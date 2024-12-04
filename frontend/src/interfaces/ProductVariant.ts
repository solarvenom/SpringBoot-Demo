
export interface Product {
    uuid: string,
    name: string,
    description: string,
    createdDate: string,
    deletedDate: string | null
}

export interface ProductVariant {
    product: Product,
    uuid: string,
    colour: string,
    size: string,
    price: number,
    stock: number,
    sku:  string,
    ean:  string,
    createdDate: string,
    deletedDate: string | null
}

export interface Order {
    productVariant: ProductVariant,
    uuid: string,
    createdDate: string,
    deletedDate: string | null,
    mapping: string,
}