package runner;

import files.ProjectFileWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainRunner {

    public static void main(String[] args) {
        if (ProjectFileWorker.getInstance().validateCardBase()) {
            SpringApplication.run(MainRunner.class, args);
        }
    }
}
