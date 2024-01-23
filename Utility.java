import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalLong;

public class Utility {

    private ParsingArgsUtil parsingUtil;
    private Statistics statistics;
    private final File ROOT;

    private ArrayList<String> stringsList;
    private ArrayList<Double> doublesList;
    private ArrayList<Long> longsList;


    public Utility(String[] args) {
        ROOT = new File(new File("").getAbsolutePath());
        parsingUtil = new ParsingArgsUtil(args);
        stringsList = new ArrayList<>();
        doublesList = new ArrayList<>();
        longsList = new ArrayList<>();
        statistics = new Statistics();
    }

    public void run() {
        filesRead(parsingUtil.getFiles());
        if (parsingUtil.isFullStat()) {
            statistics.getFullStat(longsList, doublesList, stringsList);
        } else if (parsingUtil.isSimpleStat()) {
            statistics.getSimpleStat(longsList, doublesList, stringsList);
        }
        try {
            preparationsToWritesFiles();
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + e.getMessage());
        }
    }

    private void filesRead(ArrayList<String> fileList) {
        Path currentPath;
        for (String p : fileList) {
            currentPath = Paths.get(p);
            List<String> stringList;
            try {
                stringList = Files.readAllLines(currentPath.toAbsolutePath(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.out.println(" This file is not found: " + currentPath.getFileName());
                continue;
            }
            for (String item : stringList) {
                if (castToLong(item) != OptionalLong.empty()) {
                    longsList.add(castToLong(item).getAsLong());
                } else if (castToDouble(item) != OptionalDouble.empty()) {
                    doublesList.add(castToDouble(item).getAsDouble());
                } else {
                    stringsList.add(item);
                }
            }
        }

    }

    private void preparationsToWritesFiles() throws IOException {

        List<String> list;
        list = doublesList.stream().map(Object::toString).toList();
        writeFiles(list, "floats");
        list = longsList.stream().map(Object::toString).toList();
        writeFiles(list, "integers");
        writeFiles(stringsList, "strings");


    }

    private void writeFiles(List<String> dataList, String fileName) throws IOException {

        if (dataList.size() == 0) {
            return;
        }


        StandardOpenOption standardOpenOption;
        Path pathFromParam = Paths.get(parsingUtil.getPathFromParam());
        Path pathToWrite;
        if (pathFromParam.isAbsolute()) {
            Files.createDirectories(Path.of(pathFromParam.toString()));
            pathToWrite = Paths.get(pathFromParam.toString(), parsingUtil.getPrefix() + fileName + ".txt");

        } else {
            Files.createDirectories(Path.of(ROOT.getPath(), pathFromParam.toString()));
            pathToWrite = Paths.get(ROOT.getPath(), pathFromParam.toString(), parsingUtil.getPrefix() + fileName + ".txt");
        }

        if (parsingUtil.getAppEndOption()) {
            standardOpenOption = StandardOpenOption.APPEND;
        } else {
            if (Files.exists(pathToWrite)) Files.delete(pathToWrite);
            standardOpenOption = StandardOpenOption.WRITE;
        }

        if (!Files.exists(pathToWrite)) {
            Files.createFile(pathToWrite);
        }
        Files.write(pathToWrite, dataList, StandardCharsets.UTF_8, standardOpenOption);
    }

    private OptionalLong castToLong(String str) {
        try {
            Long num = Long.parseLong(str);
            return OptionalLong.of(num);
        } catch (NumberFormatException e) {

        }
        return OptionalLong.empty();
    }

    public OptionalDouble castToDouble(String str) {
        try {
            Double num = Double.parseDouble(str);
            return OptionalDouble.of(num);
        } catch (NumberFormatException e) {

        }
        return OptionalDouble.empty();
    }
}
