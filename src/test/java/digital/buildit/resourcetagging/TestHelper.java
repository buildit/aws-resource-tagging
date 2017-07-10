package digital.buildit.resourcetagging;

import digital.buildit.resourcetagging.event.GenericEventExtractor;

import java.io.InputStream;
import java.util.Scanner;

public class TestHelper {
    public static String getFileAsString(String resourceLocation) {
        InputStream resourceAsStream = GenericEventExtractor.class.getResourceAsStream(resourceLocation);
        return new Scanner(resourceAsStream, "UTF-8").useDelimiter("\\A").next();
    }
}
