package nl.rienstra.adventofcode.day2;

public class ProductIdValidator {

    public static boolean isValidProductId(String productId) {
        int length = productId.length();
        for (int i = 0; i < length / 2; i++) {
            String part = productId.substring(0, i + 1);
            if (productId.replaceAll(part, "").isBlank()) {
                return false;
            }
        }

        return true;
    }
}
