package demo.ecommerce.api.helpers;

import java.util.Random;
import java.util.UUID;

import demo.ecommerce.product.entities.ProductEntity;
import demo.ecommerce.product.enums.ColourEnum;
import demo.ecommerce.product.enums.SizeEnum;

public class Helpers {

    public static String generateEan(){        
        Random random = new Random();
        long number = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return "73" + String.valueOf(number) + "K";
    }

    public static String generateSku(
        ProductEntity product,
        SizeEnum size,
        ColourEnum colour
    ) {    
        UUID productUuid = product.getUuid();
        String[] identifier = productUuid.toString().split("-");
        return identifier[4] + "-" + colour.toString() + "-" + size.toString();
    }

    Helpers(){}
}
