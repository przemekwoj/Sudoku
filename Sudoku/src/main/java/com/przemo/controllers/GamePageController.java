package com.przemo.controllers;



import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import com.przemo.Main;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GamePageController implements Initializable
{
	@FXML
	Text text11,text12,text13,text14,text15,text16,text17,text18,text19;
	@FXML
	Text text21,text22,text23,text24,text25,text26,text27,text28,text29;
	@FXML
	Text text31,text32,text33,text34,text35,text36,text37,text38,text39;
	@FXML
	Text text41,text42,text43,text44,text45,text46,text47,text48,text49;
	@FXML
	Text text51,text52,text53,text54,text55,text56,text57,text58,text59;
	@FXML
	Text text61,text62,text63,text64,text65,text66,text67,text68,text69;
	@FXML
	Text text71,text72,text73,text74,text75,text76,text77,text78,text79;
	@FXML
	Text text81,text82,text83,text84,text85,text86,text87,text88,text89;
	@FXML
	Text text91,text92,text93,text94,text95,text96,text97,text98,text99;
	
	@FXML
	Rectangle rec11,rec12,rec13,rec14,rec15,rec16,rec17,rec18,rec19;
	@FXML
	Rectangle rec21,rec22,rec23,rec24,rec25,rec26,rec27,rec28,rec29;
	@FXML
	Rectangle rec31,rec32,rec33,rec34,rec35,rec36,rec37,rec38,rec39;
	@FXML
	Rectangle rec41,rec42,rec43,rec44,rec45,rec46,rec47,rec48,rec49;
	@FXML
	Rectangle rec51,rec52,rec53,rec54,rec55,rec56,rec57,rec58,rec59;
	@FXML
	Rectangle rec61,rec62,rec63,rec64,rec65,rec66,rec67,rec68,rec69;
	@FXML
	Rectangle rec71,rec72,rec73,rec74,rec75,rec76,rec77,rec78,rec79;
	@FXML
	Rectangle rec81,rec82,rec83,rec84,rec85,rec86,rec87,rec88,rec89;
	@FXML
	Rectangle rec91,rec92,rec93,rec94,rec95,rec96,rec97,rec98,rec99;
	
	@FXML
	AnchorPane anchorPane;
	
	@FXML
	Button hardButton;
	@FXML
	Text clockText;
	
	Task<Boolean> clockThread;
	
	long startTime,actualTime;
	int minutes,seconds;
	
	Rectangle actualRec;
	Text actualText;
	
	Text[][] arrayText = new Text[9][9];
	Rectangle[][] arrayRec = new Rectangle[9][9];
	String level = "easy";
	
	String[] shiftArray = new String[9];
	String[] temporaryArray = new String[9];
	
	boolean finished;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, eventKeyPressed);
		Random rand = new Random();
		//assign all textfields to array
		for(int i = 1; i<=9;i++)
		{
			for(int j = 1 ; j<=9; j++)
			{
				String objectNameText = "text"+i+j;
				String objectNameRec = "rec"+i+j;
				try 
				{
					arrayText[i-1][j-1] = (Text) getClass().getDeclaredField(objectNameText).get(this);
					arrayRec[i-1][j-1] = (Rectangle) getClass().getDeclaredField(objectNameRec).get(this);
					arrayRec[i-1][j-1].setFill(Color.web("0x865827",1.0)); 				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		
		createFlashCard(level);
		
	}
	
	public void restart() 
	{
	
		for(int i = 0; i<=8;i++)
		{
			for(int j = 0 ; j<=8; j++)
			{
				arrayRec[i][j].removeEventHandler(MouseEvent.MOUSE_CLICKED, eventMouseClickedOnRec);
				arrayRec[i][j].setFill(Color.web("0x865827",1.0)); 
			}
		}
		clearFlashCard();
		createFlashCard(level);
		
	}
	
	public void createFlashCard(String level)
	{
		
		//make a flashcard
		 	makeFirstRow();
			makeRest(0,3,6);
			makeRest(0,6,6);
			makeRest(3,0,8);
			makeRest(3,3,6);
			makeRest(3,6,6);
			makeRest(6,0,8);
			makeRest(6,3,6);
			makeRest(6,6,6);
		finished = false;
		
		Random rand = new Random();
		
		int fieldNumber = 0;
		
		if(level.equals("easy"))
		{
			fieldNumber = 1;
			
		}
		else if(level.equals("medium"))
		{
			fieldNumber = 45;
		}
		else if(level.equals("hard"))
		{
			fieldNumber = 65;
		}
		
		deleteFields(fieldNumber);
		
		
		//generate clock and open Winner window if user wins
		clockThread = new Task<Boolean>() {

			@Override
			protected void succeeded() {
				// TODO Auto-generated method stub
				super.succeeded();
				try {
					openWinnerWindow();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//generate clock
			@Override
			protected Boolean call() throws Exception 
			{
				startTime = new Date().getTime();
				while(!finished)
				{
					//chekcing if he finished and check correctness of  fields
					if(Main.count == 0) { finished = checkCorrectness(); }
					//calculate clock
					actualTime = new Date().getTime();
					long deltaTime = (actualTime - startTime)/1000;
					seconds = (int) (deltaTime%60);
					minutes = (int) (deltaTime/60);
					Thread.sleep(1000);
					clockText.setText(minutes+" : "+seconds);
					Main.time = 60*minutes + seconds;
				}
				return finished;
			}

		
			
		};
		
		Thread th = new Thread(clockThread);
		th.start();
	}
	


	private void deleteFields(int fieldNumber)
	{
		Random rand = new Random();
		Main.count = 0;
		while(fieldNumber >= -1)
		{
			int i = rand.nextInt(8);
			int j = rand.nextInt(8);
			if(!arrayText[i][j].getText().equals("")) {Main.count++;}
			arrayText[i][j].setText("");
			arrayRec[i][j].setFill(Color.web("0xff931f",1.0));
			arrayRec[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, eventMouseClickedOnRec);
			fieldNumber--;
		}
	}

	//logic depends on my Arrays
	boolean checkIfForbidden(int i,int j,int number)
	{
		boolean forbidden = false;
		//check if field is free
		//check vertical
		if(!forbidden)
		{
			int a = 0,b = 0;
			if(i == 0 || i == 1 || i == 2)
			{
				a = i ;
			}
			else if(i == 3 || i == 4 || i == 5)
			{
				a = i - 3;
			}
			else if(i == 6 || i == 7 || i == 8)
			{
				a = i - 6;
			}
			if(j == 0 || j == 1 || j == 2)
			{
				b = j ;
			}
			else if(j == 3 || j == 4 || j == 5)
			{
				b = j - 3;
			}
			else if(j == 6 || j == 7 || j == 8)
			{
				b = j - 6;
			}
			for( int tempA = a; tempA <= a + 6 ;tempA  = tempA + 3)
			{
				if(forbidden==true)
				{break;}
				for(int tempB = b ; tempB<= b + 6 ;tempB = tempB + 3)
				{
					if(arrayText[tempA][tempB].getText().toString().equals(Integer.toString(number)))
					{
						forbidden = true;
						break;
					}
				}
			}
		}
		//check horizontal
		if(!forbidden)
		{
			int a = 0,b = 0;
			if(i == 0 || i == 3 || i == 6)
			{
				a = i ;
			}
			else if(i == 1 || i == 4 || i == 7)
			{
				a = i - 1;
			}
			else if(i == 2 || i == 5 || i == 8)
			{
				a = i - 2;
			}
			if(j == 0 || j == 3 || j == 6)
			{
				b = j ;
			}
			else if(j == 1 || j == 4 || j == 7)
			{
				b = j - 1;
			}
			else if(j == 2 || j == 5 || j == 8)
			{
				b = j - 2;
			}
			for( int tempA = a; tempA<= a +2 ;tempA++)
			{
				if(forbidden==true)
				{break;}
				for(int tempB = b ; tempB<= b + 2 ;tempB++)
				{
					if(arrayText[tempA][tempB].getText().toString().equals(Integer.toString(number)))
					{
						forbidden = true;
						break;
					}
				}
			}
		}
		//check square
		if(!forbidden)
		{
			for(int b = 0; b < 9 ;b++)
			{
				if(arrayText[i][b].getText().toString().equals(Integer.toString(number)))
				{
					forbidden = true;
					break;
				}
			}
		}
		
		if(forbidden == false) 
			{
			arrayText[i][j].setText(Integer.toString(number)); 
			arrayRec[i][j].setFill(Color.web("0x865827",1.0)); 
			arrayRec[i][j].removeEventHandler(MouseEvent.MOUSE_CLICKED, eventMouseClickedOnRec);
			
			}
		return forbidden;
	}
	
	public void clearFlashCard()
	{
		for(int i = 0; i<=8;i++)
		{
			for(int j = 0 ; j<=8; j++)
			{
				
					arrayText[i][j].setText(null);

			}
		}
	}
	
	public void easylevel() throws IOException
	{
		
		level = "easy";
		restart();
	}
	
	public void mediumlevel() throws IOException
	{
		level = "medium";
		restart();

	}
	
	public void hardlevel() throws IOException
	{
		level = "hard";
		restart();

	}
	
	EventHandler<MouseEvent> eventMouseClickedOnRec = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event)
		{
			if(actualRec != null) {actualRec.setFill(Color.web("0xff931f",1.0));}
			actualRec = (Rectangle) event.getSource();
			actualRec.setFill(Color.web("0x8cee41",1.0));
			//connect Rectangle with Text
			actualText = (Text) actualRec.getParent().getChildrenUnmodifiable().get(1);
		}
	};

	EventHandler<KeyEvent> eventKeyPressed = new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent event) 
		{
			String number = event.getText();
			String[] values = {"1","2","3","4","5","6","7","8","9"};
			
			if( actualText.getText().equals("") || actualText.getText().equals(null) )
				for(int i = 0;i<9;i++)
				{
					if(values[i].equals(number))
					{
						Main.count--;
						actualText.setText(number);
						break;
					}
				}
			
			if(event.getCode().toString().equals("BACK_SPACE") && !actualText.getText().equals(""))
			{
				Main.count++;
				actualText.setText(null);
			}
			System.out.println(Main.count);
		}
	};



	public boolean checkCorrectness()
	{
		boolean forbidden = false;
		System.out.println("checkig");
		for(int i = 0 ; i < 9 ;i++)
		{
			if(forbidden) {break;}
			for(int j = 0 ; j < 9 ;j++)
				if(fordibbenField(i,j)) {forbidden = true; break;}
								
				
		}
		return !forbidden;
	}

	boolean fordibbenField(int i,int j)
	{
		String number = arrayText[i][j].getText();
		//check vertical
		boolean forbidden = false;
				if(!forbidden)
				{
					int a = 0,b = 0;
					if(i == 0 || i == 1 || i == 2)
					{
						a = i ;
					}
					else if(i == 3 || i == 4 || i == 5)
					{
						a = i - 3;
					}
					else if(i == 6 || i == 7 || i == 8)
					{
						a = i - 6;
					}
					if(j == 0 || j == 1 || j == 2)
					{
						b = j ;
					}
					else if(j == 3 || j == 4 || j == 5)
					{
						b = j - 3;
					}
					else if(j == 6 || j == 7 || j == 8)
					{
						b = j - 6;
					}
					for( int tempA = a; tempA <= a + 6 ;tempA  = tempA + 3)
					{
						if(forbidden==true)
						{break;}
						for(int tempB = b ; tempB<= b + 6 ;tempB = tempB + 3)
						{
							if(tempA == i && tempB == j) {continue;}
							if(arrayText[tempA][tempB].getText().toString().equals(number))
							{
								forbidden = true;
								break;
							}
						}
					}
				}
				//check horizontal
				if(!forbidden)
				{
					int a = 0,b = 0;
					if(i == 0 || i == 3 || i == 6)
					{
						a = i ;
					}
					else if(i == 1 || i == 4 || i == 7)
					{
						a = i - 1;
					}
					else if(i == 2 || i == 5 || i == 8)
					{
						a = i - 2;
					}
					if(j == 0 || j == 3 || j == 6)
					{
						b = j ;
					}
					else if(j == 1 || j == 4 || j == 7)
					{
						b = j - 1;
					}
					else if(j == 2 || j == 5 || j == 8)
					{
						b = j - 2;
					}
					for( int tempA = a; tempA<= a +2 ;tempA++)
					{
						if(forbidden==true)
						{break;}
						for(int tempB = b ; tempB<= b + 2 ;tempB++)
						{
							if(tempA == i && tempB == j) {continue;}
							if(arrayText[tempA][tempB].getText().toString().equals(number))
							{
								forbidden = true;
								break;
							}
						}
					}
				}
				//check square
				if(!forbidden)
				{
					for(int b = 0; b < 9 ;b++)
					{
						if(b == j) {continue;}
						if(arrayText[i][b].getText().toString().equals(number))
						{
							forbidden = true;
							break;
						}
					}
				}
	return forbidden;
	}
	
	public boolean allSet()
	{
		boolean allAreSet = true;
		int h  = 0;
		for(int i = 0; i <= 8; i++)
		{
			for(int j = 0; j <= 8; j++)
			{
				h++;
				if(arrayText[i][j].getText().equals(""))
				{
					
					allAreSet = false;
					break;
				}
			}
		}
		System.out.println(h);

		return allAreSet;
	}
	
	private void makeRest(int a , int b,int shift)
	{
		
		shiftOperation(shift);
		int count = 0;
		for(int i = a ; i<=a+2 ; i++)
		{
			for(int j = b ; j<=b+2;j++)
			{
				arrayText[i][j].setText(shiftArray[count]);
				count++;
			}
		}
			
	}
	//shift rows of flashcard
	private void shiftOperation(int shift) 
	{
		
		while(shift!=0)
		{
		for(int i = 1; i<=8;i++)
		{
			temporaryArray[i] = shiftArray[i-1];
		}
		temporaryArray[0]=shiftArray[8];
		for(int i = 0; i<=8;i++)
		{
			shiftArray[i] = temporaryArray[i];
		}
		shift--;
		}
	}

	public void makeFirstRow()
	{	
		boolean forbidden = true;
		
		Random rand = new Random();
		int number = 0;
		int shiftArrayElement = 0;
		for(int i = 0 ; i<=2 ; i++)
		{
			for(int j = 0 ; j<=2;j++)
			{
				while(forbidden)
				{
				number = rand.nextInt(9) + 1;
				forbidden = checkIfOk(number);
				}
				arrayText[i][j].setText(Integer.toString(number));
				shiftArray[shiftArrayElement] = Integer.toString(number);
				shiftArrayElement++;
				forbidden = true;
			}
		}
	}
	
	public boolean checkIfOk(int number) 
	{
		boolean forbidden = false;
		
		for(int i = 0 ; i<=2 ; i++)
		{
			for(int j = 0 ; j<=2;j++)
			{
				if(arrayText[i][j].getText().toString().equals(Integer.toString(number)))
				{
					
					forbidden = true;
				}
			}
		}
		return forbidden;
	}
	
	public void openWinnerWindow() throws IOException
	{
		Stage stage = (Stage) hardButton.getScene().getWindow();
		URL url = new File("src/main/java/com/przemo/layout/WinnerPage.fxml").toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		String s = "com/przemo/layout/application.css";
		scene.getStylesheets().add(s);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}
}
