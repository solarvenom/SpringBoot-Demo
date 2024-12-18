import { Dispatch, SetStateAction } from "react";
import { formatDate } from "./";
import { URL } from "../const"

export const createDataSetter = (setter: Dispatch<SetStateAction<any>>) => {
    return (activeTab: number, data: any) => {
        if(activeTab == 1) {
            setter(data ? data.map((variant: any) => {
                return {
                    ...variant, 
                    name: variant.product.name,
                    endpoint: URL.PRODUCT_VARIANTS
                }
            }): [])
        } else if (activeTab == 2) {
            setter(data ? data.map((order: any) => {
                return {
                    ...order,
                    name: order.productVariant.product.name,
                    status: order.deletedDate ? "Canceled" : "Active",
                    createdDate: formatDate(order.createdDate),
                    sku: order.productVariant.sku,
                    endpoint: URL.ORDERS
                }
            }): [])
        } else {
            setter(data ? data.map((product: any) => {
                return {
                    ...product,
                    endpoint: URL.PRODUCTS
                }
            }): []);
        }
    }
}
