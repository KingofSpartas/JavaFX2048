import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("FX2048");
		stage.setScene(new Game().buildScene());
		stage.setResizable(false);
		stage.show();
	}
}