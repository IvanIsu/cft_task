import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParsingArgsUtil {

    private Map<String, String> paramMap;
    private ArrayList<String> fileList;


    public ParsingArgsUtil(String[] args) {
        paramMap = new HashMap<>();
        fileList = new ArrayList<>();
        parseArgs(args);
    }

    public ArrayList<String> getFiles() {
        return fileList;
    }

    public String getPrefix() {
        String str = "";
        if (paramMap.containsKey("-p")) {
            return paramMap.get("-p");
        }
        return str;
    }

    public String getPathFromParam() {
        String str = "";
        if (paramMap.containsKey("-o")) {
            return paramMap.get("-o");
        }
        return str;
    }

    public boolean getAppEndOption() {
        if (paramMap.containsKey("-a")){
            return true;
        }
        return false;
    }
    public boolean isFullStat() {
        if(paramMap.containsKey("-f")){
            return true;
        }
        return false;
    }

    public boolean isSimpleStat() {
        if(paramMap.containsKey("-s")){
            return true;
        }
        return false;
    }

    private void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-files")) fileList.addAll(Arrays.stream(args[i + 1].split(",")).toList());
            if (args[i].equals("-a")) paramMap.put(args[i], "true");
            if (args[i].equals("-o")) paramMap.put(args[i], args[i + 1]);
            if (args[i].equals("-p")) paramMap.put(args[i], args[i + 1]);
            if (args[i].equals("-s")) paramMap.put(args[i], "true");
            if (args[i].equals("-f")) paramMap.put(args[i], "true");
        }
    }
}
