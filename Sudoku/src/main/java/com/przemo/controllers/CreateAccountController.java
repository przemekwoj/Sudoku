package com.przemo.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Projections.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.przemo.Main;
import com.przemo.database.databaseConnection.DatabaseConnection;
import com.przemo.entity.Player;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateAccountController
{
	@FXML
	private Button backButton;
	@FXML
	private TextField usernameText;
	@FXML
	private TextField passwordText;
	@FXML
	private Text hintText;
	
	public void goBack() throws IOException
	{
		Stage stage = (Stage) backButton.getScene().getWindow();
		URL url = new File("src/main/java/com/przemo/layout/firstPage.fxml").toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		String s = "com/przemo/layout/application.css";
		scene.getStylesheets().add(s);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void createAcc() throws IOException
	{
		boolean canCreateAcc = false;
		hintText.setText(null);
		
		if(usernameText.getText().equals("") || passwordText.getText().equals(""))
		{
			hintText.setText("try another password");
			hintText.setFill(Color.RED);
		}
		else
		{
			 MongoCollection<Document> collection = DatabaseConnection.getCollectionDocument();
			 MongoCursor<Document> cursor = collection.find().projection(fields(include("username"), excludeId())).iterator();
		
			 while (cursor.hasNext()) 
			    {
				 if(cursor.next().get("username").equals(usernameText.getText()))
				 {
					 canCreateAcc = false;
					 usernameText.setText(null);
					 passwordText.setText(null);
					 hintText.setText("this username is already exist , please try another one");
					 break;
				 }
				 else{canCreateAcc = true; hintText.setText(null);}
					 
			    }
			 
		}
		
		if(canCreateAcc ==  true)
		{
			Player p = new Player();
			p.createPlayer(usernameText.getText(),passwordText.getText());
			Main.username = usernameText.getText();
			Main.password = passwordText.getText();
			openGamePage();
		}
		
		
	}
	
	public void openGamePage() throws IOException
	{
		Stage stage = (Stage) backButton.getScene().getWindow();
		URL url = new File("src/main/java/com/przemo/layout/gamePage.fxml").toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		String s = "com/przemo/layout/application.css";
		scene.getStylesheets().add(s);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}
