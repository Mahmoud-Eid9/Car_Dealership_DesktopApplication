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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
			HBox root = new HBox(0);
			root.setStyle("-fx-background-color: white");
			root.setMaxSize(1100, 750);
			root.setMinSize(1100, 750);
			root.setAlignment(Pos.CENTER_LEFT);
			VBox vbox = new VBox(10);
			vbox.setPadding(new Insets(90, 50, 5, 20));
			Scene scene = new Scene(root,1200,600,Color.WHITE);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
	
			
			//TextFields
			VBox v1 = new VBox(10);
			VBox v2 = new VBox(10);
			TextField usernameField = new TextField();
			usernameField.setFont(Font.font(20));
			Label usernameLabel = new Label("Username");
			usernameField.setMinSize(300, 40);
			usernameLabel.setPadding(new Insets(3, 0, 0, 0));
			usernameLabel.setFont(Font.font( 25) );
			TextField passwordField = new TextField();
			passwordField.setFont(Font.font(20));
			Label passwordLabel = new Label("Password ");
			passwordLabel.setFont(Font.font( 25) );
			passwordLabel.setPadding(new Insets(5, 0, 0, 0));
			passwordField.setMinSize(300, 40);
			v1.getChildren().addAll(usernameLabel, usernameField);
			v2.getChildren().addAll(passwordLabel, passwordField);
			
			
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
			
			Image im = new Image("carImage.jpeg");
			ImageView image = new ImageView(im);
			image.setFitHeight(650);
			image.setFitWidth(850);
			vbox.getChildren().addAll(v1, v2, buttonsBox);
			root.getChildren().addAll(vbox,image);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
