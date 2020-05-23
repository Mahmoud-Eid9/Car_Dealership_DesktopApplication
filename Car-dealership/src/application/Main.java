package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HBox root = new HBox(0);
			root.setStyle("-fx-background-color: white");
			root.setMaxSize(1100, 750);
			root.setMinSize(1100, 750);
			root.setAlignment(Pos.CENTER_LEFT);
			VBox vbox = new VBox(20);
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(150, 15, 5, 20));
	
			
			//TextFields
			HBox h1 = new HBox(20);
			HBox h2 = new HBox(20);
			TextField usernameField = new TextField();
			Label usernameLabel = new Label("Username");
			usernameField.setMinSize(300, 40);
			usernameLabel.setPadding(new Insets(3, 0, 0, 0));
			usernameLabel.setFont(Font.font( 25) );
			TextField passwordField = new TextField();
			Label passwordLabel = new Label("Password ");
			passwordLabel.setFont(Font.font( 25) );
			passwordLabel.setPadding(new Insets(5, 0, 0, 0));
			passwordField.setMinSize(300, 40);
			h1.getChildren().addAll(usernameLabel, usernameField);
			h2.getChildren().addAll(passwordLabel, passwordField);
			
			
			//Buttons
			Button logIn = new Button("Log-In");
			logIn.setMinSize(150, 60);
			logIn.setFont(Font.font( 25) );
			Button signUp = new Button("Sign-Up");
			signUp.setMinSize(200, 60);
			signUp.setFont(Font.font( 25) );
			VBox buttonsBox = new VBox(logIn, signUp);
			buttonsBox.setSpacing(40);
			buttonsBox.setPadding(new Insets(20, 0, 0, 0));
			buttonsBox.setAlignment(Pos.BASELINE_CENTER);
			
			
			//Image
			
			Image image = new Image("carImage.jpg");
			ImageView imageView = new ImageView(image);
			vbox.getChildren().addAll(h1, h2, buttonsBox);
			root.getChildren().addAll(vbox, imageView);
			Scene scene = new Scene(root,1100,650,Color.WHITE);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
