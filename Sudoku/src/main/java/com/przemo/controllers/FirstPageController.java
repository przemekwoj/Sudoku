package com.przemo.controllers;


import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.przemo.Main;
import com.przemo.database.databaseConnection.DatabaseConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FirstPageController 
{
	private Stage primaryStage;
	
	
	@FXML
	private Button loginButton;
	@FXML
	private Button createAccountButton;
	@FXML
	private Button guestButton;
	@FXML
	private TextField usernameText;
	@FXML
	private TextField passwordText;
	@FXML
	private Text hintText;
	
	public FirstPageController() {}
	
	public FirstPageController(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		loadPage();
	}
	
	public void loadPage()
	{
	try {
		URL url = new File("src/main/java/com/przemo/layout/firstPage.fxml").toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		String s = "com/przemo/layout/application.css";
		scene.getStylesheets().add(s);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	public void loginButtonPressed() throws IOException
	{
		boolean login = false;
		
		 MongoCollection<Document> collection = DatabaseConnection.getCollectionDocument();
		 MongoCursor<Document> cursor = collection.find().projection(fields(include("username","password"), excludeId())).iterator();
	
		 while (cursor.hasNext()) 
		    {
			 //save temporary document pointed by cursor
			 Document obj =  cursor.next();
			 	if(obj.get("username").equals(usernameText.getText()) && (obj.get("password").equals(passwordText.getText())))
			 	{
				 login = true;
				 Main.password = passwordText.getText();
				 Main.username = usernameText.getText();
				 break;
			 	}
			 	else
			 	{login = false; hintText.setText("wrong pass or name");}
				 
		    }
		
		if(login==true) {openGamePage(); hintText.setText(null);}
	}
	
	public void createAcc() throws IOException
	{
		Stage stage = (Stage) createAccountButton.getScene().getWindow();
		URL url = new File("src/main/java/com/przemo/layout/createAccountPage.fxml").toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		String s = "com/przemo/layout/application.css";
		scene.getStylesheets().add(s);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void playAsGuest() throws IOException
	{
		openGamePage();
	}
	
	public void openGamePage() throws IOException
	{
		Stage stage = (Stage) guestButton.getScene().getWindow();
		URL url = new File("src/main/java/com/przemo/layout/gamePage.fxml").toURL();
		//URL url = new File("src/main/java/com/przemo/layout/WinnerPage.fxml").toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		String s = "com/przemo/layout/application.css";
		scene.getStylesheets().add(s);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}
