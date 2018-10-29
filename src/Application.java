import controller.InterpreterController;
import repository.Repository;
import repository.RepositoryImpl;
import view.View;

public class Application {

    public static void main(String... args) {
        Repository repository = new RepositoryImpl();
        InterpreterController controller = new InterpreterController(repository);
        controller.setDisplayOn(true);
        View view = new View(controller);
        view.start();
    }

}
