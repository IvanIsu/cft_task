import java.util.ArrayList;

public class Statistics {


    private StringBuilder stringBuilder = new StringBuilder();

    public void getSimpleStat(ArrayList<Long> longsList, ArrayList<Double> doublesList, ArrayList<String> stringsList) {
        stringBuilder.append(" Integer : " + longsList.size() + " pcs");
        stringBuilder.append(" Float : " + doublesList.size() + " pcs");
        stringBuilder.append(" String : " + stringsList.size() + " pcs");
        print();
    }

    public void getFullStat(ArrayList<Long> longsList, ArrayList<Double> doublesList, ArrayList<String> stringsList) {
        if (longsList.size() > 0) {
            minMaxInteger(longsList);
            averageAndSumInteger(longsList);
        }
        if (doublesList.size() > 0) {
            minMaxFloat(doublesList);
            averageAndSumFloat(doublesList);
        }
        if (stringsList.size() > 0) {
            stringInfo(stringsList);
        }
        getSimpleStat(longsList, doublesList, stringsList);
        print();
    }

    private void minMaxInteger(ArrayList<Long> longsList) {
        long min = longsList.get(0);
        long max = longsList.get(0);
        for (Long l : longsList) {
            min = Math.min(l, min);
            max = Math.max(l, max);
        }
        stringBuilder.append(String.format(" MIN Integer: %d || MAX Integer %d \n", min, max));

    }

    private void averageAndSumInteger(ArrayList<Long> longsList) {

        Long sum = 0L;
        double average;
        int size = longsList.size();
        for (Long l : longsList) {
            sum += l;
        }
        average = (sum * 1.0d) / size;
        stringBuilder.append(String.format(" SUM Integer: %d || AVERAGE Integer %.4f \n", sum, average));
    }

    private void stringInfo(ArrayList<String> stringsList) {
        int min = stringsList.get(0).length();
        int max = stringsList.get(0).length();
        for (String s : stringsList) {
            min = Math.min(s.length(), min);
            max = Math.max(s.length(), max);
        }
        stringBuilder.append(String.format(" Shortest string: %d chars || Longest string %d chars \n", min, max));
    }

    private void averageAndSumFloat(ArrayList<Double> doublesList) {
        Double sum = 0d;
        Double average;
        for (Double l : doublesList) {
            sum += l;
        }
        average = Double.valueOf(sum / doublesList.size());
        stringBuilder.append(String.format(" SUM Float: %.4f || AVERAGE Float %.4f \n", sum, average));
    }

    private void minMaxFloat(ArrayList<Double> doublesList) {
        Double min = doublesList.get(0);
        Double max = doublesList.get(0);
        for (Double d : doublesList) {
            min = Math.min(d, min);
            max = Math.max(d, max);
        }
        stringBuilder.append(String.format(" MIN Float: %f || MAX Float %f \n", min, max));
    }

    private void print() {
        System.out.println(stringBuilder);
        stringBuilder = new StringBuilder();
    }
}
