package Template;

import org.apache.commons.lang3.RandomStringUtils;

public class Generator {
    public static String randomShortText() {
        return randomText(10);
    }

    private static String randomText(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    public static int randomInt(int count) {
        return Integer.parseInt(RandomStringUtils.randomNumeric(count));
    }
}
