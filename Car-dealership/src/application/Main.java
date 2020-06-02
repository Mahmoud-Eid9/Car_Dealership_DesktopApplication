package application;
	
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import javax.imageio.ImageIO;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;



public class Main extends Application {
	
private	Image signUpImage ;
private	File file ;
private	Customer customer;
private	Image profile ;
private ArrayList<Car> cars = new ArrayList<Car>() ;

	boolean v = true ;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
        	Statement stmt = con.createStatement();
        	ResultSet r = stmt.executeQuery("Select * From CAR");
        	while(r.next()) {

            	InputStream is = r.getBinaryStream("image");
            	OutputStream os = new FileOutputStream(new File("profile.png"));
            	byte[] content = new byte[1024];
            	int size = 0;
            	while((size = is.read(content))!= -1) {
            		os.write(content, 0, size);
            	}
            	os.close();
            	is.close();
            	Image image  = new Image("file:profile.png");
            	image.isPreserveRatio();
            	if(r.getInt("BATTERYCAPACITY") != 0) {
            		ElectricMotors car = new ElectricMotors(r.getInt(1),r.getString(2), r.getString(3), r.getDouble(4), r.getInt(5), r.getInt(6), r.getInt(7),r.getInt(8), r.getString(9), r.getInt(10), r.getString(11),r.getInt(14), r.getInt(15),r.getInt(16),image );
            		this.cars.add(car);
            	}else {
        		Car car = new Car(r.getInt(1),r.getString(2), r.getString(3), r.getDouble(4), r.getInt(5), r.getInt(6), r.getInt(7),r.getInt(8), r.getString(9), r.getInt(10), r.getString(11),image );
        		this.cars.add(car);
            	}
        	}
        	con.close();
        	
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
			
			primaryStage.setScene(logInScreen(primaryStage));
			primaryStage.setResizable(false);
			primaryStage.setTitle("Car dealership");
			primaryStage.show();
	}
	
	public Scene logInScreen(Stage primaryStage) {
		
		HBox root = new HBox(0);
		root.setStyle("-fx-background-color: white");
		root.setMaxSize(1100, 750);
		root.setMinSize(1100, 750);
		root.setAlignment(Pos.CENTER_LEFT);
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(125, 50, 5, 20));
		Scene scene = new Scene(root,1200,600,Color.WHITE);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
		
		
		//TextFields
		VBox v1 = new VBox(0);
		VBox v2 = new VBox(0);
		Label warning = new Label("Invalid Username and/or Password");
		warning.setVisible(false);
		warning.setId("warning");
		TextField usernameField = new TextField();
		usernameField.setMinHeight(25);
		usernameField.setMaxHeight(25);
		Label usernameLabel = new Label("Username");
		usernameField.setMinSize(300, 40);
		usernameLabel.setPadding(new Insets(3, 0, 0, 0));
		usernameLabel.setFont(Font.font( 20) );
		PasswordField passwordField = new PasswordField();
		passwordField.setMinHeight(25);
		passwordField.setMaxHeight(25);
		Label passwordLabel = new Label("Password ");
		passwordLabel.setFont(Font.font( 20) );
		passwordLabel.setPadding(new Insets(5, 0, 0, 0));
		passwordField.setMinSize(300, 40);
		v1.getChildren().addAll(usernameLabel, usernameField);
		v2.getChildren().addAll(passwordLabel, passwordField, warning);
		usernameField.setOnMouseClicked(e ->{
			warning.setVisible(false);
		});
		usernameField.setOnKeyPressed(e -> {
			warning.setVisible(false);
			
		});
		//Buttons
		Button logIn = new Button("Log-In");
		logIn.setMinSize(150, 60);
		logIn.setFont(Font.font( 25) );
		logIn.setOnAction(e -> {
			try {
			this.customer = (Customer)checkAccount(usernameField.getText(), passwordField.getText());
			if(this.v) {
				warning.setVisible(true);
				usernameField.setText(null);
				passwordField.setText(null);
			}else {
				FadeTransition trans = new FadeTransition(Duration.millis(200));
				trans.setNode(root);
				trans.setFromValue(1);
				trans.setToValue(0);
				trans.play();
				trans.setOnFinished((ActionEvent event) -> {
				primaryStage.setScene(homeScreen(primaryStage));
			});
			}
			}catch(Exception f) {
				Employee employee = (Employee)checkAccount(usernameField.getText(), passwordField.getText());
			
				if(this.v) {
					warning.setVisible(true);
					usernameField.setText(null);
					passwordField.setText(null);
				}else {
					FadeTransition trans = new FadeTransition(Duration.millis(200));
					trans.setNode(root);
					trans.setFromValue(1);
					trans.setToValue(0);
					trans.play();
					trans.setOnFinished((ActionEvent event) -> {
					primaryStage.setScene(homeScreen(primaryStage, employee));
				});
				

			}
			}});
		Button signUp = new Button("Sign-Up");
		signUp.setMinSize(200, 60);
		signUp.setFont(Font.font( 25) );
		signUp.setOnAction(e -> {
			FadeTransition trans = new FadeTransition(Duration.millis(200));
			trans.setNode(root);
			trans.setFromValue(1);
			trans.setToValue(0);
			trans.play();
			trans.setOnFinished((ActionEvent event) -> {
				primaryStage.setScene(signUpScreen(primaryStage));
			});
		});
		VBox buttonsBox = new VBox(logIn, signUp);
		buttonsBox.setSpacing(40);
		buttonsBox.setPadding(new Insets(5, 0, 0, 0));
		buttonsBox.setAlignment(Pos.BASELINE_CENTER);
		
		
		//Image
		
		Image im = new Image("carImage.jpeg");
		ImageView image = new ImageView(im);
		image.setFitHeight(650);
		image.setFitWidth(850);
		vbox.getChildren().addAll(v1, v2, buttonsBox);
		root.getChildren().addAll(vbox,image);
		
		return scene ;
	}

	public Scene homeScreen(Stage primaryStage) {
		

		BorderPane root = new BorderPane();
		FadeTransition trans = new FadeTransition(Duration.millis(200));
		trans.setNode(root);
		trans.setFromValue(0);
		trans.setToValue(1);
		trans.play();
		
		root.setStyle("-fx-background-color : white");
		Scene scene = new Scene(root,1200,800,Color.WHITE);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
		
		//SideBar
		VBox sideBar = new VBox(50);
		Circle profile = new Circle(80);
		Image im = new Image("profile_icon.png");
		profile.setFill(new ImagePattern(this.profile));
		Image im2 = new Image("LogOut_icon.png");
		ImageView image = new ImageView(im2);
		HBox logOut = new HBox(image);
		logOut.setOnMouseClicked(e -> {
			primaryStage.setScene(logInScreen(primaryStage));
		});
		logOut.setAlignment(Pos.CENTER);
		sideBar.getChildren().addAll(profile, logOut);
		sideBar.setStyle("-fx-background-color: black");
		sideBar.setPadding(new Insets(15, 15, 15, 15));
		root.setLeft(sideBar);
		
		//cars list
		root.setCenter(displayCars(primaryStage));		
		return scene;
	}
	
	public Scene homeScreen(Stage primaryStage, Employee employee) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,1515,800,Color.WHITE);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
		//SideBar
		VBox sideBar = new VBox(50);
		Circle profile = new Circle(80);
		Image im = new Image("profile_icon.png");
		profile.setFill(new ImagePattern(this.profile));
		Image im2 = new Image("LogOut_icon.png");
		ImageView image = new ImageView(im2);
		Image im3 = new Image("Car_icon.png");
		ImageView image2 = new ImageView(im3);
		image2.setFitHeight(120);
		image2.setFitWidth(120);
		HBox carIcon = new HBox(image2);
		HBox logOut = new HBox(image);
		logOut.setOnMouseClicked(e -> {
			primaryStage.setScene(logInScreen(primaryStage));
		});
		logOut.setAlignment(Pos.CENTER);
		carIcon.setAlignment(Pos.CENTER);
		sideBar.getChildren().addAll(profile, carIcon, logOut);
		sideBar.setStyle("-fx-background-color: black");
		sideBar.setPadding(new Insets(15, 15, 15, 15));
		root.setLeft(sideBar);
		root.setCenter(customersDataBase());
		carIcon.setOnMouseClicked(e -> {
			
			if(image2.getImage().equals(im3)) {
				root.setCenter(carsDataBase());
				image2.setImage(new Image("profile_icon.png"));
			}else {
				root.setCenter(customersDataBase());
				image2.setImage(im3);
			}
			
		});
		
		return scene ;
	}
	
	public ScrollPane displayCars(Stage primaryStage) {
		
		ScrollPane root = new ScrollPane();
		GridPane pane = new GridPane();
		root.setContent(pane);
		final double SPEED = 0.01;
	    root.getContent().setOnScroll(scrollEvent -> {
	        double deltaY = scrollEvent.getDeltaY() * SPEED;
	        root.setVvalue(root.getVvalue() - deltaY);
	    });
		ImageView image[] = new ImageView[this.cars.size()];
		Label carName[] = new Label[this.cars.size()];
		Label carPrice[] = new Label[this.cars.size()];
		HBox h1[] = new HBox[this.cars.size()];
		VBox carBox[] = new VBox[this.cars.size()];
		
		for(int i=0; i<this.cars.size();i++) {
			
			Rectangle photo = new Rectangle(360, 202.5);
			photo.setFill(new ImagePattern(this.cars.get(i).getImage()));
			photo.setArcWidth(15);
			photo.setArcHeight(15);
			carName[i] = new Label(this.cars.get(i).getBrand()+" "+ this.cars.get(i).getModel());
			carPrice[i] = new Label(this.cars.get(i).getPrice() + " L.E");
			carPrice[i].setAlignment(Pos.BASELINE_RIGHT);;
			h1[i] = new HBox(carName[i],carPrice[i]);
			h1[i].setSpacing(160);
			h1[i].setAlignment(Pos.BASELINE_CENTER);
			carBox[i] = new VBox(photo, h1[i]);
			carBox[i].setSpacing(30);
			carBox[i].setPadding(new Insets(22, 18, 15, 18));
			carBox[i].setStyle("-fx-background-color: white; -fx-border-width: 1px;-fx-border-radius: 10px;"); 
			carBox[i].setMinWidth(400);
			carBox[i].setAlignment(Pos.CENTER);
			carBox[i].setId("car");
			pane.add(carBox[i], i%2 , (int) Math.ceil(i/2));
			final int j = i ;
			carBox[i].setOnMouseClicked(e -> {
			carDescription(cars.get(j)).show();
			});
			}
			pane.setPadding(new Insets(50,50,50,50));
			pane.setAlignment(Pos.CENTER);
			pane.setHgap(50);
			pane.setVgap(50);
			return root ;
	}
	
	public Stage carDescription(Car car) {
		try {
			
			ElectricMotors elecCar = (ElectricMotors)car ;
			VBox root = new VBox();
			Scene scene = new Scene(root, 1500,900);
			scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());	
			root.setStyle("-fx-background-color: white");
			Label brand = new Label("Brand:  "+car.getBrand());
			Label model = new Label("Model:  "+car.getModel());
			Label price = new Label("Price:  "+Double.toString(car.getPrice())+ " L.E");
			Label horsePower = new Label("Horsepower:  "+ Integer.toString(car.getHorsePower()));
			Label doors = new Label("Doors:  "+Integer.toString(car.getDoors()));
			Label seats = new Label("Seats:  "+Integer.toString(car.getSeats())+" seats");
			Label topSpeed = new Label("Top Speed:  " +Integer.toString(car.getTopSpeed())+" k/hr");
			Label transmission = new Label("transmission:  "+car.getTransmission());
			Label trunkSize = new Label("trunksize:  "+Double.toString(car.getTrunkSize())+" Liters");
			Label breaksType = new Label("BreaksType:  "+car.getBreaksType());
			Label batteryCapacity = new Label("BatteryCapacity:  "+ elecCar.getBatteryCapacity()+" kWh");
			Label milesPerCharge = new Label("Miles per Charge:  "+elecCar.getMilesPerCharge()+" Miles");
			Label chargingTime = new Label("Charging time:  "+elecCar.getChargingTime()+" Hrs");
			
			Label dateLabel = new Label("Pick a Date ");
			DatePicker datepick = new DatePicker() ;
			Label dateLabel2 = new Label("Charger Installation ");
			DatePicker datepick2 = new DatePicker() ;
			
			Button reserve ;
			if(!this.customer.getReservation()) {
				int key = 0;
				try {
			        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
			        Statement stmt = con.createStatement();
			        ResultSet r = stmt.executeQuery("Select ACCOUNT_ID From CAR WHERE ID = " + car.getid());
			        while(r.next()) {
			        	key = r.getInt(1);
			        }}catch(Exception a) {
			        	System.out.println(a.getMessage());
			        }
				if(key == 0) {
					
					reserve = new Button("R E S E R V E");
					reserve.setAlignment(Pos.CENTER);
					reserve.setMinSize(200, 60);
					reserve.setOnAction(f ->{
						try {
					        elecCar.reserve(this.customer, datepick.getValue().toString(), datepick2.getValue().toString());
							reserve.setText("R E S E R V E D");
							reserve.setId("grey-button");
							reserve.setAlignment(Pos.CENTER);
							reserve.setMinSize(200, 60);
						}catch(Exception e) {
				        	Alert warning = new Alert(Alert.AlertType.ERROR);
				        	warning.setHeaderText("Please Choose a Date");
				        	warning.show();
						}
				});
				}else {
						reserve = new Button("R E S E R V E D");
				        reserve.setId("grey-button");
						reserve.setAlignment(Pos.CENTER);
						reserve.setMinSize(200, 60);
			
			}
			}else {
				reserve = new Button("Can't Reserve");
				reserve.setId("grey-button");
				reserve.setAlignment(Pos.CENTER);
				reserve.setMinSize(200, 60);
			}
			
			VBox v1 = new VBox(brand, model, price, horsePower, topSpeed);
			v1.setSpacing(15);
			VBox v2 = new VBox(transmission, trunkSize, breaksType,batteryCapacity,milesPerCharge);
			v2.setSpacing(15);
			VBox v3 = new VBox(doors, seats,chargingTime);
			v3.setSpacing(15);
			HBox h1 = new HBox(dateLabel,datepick);
			h1.setSpacing(83);
			HBox h2 = new HBox(dateLabel2,datepick2);
			h2.setSpacing(10);
			VBox v4 = new VBox(h1, h2 ,reserve);
			v4.setSpacing(30);
			v4.setAlignment(Pos.CENTER);
			HBox h = new HBox (v1,v2,v3,v4);
			h.setSpacing(70);
			h.setAlignment(Pos.CENTER);
			ImageView im = new ImageView(elecCar.getImage());
			im.setFitHeight(590.625);
			im.setFitWidth(1050);
			HBox image = new HBox(im);
			image.setPadding(new Insets(50,0,0,0));
			image.setAlignment(Pos.CENTER);
			root.getChildren().addAll(image, h);
			root.setSpacing(50);
			root.setPadding(new Insets(30, 100,30,100));
			root.setAlignment(Pos.CENTER);
			Stage stage =  new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			return stage ;
			
		}catch(Exception e) {
			
			VBox root = new VBox();
			Scene scene = new Scene(root, 1400,900);
			scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());	
			root.setStyle("-fx-background-color: white");
			Label brand = new Label("Brand:  "+car.getBrand());
			Label model = new Label("Model:  "+car.getModel());
			Label price = new Label("Price:  "+Double.toString(car.getPrice())+ " L.E");
			Label horsePower = new Label("Horsepower:  "+ Integer.toString(car.getHorsePower()));
			Label doors = new Label("Doors:  "+Integer.toString(car.getDoors()));
			Label seats = new Label("Seats:  "+Integer.toString(car.getSeats()) + " seats");
			Label topSpeed = new Label("Top Speed:  " +Integer.toString(car.getTopSpeed())+" k/hr");
			Label transmission = new Label("transmission:  "+car.getTransmission());
			Label trunkSize = new Label("trunksize:  "+Double.toString(car.getTrunkSize()) + " Liters");
			Label breaksType = new Label("BreaksType:  "+car.getBreaksType());
			
			Label datepickLabel = new Label("Pick a Date ");
			DatePicker datepick = new DatePicker() ;
			
			Button reserve ;
			if(!this.customer.getReservation()) {
				int key = 0;
				try {
			        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
			        Statement stmt = con.createStatement();
			        ResultSet r = stmt.executeQuery("Select ACCOUNT_ID From CAR WHERE ID = " + car.getid());
			        while(r.next()) {
			        	key = r.getInt(1);
			        }}catch(Exception a) {
			        	System.out.println(a.getMessage());
			        }
				if(key == 0) {
				reserve = new Button("R E S E R V E");
				reserve.setAlignment(Pos.CENTER);
				reserve.setMinSize(200, 60);
				reserve.setOnAction(f ->{
					try {
				        car.reserve(this.customer, datepick.getValue().toString(), "hi");
						reserve.setText("R E S E R V E D");
						reserve.setId("grey-button");
						reserve.setAlignment(Pos.CENTER);
						reserve.setMinSize(200, 60);
					}catch(Exception a) {
			        	Alert warning = new Alert(Alert.AlertType.ERROR);
			        	warning.setHeaderText("Please Choose a Date");
			        	warning.show();
					}
				});
				}else {
						reserve = new Button("R E S E R V E D");
				        reserve.setId("grey-button");
						reserve.setAlignment(Pos.CENTER);
						reserve.setMinSize(200, 60);
			
			}
			}else {
				reserve = new Button("Can't Reserve");
				reserve.setId("grey-button");
				reserve.setAlignment(Pos.CENTER);
				reserve.setMinSize(200, 60);
			}
			
			
			HBox h1 = new HBox(datepickLabel,datepick);
			h1.setSpacing(40);
			VBox v1 = new VBox(brand, model, price, horsePower);
			v1.setSpacing(15);
			VBox v2 = new VBox(topSpeed, transmission, trunkSize, breaksType);
			v2.setSpacing(15);
			VBox v3 = new VBox(doors, seats);
			v3.setSpacing(15);
			VBox v4 = new VBox(h1, reserve);
			v4.setSpacing(30);
			v4.setAlignment(Pos.CENTER);
			HBox h = new HBox (v1,v2,v3,v4);
			h.setSpacing(70);
			h.setAlignment(Pos.CENTER);
			ImageView im = new ImageView(car.getImage());
			im.setFitHeight(590.625);
			im.setFitWidth(1000);
			HBox image = new HBox(im);
			image.setPadding(new Insets(50,0,0,0));
			image.setAlignment(Pos.CENTER);
			root.getChildren().addAll(image, h);
			root.setSpacing(50);
			root.setPadding(new Insets(30, 100, 30, 100));
			root.setAlignment(Pos.CENTER);
			Stage stage =  new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			
			return stage ;
		}


		

	}

	public Scene signUpScreen(Stage primaryStage) {
		
		
		HBox pane = new HBox();
		FadeTransition trans = new FadeTransition(Duration.millis(200));
		trans.setNode(pane);
		trans.setFromValue(0);
		trans.setToValue(1);
		trans.play();
		pane.setMaxSize(1100, 750);
		pane.setMinSize(1100, 750);
		pane.setStyle("-fx-background-color: white");
		Scene scene = new Scene(pane, 1200 , 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
		
		//textfields
		
		TextField firstName = new TextField();
		TextField userName = new TextField();
		TextField lastName = new TextField();
		TextField age = new TextField();
		age.setMaxWidth(190);
		PasswordField password = new PasswordField();
		PasswordField confirmPassword = new PasswordField();
		
		//labels
		
		Label firstNameLabel = new Label("First Name");
		Label lastNameLabel = new Label("Last Name");
		Label userNameLabel = new Label("Username");
		Label ageLabel = new Label("Age");
		Label passwordLabel = new Label("Password");
		Label confirmPasswordLabel = new Label("Confirm Passowrd");
		Label browseLabel = new Label("Choose a Photo");
		Label fileLabel = new Label();
		browseLabel.setPadding(new Insets(7,0,0,0));
		
		//buttons
		

		Button browse = new Button("Browse");
		
		
		Button button = new Button("Sign-Up");
		button.setOnAction(e -> {
			try {
			Register(firstName.getText(), lastName.getText(), userName.getText(), Integer.parseInt(age.getText()), password.getText(), confirmPassword.getText(), this.signUpImage ,primaryStage);
			}catch(Exception f) {
	        	Alert warning = new Alert(Alert.AlertType.ERROR);
	        	warning.setHeaderText("Please fill all the requirements");
	        	warning.show();
			}
		});
		Button back = new Button("Back");
		back.setOnAction(e -> {
			primaryStage.setScene(logInScreen(primaryStage));
		});
		back.setId("back_button");
		back.setMinSize(70, 40);
		button.setMinSize(150, 60);
		button.setFont(Font.font(30) );
		
		//vbox's label and field
		VBox v1 = new VBox(firstNameLabel,firstName);
		v1.setSpacing(10);
		VBox v2 = new VBox(lastNameLabel,lastName);
		v2.setSpacing(10);
		VBox v3 = new VBox(ageLabel, age);
		v3.setSpacing(10);
		VBox v4 = new VBox(passwordLabel, password);
		v4.setSpacing(10);
		VBox v5 = new VBox(confirmPasswordLabel, confirmPassword);
		v5.setSpacing(10);
		VBox v6 = new VBox(userNameLabel, userName);
		v6.setSpacing(10);

		
		HBox h1 = new HBox(v1, v2);
		h1.setSpacing(50);
		HBox h2 = new HBox(v4, v5);
		h2.setSpacing(50);
		HBox h3 = new HBox(v6, v3);
		h3.setSpacing(50);
		HBox h4 = new HBox(button);
		h4.setAlignment(Pos.BASELINE_RIGHT);
		HBox h5 = new HBox(browseLabel, browse);
		h5.setSpacing(10);
		
		VBox v7 = new VBox(h5, fileLabel);
		v7.setSpacing(5);
		VBox vx = new VBox(h1,h3 ,h2,v7);
		vx.setSpacing(40);
		VBox vBack = new VBox(back, vx);
		vBack.setSpacing(25);
		VBox v = new VBox(vBack, h4);
		v.setPadding(new Insets(25,50,25,50));
		v.setSpacing(30);
		v.setMinWidth(500);
		pane.getChildren().addAll(v);
		
		Image im = new Image("signup_image.png");
		ImageView image = new ImageView(im);
		browse.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser() ;
			File file = fileChooser.showOpenDialog(primaryStage) ;
			this.file = file ;
			if(file != null) {
				try {
					BufferedImage bufferedImage = ImageIO.read(file) ;
					fileLabel.setText(this.file.toString());
					this.signUpImage = new Image(file.toURI().toString()) ;
					this.signUpImage = SwingFXUtils.toFXImage(bufferedImage, null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		image.setFitHeight(600);
		image.setFitWidth(700);
		pane.getChildren().add(image);
		
		
		return scene ;
	}
	
	public Account checkAccount(String userName, String password) {
		Customer customer = new Customer();
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
        Statement stmt = con.createStatement();
        ResultSet r = stmt.executeQuery("Select * From ACCOUNT WHERE Username = '" + userName + "' AND password = '" + password + "'");
        boolean employee ;
        boolean reservation;
        
        while(r.next()) {
        	this.v = false ;
        	if(r.getInt(7) == 1) {
        		employee = true;
        	}else {
        		employee = false;
        	}
        	if(r.getInt(11) == 1) {
        		reservation = true;
        	}else {
        		reservation = false;
        	}
        	if(!employee) {
        	customer = new Customer(r.getInt(1),r.getString(2), r.getString(3), r.getInt(4),r.getString(5), r.getString(6), false, reservation );
        	InputStream is = r.getBinaryStream("image");
        	OutputStream os = new FileOutputStream(new File("profile.png"));
        	byte[] content = new byte[1024];
        	int size = 0;
        	while((size = is.read(content))!= -1) {
        		os.write(content, 0, size);
        	}
        	this.profile = new Image("file:profile.png");
        	this.profile.isPreserveRatio();
        	os.close();
        	is.close();
        	return customer ;
        	}else {
        	Employee emp= new Employee(r.getInt(1),r.getString(2), r.getString(3), r.getInt(4),r.getString(5), r.getString(6), r.getString(9),r.getString(10), employee);
        	InputStream is = r.getBinaryStream("image");
        	OutputStream os = new FileOutputStream(new File("profile.png"));
        	byte[] content = new byte[1024];
        	int size = 0;
        	while((size = is.read(content))!= -1) {
        		os.write(content, 0, size);
        	}
        	this.profile = new Image("file:profile.png");
        	this.profile.isPreserveRatio();
        	os.close();
        	is.close();
        	return emp ;
        	}
		}
        con.close();
        this.v = true ;

		}catch(Exception e) {
			this.profile = new Image("profile_icon.png");
			
		}
		return customer ;
	}
	
	public void Register(String firstName, String lastName, String userName, int age, String password, String confirmPassword,Image profile ,Stage stage) {
		
		if(password.equals(confirmPassword)) {
		try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
            Statement stmt = con.createStatement();
            FileInputStream fis = new FileInputStream(this.file);
            PreparedStatement pst = null;
            String sql = "Insert into Account (firstname, lastname, age, username, password, image ,employee, reservation) Values (?,?,?,?,?,?,0,0)" ;
            pst = con.prepareStatement(sql) ;
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setInt(3, age);
            pst.setString(4, userName);
            pst.setString(5, password);
            File imgfile = this.file;
            InputStream fin = new FileInputStream(this.file);
            pst.setBinaryStream(6, fin, (int)imgfile.length());
            pst.execute();
            stage.setScene(logInScreen(stage));
            con.close();
            pst.close();

        }catch(Exception e) {
            System.out.println(e);
        }
        }else{
        	Alert warning = new Alert(Alert.AlertType.ERROR);
        	warning.setHeaderText("Passwords do not match");
        	warning.show();
        }
		
	}
	
	public TableView carsDataBase(){
		
		TableView<ElectricMotors> table = new TableView<>();
		TableColumn<ElectricMotors, String> brand = new TableColumn<>("Brand");
		brand.setMinWidth(50);
		brand.setCellValueFactory(new PropertyValueFactory("brand"));
		TableColumn<ElectricMotors, String> model = new TableColumn<>("Model");
		model.setMinWidth(50);
		model.setCellValueFactory(new PropertyValueFactory("model"));
		TableColumn<ElectricMotors, Integer> price = new TableColumn<>("Price");
		price.setCellValueFactory(new PropertyValueFactory("price"));
		TableColumn<ElectricMotors, Integer> horsepower = new TableColumn<>("Horsepower");
		horsepower.setMinWidth(50);
		horsepower.setCellValueFactory(new PropertyValueFactory("horsePower"));
		TableColumn<ElectricMotors, Integer> doors = new TableColumn<>("Doors");
		doors.setCellValueFactory(new PropertyValueFactory("doors"));
		TableColumn<ElectricMotors, Integer> seats = new TableColumn<>("Seats");
		seats.setCellValueFactory(new PropertyValueFactory("seats"));
		TableColumn<ElectricMotors, Integer> topSpeed = new TableColumn<>("Top Speed");
		topSpeed.setMinWidth(60);
		topSpeed.setMaxWidth(100);
		topSpeed.setCellValueFactory(new PropertyValueFactory("topSpeed"));
		TableColumn<ElectricMotors, String> transmission = new TableColumn<>("Transmission");
		transmission.setMinWidth(60);
		transmission.setCellValueFactory(new PropertyValueFactory("transmission"));
		TableColumn<ElectricMotors, Integer> trunkSize = new TableColumn<>("Trunk Size");
		trunkSize.setMinWidth(50);
		trunkSize.setCellValueFactory(new PropertyValueFactory("trunkSize"));
		TableColumn<ElectricMotors, String> breakesType = new TableColumn<>("Brakes Type");
		breakesType.setMinWidth(50);
		breakesType.setCellValueFactory(new PropertyValueFactory("breaksType"));
		TableColumn<ElectricMotors, Integer> accountId = new TableColumn<>("Customer ID");
		accountId.setCellValueFactory(new PropertyValueFactory("accountId"));
		TableColumn<ElectricMotors, Integer> batteryCap = new TableColumn<>("Battery Capacity");
		batteryCap.setMinWidth(50);
		batteryCap.setCellValueFactory(new PropertyValueFactory("batteryCapacity"));
		TableColumn<ElectricMotors, Integer> milesPerCharge = new TableColumn<>("MilesPerCharge");
		milesPerCharge.setMinWidth(50);
		milesPerCharge.setCellValueFactory(new PropertyValueFactory("milesPerCharge"));
		TableColumn<ElectricMotors, Integer> chargeTime = new TableColumn<>("Charging Time");
		chargeTime.setMinWidth(50);
		chargeTime.setCellValueFactory(new PropertyValueFactory("chargingTime"));
		
		ObservableList<ElectricMotors> cars = FXCollections.observableArrayList() ;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
        	Statement stmt = con.createStatement();
        	ResultSet r = stmt.executeQuery("Select * From CAR");
			while(r.next()) {
				ElectricMotors car = new ElectricMotors(r.getInt(1),r.getString(2), r.getString(3), r.getDouble(4), r.getInt(5), r.getInt(6), r.getInt(7),r.getInt(8), r.getString(9), r.getInt(10), r.getString(11),r.getInt(14), r.getInt(15),r.getInt(16),null );
				car.setAccountId(r.getInt(13));
				cars.add(car);
			}
			
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		table.getColumns().addAll(brand,model,price, horsepower,doors,seats,topSpeed,transmission,trunkSize,breakesType,accountId,batteryCap,milesPerCharge,chargeTime );
		table.setItems(cars);
		
		
		return table ;
	}
	
	public TableView customersDataBase(){
		
		TableView<Customer> table = new TableView<>();
		
		TableColumn<Customer, Integer> id = new TableColumn<>("ID");
		id.setCellValueFactory(new PropertyValueFactory("id"));
		TableColumn<Customer, String> firstName = new TableColumn<>("First Name");
		firstName.setMinWidth(100);
		firstName.setCellValueFactory(new PropertyValueFactory("firstName"));
		TableColumn<Customer, String> lastName = new TableColumn<>("Last Name");
		lastName.setMinWidth(100);
		lastName.setCellValueFactory(new PropertyValueFactory("lastName"));
		TableColumn<Customer, Integer> age = new TableColumn<>("Age");
		age.setCellValueFactory(new PropertyValueFactory("age"));
		TableColumn<Customer, String> username = new TableColumn<>("Username");
		username.setMinWidth(130);
		username.setCellValueFactory(new PropertyValueFactory("userName"));
		TableColumn<Customer, String> password = new TableColumn<>("Password");
		password.setMinWidth(140);
		password.setCellValueFactory(new PropertyValueFactory("Password"));
		TableColumn<Customer, String> receiptDate = new TableColumn<>("Receipt Date");
		receiptDate.setMinWidth(150);
		receiptDate.setCellValueFactory(new PropertyValueFactory("receiptDate"));
		TableColumn<Customer, String> charger = new TableColumn<>("Charger Installation");
		charger.setMinWidth(150);
		charger.setCellValueFactory(new PropertyValueFactory("chargerDate"));

		ObservableList<Customer> customers = FXCollections.observableArrayList() ;
		try {
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
	        Statement stmt = con.createStatement();
	        ResultSet r = stmt.executeQuery("Select * From ACCOUNT WHERE EMPLOYEE = 0");
	        boolean reserve;
			while(r.next()) {
	        	if(r.getInt(11) == 1) {
	        		reserve = true;
	        	}else {
	        		reserve = false;
	        	}
				Customer customer = new Customer(r.getInt(1),r.getString(2), r.getString(3), r.getInt(4),r.getString(5), r.getString(6), false, reserve);
				customer.setReceiptDate(r.getString(12));
				customer.setChargerDate(r.getString(13));
				customers.add(customer);
			}
			
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		
		
		table.getColumns().addAll(id,firstName,lastName,age,username,password,receiptDate,charger) ;
		table.setItems(customers);
		
		return table ;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
