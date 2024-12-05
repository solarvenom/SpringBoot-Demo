import { ProductVariant, Product, Order } from "../interfaces"

export const reduceToUniqueProducts = (items: Product[]): Product[] => {
    return items.reduce<Product[]>((unique, item) => {
      if (!unique.some((u) => u.uuid === item.uuid)) {
        unique.push(item);
      }
      return unique;
    }, []);
  };

export const reduceToUniqueProductVariants = (items: ProductVariant[]): ProductVariant[] => {
  return items.reduce<ProductVariant[]>((unique, item) => {
    if (!unique.some((u) => u.sku === item.sku)) {
      unique.push(item);
    }
    return unique;
  }, []);
};
  