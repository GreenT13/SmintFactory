import com.airhacks.afterburner.injection.Injector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import screen.ScreenView;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	// Dit is een  verandering.
    	// Create a new window which loads 'screen.fxml' into it.
    	ScreenView appView = new ScreenView();
        Scene scene = new Scene(appView.getView());
        stage.setTitle("Testing badass shit");
        
        // Add global css. Not sure when we will actually use this.
        final String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
