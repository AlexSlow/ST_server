
import Handlers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception  {
        stage=primaryStage;
        stage.setTitle("Сервер");
        Parent parent = FXMLLoader.load(getClass().getResource("res/main_page.fxml"));
        Scene scene=new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void  stop()
    {
        if (Controller.current_controller==null) return;
        Controller.current_controller.stop_server();
    }


    public static void main(String[] args) {
        Application.launch(args);

    }

}

