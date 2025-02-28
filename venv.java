import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VirtualLinuxEnvironment {
    private Map<String, String> fileSystem;
    private String currentDirectory;

    public VirtualLinuxEnvironment() {
        this.fileSystem = new HashMap<>();
        this.currentDirectory = "/";
        this.fileSystem.put("/", "root directory");
        this.fileSystem.put("/home", "home directory");
        this.fileSystem.put("/home/user", "user directory");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(currentDirectory + "$ ");
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                break;
            }
            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "cd":
                    cd(parts);
                    break;
                case "ls":
                    ls();
                    break;
                case "mkdir":
                    mkdir(parts);
                    break;
                case "rm":
                    rm(parts);
                    break;
                case "touch":
                    touch(parts);
                    break;
                case "cat":
                    cat(parts);
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
        scanner.close();
    }

    private void cd(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Please specify a directory");
            return;
        }
        String directory = parts[1];
        if (directory.equals("..")) {
            if (currentDirectory.equals("/")) {
                System.out.println("Already in root directory");
                return;
            }
            currentDirectory = currentDirectory.substring(0, currentDirectory.lastIndexOf("/"));
            if (currentDirectory.isEmpty()) {
                currentDirectory = "/";
            }
        } else if (directory.equals(".")) {
            System.out.println("Already in current directory");
            return;
        } else {
            if (fileSystem.containsKey(currentDirectory + "/" + directory)) {
                currentDirectory += "/" + directory;
            } else {
                System.out.println("Directory not found");
            }
        }
    }

    private void ls() {
        System.out.println("Files and directories in " + currentDirectory + ":");
        for (String key : fileSystem.keySet()) {
            if (key.startsWith(currentDirectory + "/")) {
                System.out.println(key.substring(currentDirectory.length() + 1));
            }
        }
    }

    private void mkdir(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Please specify a directory name");
            return;
        }
        String directoryName = parts[1];
        if (fileSystem.containsKey(currentDirectory + "/" + directoryName)) {
            System.out.println("Directory already exists");
            return;
        }
        fileSystem.put(currentDirectory + "/" + directoryName, "new directory");
        System.out.println("Directory created");
    }

    private void rm(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Please specify a file or directory name");
            return;
        }
        String fileName = parts[1];
        if (fileSystem.containsKey(currentDirectory + "/" + fileName)) {
            fileSystem.remove(currentDirectory + "/" + fileName);
            System.out.println("File or directory removed");
        } else {
            System.out.println("File or directory not found");
        }
    }

    private void touch(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Please specify a file name");
            return;
        }
        String fileName = parts[1];
        if (fileSystem.containsKey(currentDirectory + "/" + fileName)) {
            System.out.println("File already exists");
            return;
        }
        fileSystem.put(currentDirectory + "/" + fileName, "new file");
        System.out.println("File created");
    }

    private void cat(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Please specify a file name");
            return;
        }
        String fileName = parts[1];
        if (fileSystem.containsKey(currentDirectory + "/" + fileName)) {
            System.out.println(fileSystem.get(currentDirectory + "/" + fileName));
        } else {
            System.out.println("File not found");
        }
    }

    public static void main(String[] args) {
        VirtualLinuxEnvironment environment = new VirtualLinuxEnvironment();
        environment.run();
    }
}