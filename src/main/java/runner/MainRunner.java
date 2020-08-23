package runner;

import cards.CardArray;
import files.ProjectFileWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainRunner {

    public static void main(String[] args) {

        //   ;
        if (ProjectFileWorker.getInstance().validateCardBase()) {
            SpringApplication.run(MainRunner.class, args);
//            CardArray array = ProjectFileWorker.getInstance().readCards();
//            System.out.println(array.cardArrayToConfigString());
        }
        //TODO реализация запуска приложения
    }
}
