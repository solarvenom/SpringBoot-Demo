export const URL: { [key: string]: string; } = {
    SEARCH_PRODUCTS: `http://localhost:8080/products?searchTerm=`,
    SEARCH_PRODUCT_VARIANTS: "http://localhost:8080/product-variants?searchTerm=",
    SEARCH_ORDERS: "http://localhost:8080/orders?searchTerm=",
    PRODUCTS: "http://localhost:8080/products",
    PRODUCT_VARIANTS: "http://localhost:8080/product-variants",
    ORDERS: "http://localhost:8080/orders"
}

// const API_URL: string = `http://${process.env.API_HOST}:${process.env.API_PORT}`;

// export const URL: { [key: string]: string; } = {
//     SEARCH_PRODUCTS: `${API_URL}${process.env.SEARCH_PRODUCTS_ENDPOINT}`,
//     SEARCH_PRODUCT_VARIANTS: `${API_URL}${process.env.SEARCH_PRODUCT_VARIANTS_ENDPOINT}`,
//     SEARCH_ORDERS: `${API_URL}${process.env.SEARCH_ORDERS_ENDPOINT}`,
//     PRODUCTS: `${API_URL}${process.env.PRODUCTS_ENDPOINT}`,
//     PRODUCT_VARIANTS: `${API_URL}${process.env.PRODUCT_VARIANTS_ENDPOINT}`,
//     ORDERS: `${API_URL}${process.env.ORDERS_ENDPOINT}`
// }