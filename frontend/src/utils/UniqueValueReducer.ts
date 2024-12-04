import { ProductVariant, Product, Order } from "../interfaces"

export const reduceToUniqueProducts = (items: ProductVariant[]): Product[] => {
    return items.reduce<Product[]>((unique, item) => {
      if (!unique.some((u) => u.uuid === item.product.uuid)) {
        unique.push(item.product);
      }
      return unique;
    }, []);
  };

export const reductToUniqueProductVariants = (items: Order[]): ProductVariant[] => {
  return items.reduce<ProductVariant[]>((unique, item) => {
    if (!unique.some((u) => u.sku === item.productVariant.sku)) {
      unique.push(item.productVariant);
    }
    return unique;
  }, []);
};
  