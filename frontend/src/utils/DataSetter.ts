import { Dispatch, SetStateAction } from "react";
import { formatDate } from './';

export const createDataSetter = (setter: Dispatch<SetStateAction<any>>) => {
    return (activeTab: number, data: any) => {
        if(activeTab == 1) {
            setter(data.map((variant: any) => {
                return {
                    ...variant, 
                    name: variant.product.name,
                    endpoint: "http://localhost:8080/product-variants"
                }
            }))
        } else if (activeTab == 2) {
            setter(data.map((order: any) => {
                return {
                    ...order,
                    name: order.productVariant.product.name,
                    status: order.deletedDate ? "Canceled" : "Active",
                    createdDate: formatDate(order.createdDate),
                    sku: order.productVariant.sku,
                    endpoint: "http://localhost:8080/orders"
                }
            }))
        } else {
            setter(data.map((product: any) => {
                return {
                    ...product,
                    endpoint: "http://localhost:8080/products"
                }
            }));
        }
    }
}
