package com.przemo.entity;

import java.util.ArrayList;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.przemo.database.databaseConnection.DatabaseConnection;

public class Player 
{

	public  String username;
	public  String password;
	public  ArrayList<Integer> rozgrywki;
	
	public Player() {}
	
	
	
	public Player(String username, String password, ArrayList<Integer> rozgrywki) {
		super();
		this.username = username;
		this.password = password;
		this.rozgrywki = rozgrywki;
	}
	
	public Player(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}



	@Override
	public String toString() {
		return "Player [username=" + username + ", password=" + password + ", rozgrywki=" + rozgrywki + "]";
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



	public ArrayList<Integer> getRozgrywki() {
		return rozgrywki;
	}



	public void setRozgrywki(ArrayList<Integer> rozgrywki) {
		this.rozgrywki = rozgrywki;
	}
	
	public void createPlayer(String uName,String uPassword)
	{

	     MongoCollection<Player> collection = DatabaseConnection.getCollectionClass();
		 collection.insertOne(new Player(uName, uPassword, new ArrayList<Integer>()));

	}
	
	
}
