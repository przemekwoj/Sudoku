package com.przemo;
	
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import com.przemo.controllers.FirstPageController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static String username;
	public static String password;
	public static ArrayList<Integer> playerList;
	public static int count;
	
	@Override
	public void start(Stage primaryStage) 
	{
		FirstPageController firstPage = new FirstPageController(primaryStage);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
