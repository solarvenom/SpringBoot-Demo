import { ProductVariant, Product } from "../interfaces"

export const reduceToUniqueProducts = (items: ProductVariant[]): Product[] => {
    return items.reduce<Product[]>((unique, item) => {
      if (!unique.some((u) => u.uuid === item.product.uuid)) {
        unique.push(item.product);
      }
      return unique;
    }, []);
  };


  