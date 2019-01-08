package com.przemo.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import static com.mongodb.client.model.Sorts.*;

import com.przemo.Main;
import com.przemo.database.databaseConnection.DatabaseConnection;
import com.przemo.entity.TablePlayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class TableController implements Initializable
{
	
	@FXML
	private TableView<TablePlayer> tableView;
	@FXML
	private TableColumn<TablePlayer,String> tableColumnName;
	@FXML
	private TableColumn<TablePlayer,Integer> tableColumnTime;
	@FXML
	private Text highscore;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		generateTable();
		generateHighScore();
		
	}
	private void generateHighScore()
	{
		highscore.setText("your score is : " + Main.time);
	}
	private void generateTable()
	{		 
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
		
   		 MongoCollection<Document> collection = DatabaseConnection.getCollectionDocument();

		    MongoCursor<Document> cursor =  collection.aggregate(Arrays.asList(
		    		///rozgrywki is an array, after uniwind all elements will create addidional documents
		    		// with integer fields rozgrywki, and now we can sort documents
                Aggregates.unwind("$rozgrywki"),
                Aggregates.sort(ascending("rozgrywki")),
                Aggregates.limit(5),
                 Aggregates.project(Projections.fields(
                         Projections.excludeId(),
                         Projections.include("username"),
                		 Projections.include("rozgrywki")))
                 )).iterator();
		 
		 while (cursor.hasNext()) 
		    {
			 Document document = cursor.next();
			 int minutes = document.getInteger("rozgrywki") / 60;
			 int seconds = document.getInteger("rozgrywki") % 60;
			 String time = ""+minutes+" : "+seconds;
			 tableView.getItems().add(new TablePlayer(document.getString("username"),time));
		    }

	}

}
