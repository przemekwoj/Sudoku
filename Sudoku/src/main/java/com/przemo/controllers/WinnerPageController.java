package com.przemo.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.przemo.Main;
import com.przemo.database.databaseConnection.DatabaseConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WinnerPageController implements Initializable
{

	@FXML
	Text timeText;
	@FXML
	Text usernameText;
	@FXML
	Button okButton;
	
	int minutes;
	int seconds;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		usernameText.setText(Main.username);
		 minutes = Main.time/60;
		 seconds = Main.time%60;
		timeText.setText("your time was " + " "+minutes+ " : "+seconds);
	}
	
	public void okButtonAction() throws IOException
	{
  		MongoCollection<Document> collection = DatabaseConnection.getCollectionDocument();
		BasicDBObject update = new BasicDBObject("$addToSet", new BasicDBObject("rozgrywki", Main.time));
  		collection.updateOne(Filters.eq("username",Main.username), update) ;  		 
		Stage stage = (Stage) okButton.getScene().getWindow();
		URL url = new File("src/main/java/com/przemo/layout/gamePage.fxml").toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		String s = "com/przemo/layout/application.css";
		scene.getStylesheets().add(s);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public void showTable() throws IOException
	{
		URL url = new File("src/main/java/com/przemo/layout/table.fxml").toURL();
		Parent root = FXMLLoader.load(url);
		String s = "com/przemo/layout/application.css";
		Scene scene = new Scene(root);
		scene.getStylesheets().add(s);
		Stage stage = new Stage();
		stage.setTitle("Table");
		stage.setScene(scene);
		// focus only on one stage(window)
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
}
