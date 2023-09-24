import java.util.Comparator;

public class PriceComparator implements Comparator<String> {
    @Override
    public int compare(String value1, String value2) {
        // Remove the dollar sign and convert prices to double for comparison
        double doubleValue1 = Double.parseDouble(value1.substring(1));
        double doubleValue2 = Double.parseDouble(value2.substring(1));

        // Compare the double values
        return Double.compare(doubleValue1, doubleValue2);
    }
}
