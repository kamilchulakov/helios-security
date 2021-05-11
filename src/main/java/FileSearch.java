import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class FileSearch {

    private String fileNameToSearch;
    private List<String> result = new ArrayList<>();

    public String getFileNameToSearch() {
        return fileNameToSearch;
    }

    public void setFileNameToSearch(String fileNameToSearch) {
        this.fileNameToSearch = fileNameToSearch;
    }

    public List<String> getResult() {
        return result;
    }

    public static void main(String[] args) throws CheckFailure {

        FileSearch fileSearch = new FileSearch();

        //try different directory and filename :)
        fileSearch.searchDirectory(new File("../"), ".*java");

        int count = fileSearch.getResult().size();
        if (count == 0) {
            System.out.println("\nNo result found!");
        } else {
            System.out.println("\nFound " + count + " result!\n");
            for (String matched : fileSearch.getResult()) {
                System.out.println("Found : " + matched);


                File file = new File(matched);
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                String string = "";

                while (true) {
                    try {
                        if ((string = br.readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (String small: string.split(" ")) {
                        if (small.endsWith(";")) small = small.substring(0, small.length() - 1);
                        if (small.contains("=\"s")) small = small.split("=")[1];
                        if (HeliosLoginCheck.checkIfContainsHeliosLogin(small)) {
                            throw new CheckFailure("It has problems: " + matched);
                        }
                    }
                }
            }
            }
        }

    public void searchDirectory(File directory, String fileNameToSearch) {

        setFileNameToSearch(fileNameToSearch);

        if (directory.isDirectory()) {
            search(directory);
        } else {
            System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }

    }

    private void search(File file) {

        if (file.isDirectory()) {
            System.out.println("Searching directory ... " + file.getAbsoluteFile());

            //do you have permission to read this directory?
            if (file.canRead()) {
                for (File temp : Objects.requireNonNull(file.listFiles())) {
                    if (temp.isDirectory()) {
                        search(temp);
                    } else {
                        if (Pattern.matches(getFileNameToSearch(), temp.getName())) {
                            result.add(temp.getAbsoluteFile().toString());
                        }

                    }
                }

            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }
    }
}
