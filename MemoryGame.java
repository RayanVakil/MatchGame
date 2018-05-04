/**********************************************************
 * Program Name   : MatchGame
 * Author         : Rayan Vakil
 * Date           : 5/1/2018
 * Course/Section : CSC264 - 801
 * Program Description: A picture matching game
 *      created for fun and educational purposes.
 *
 * Methods:
 * -------
 * main - creates new MemoryGame
 * MemoryGame - Sets up the whole look and layout of game
 * ResetListner - Handles game reset button click options
 * NewListner - New game button creates brand new game
 * ClearListner - Clear button action resets game to
 * 				  original form
 * TimerListener - Will close the cards if there is no match
 * TwoPlayerListener - Will turn on 2 player settings
 * OnePlayerListener - Will turn on 1 player mode
 * ImageButtonListner - Handle click on cards
 * Player - encapsulates functionality of player
 * Card - Implements picture buttons
 **********************************************************/

//import statements
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Font;

public class MemoryGame extends JFrame
{
	//local constants
	//local variables
    static String files[] = { "picture1.jpg","picture2.jpg","picture3.jpg",
    	"picture4.jpg","picture5.jpg", "picture6.jpg", "picture7.jpg",
   	    "picture8.jpg"}; //picture files to match
    static  JButton buttons[]; //buttons array
    JButton      Info;  //click to get game info
    JButton      NewGame; //new game button
    JButton      ClearGame; //clear game button
    JButton      ResetGame; //reset game button
    JPanel		 infoPanel; //description of game
    JLabel       CurrentPlayer; //label for player
    JLabel  	 Player1ScoreLabel; //label for P1 score
    JLabel  	 Player2ScoreLabel; //label for P2 score
    JLabel 		 Player1Score; //p1 score
    JLabel 		 Player2Score; //p2 score
    JLabel 		 Player1WrongMovesLabel; //wrong moves label P1
    JLabel 		 Player2WrongMovesLabel; //wrong moves label P2
    JLabel 		 Player1WrongMoves; //p1 wrong clicks
    JLabel       Player2WrongMoves; //p2 wrong clicks
    JLabel       Winner;  //label to show winner
    JPanel       buttonPanel; //variable for button panel
    JPanel       cardPanel; //variable for card panel
    int          numGames;  //keeps track of games played
    int          numMatched = 0; //keeps track of matches made
    JRadioButton OnePlayer; //button to choose 1P
    JRadioButton TwoPlayers; //button to choose 2P
    ButtonGroup  BG; //group of buttons
    Player       player1; //create player 1
    Player       player2; //create player 2
    ImageIcon    closedIcon; //closed card
    int          numButtons; //num of cards
    ImageIcon    icons []; //pictures
    Timer        myTimer; //timer for card turnover
    int          numClicks = 0; //keeps tracks of clicks
    int          oddClickIndex = 0; //prevent timer till 2nd pic
    int          currentIndex = 0; //current state of index
    int          numPlayers = 1; //number of players

     /**********************************************************
	  * Method Name    : MemoryGame
	  * Author         : Rayan Vakil
	  * Date           : 5/1/2018
	  * Course/Section : CSC264 - 801
	  * Program Description: Sets up the look and layout of
	  *   					 game
	  *
	  * BEGIN MemoryGame
	  * 	create and add all objects to game
	  *		FOR(number of cards in game)
	  *			set them all to the closed state
	  *			add the panel to game
	  *		END FOR
	  *		FOR(number of cards)
	  *			shuffle randomly
	  *		END FOR
	  * END MemoryGame
     **********************************************************/

    public MemoryGame()
    {
		//local constants
    	//local variables
    	/********************   Start  *****************/

    	//choose layout
		setLayout(new GridLayout(3,1));

    	// Create the Button Panel and the Card Panels
    	buttonPanel = new JPanel();
    	cardPanel  = new JPanel();
    	infoPanel  = new JPanel();

    	//set button panel layout
    	buttonPanel.setLayout(new FlowLayout());

    	//title
        setTitle("Match Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set layout for cards
        cardPanel.setLayout(new GridLayout(2, files.length));

        //set back of card image
        closedIcon = new ImageIcon("BackCard.jpg");

        //button creation and picture icons
        JButton Info = new JButton("Game Info");
        Info.addActionListener(new InfoListener());
        numButtons = files.length*2;
        buttons  = new JButton[numButtons];
        icons  = new ImageIcon[numButtons];

        // Create the Player Selection Button Group
        OnePlayer = new JRadioButton("One Player");
        OnePlayer.setSelected(true);
        TwoPlayers = new JRadioButton("Two Players");

 		//create button group and add listeners
        BG = new ButtonGroup();
        BG.add(OnePlayer);
        OnePlayer.addActionListener(new OnePlayerListener());
        BG.add(TwoPlayers);
        TwoPlayers.addActionListener(new TwoPlayerListener());
        buttonPanel.add(OnePlayer);
        buttonPanel.add(TwoPlayers);
        NewGame = new JButton("New Game");
        NewGame.addActionListener(new NewListner());
        ClearGame = new JButton("Clear Game");
        ClearGame.addActionListener(new ClearListner());
        ResetGame = new JButton("Reset Game");
        ResetGame.addActionListener(new ResetListner());
        buttonPanel.add(NewGame);
        buttonPanel.add(ClearGame);
        buttonPanel.add(ResetGame);

		//create labels for showcasing score
        Player1ScoreLabel = new JLabel("Player 1 Score:");
        Player2ScoreLabel= new JLabel("Player 2 Score:");
        Player1Score  = new JLabel("0");
        Player2Score = new JLabel("0");

		//create labels for showcasing wrong moves
        Player1WrongMovesLabel = new JLabel("Player 1 Wrong Moves:");
        Player2WrongMovesLabel= new JLabel("Player 2 WrongMoves:");
        Player1WrongMoves  = new JLabel("0");
        Player2WrongMoves = new JLabel("0");

		//more labels
        CurrentPlayer = new JLabel("Current Player - Player1");
        Winner = new JLabel("");

		//add labels
        buttonPanel.add(Player1ScoreLabel);
        buttonPanel.add(Player1Score);
        buttonPanel.add(Player2ScoreLabel);
        buttonPanel.add(Player2Score);
        buttonPanel.add(Player1WrongMovesLabel);
        buttonPanel.add(Player1WrongMoves);
        buttonPanel.add(Player2WrongMovesLabel);
        buttonPanel.add(Player2WrongMoves);
        buttonPanel.add(CurrentPlayer);
        Player2ScoreLabel.setVisible(false);
        Player2Score.setVisible(false);
        Winner.setVisible(false);
        buttonPanel.add(Winner);
        Player2WrongMovesLabel.setVisible(false);
        Player2WrongMoves.setVisible(false);

        // Create the players
        player1 = new Player();
        player2 = new Player();
        player1.setturn(true);
        player1.setScore(0);
        player1.setWrongMoves(0);
        player2.setScore(0);
        player2.setWrongMoves(0);
        add(buttonPanel);

        //add info button
        add(infoPanel);
        infoPanel.add(Info);


 		// Set the initial Card images to the Closed state
        for(int i = 0, j= 0; i < files.length;i++)
        {
            icons[j] = new ImageIcon(files[i]);
            buttons[j] = new JButton("");
            buttons[j].addActionListener(new ImageButtonListner());
            buttons[j].setIcon(closedIcon);
            cardPanel.add(buttons[j++]);

            icons[j] = icons[j-1];
            buttons[j] = new JButton("");
            buttons[j].addActionListener(new ImageButtonListner());
            buttons[j].setIcon(closedIcon);
            cardPanel.add(buttons[j++]);

        }//end for
        add(cardPanel);

        // Shuffle the Cards
        Random gen = new Random();
        for(int i = 0;i < numButtons ;i++)
        {
            int rand = gen.nextInt(numButtons);
            ImageIcon temp = icons[i];
            icons[i] = icons[rand];
            icons[rand]=temp;
        }//end for
        // Pack and show the Panels
        pack();
        setVisible(true);
        // Create the Timer
        myTimer = new Timer(1000, new TimerListener());
    } //end MemoryGame

	private class InfoListener implements ActionListener
	{
	    /**********************************************************
		 * Method Name    : InfoListener
		 * Author         : Rayan Vakil
		 * Date           : 5/1/2018
		 * Course/Section : CSC264 - 801
		 * Program Description:  When button is clicked, game info
		 * 	screen will come up
		 *
		 *	BEGIN InfoListener
		 *		create frame
		 *		add label
		 *	END InfoListener
		**********************************************************/
		public void actionPerformed (ActionEvent e)
		{
			//create frame and add infolabel
			//local constants
			//local variables
			JFrame infoFrame = new JFrame("Game Info");  //create frame
			JLabel infolabel = new JLabel("<html><center>Match Game!</center><br/> "
		    +"There are 8 pairs of cards, match them up"+
		    " by clicking on them.  While playing 2 players, players take"+
		    " turns matching using the same mouse.</html>");//create message to display
		    infoFrame.add(infolabel);
		    infoFrame.setSize(500,200);
		    infoFrame.setVisible(true);
		}//end actionPerformed
	}//end InfoListener

    private class ResetListner implements ActionListener
    {
		 /**********************************************************
		  * Method Name    : ResetListner
		  * Author         : Rayan Vakil
		  * Date           : 5/1/2018
		  * Course/Section : CSC264 - 801
		  * Program Description:  When button is clicked, game will
		  *		completely reset.
		  *
		  * BEGIN ResetListner
		  * 	FOR (number of cards)
		  *     	shuffle cards
		  *		END FOR
		  *	    FOR(number of cards)
		  *			close the cards
		  *		END FOR
		  *		update game information
		  * END ResetListner
		 **********************************************************/
        public void actionPerformed ( ActionEvent e)
        {
			//local constants
			//local variables

    	    /********************   Start  *****************/

        	// Shuffle the cards
            Random gen = new Random();
            for(int i = 0;i < numButtons ;i++)
            {
                int rand = gen.nextInt(numButtons);
                ImageIcon temp = icons[i];
                icons[i] = icons[rand];
                icons[rand]=temp;
            }//end for

            // Close all the Cards
        	for(int i = 0; i < 2*files.length;i++)
        	{

                buttons[i].setIcon(closedIcon);
                buttons[i].revalidate();
        	}//end for

        	// Reset the scores
        	player1.setturn(true);
        	player2.setturn(false);
            player1.setScore(0);
            player2.setScore(0);
            Player1Score.setText(Integer.toString(player2.getScore()));
            Player2Score.setText(Integer.toString(player2.getScore()));
            player1.setWrongMoves(0);
            player2.setWrongMoves(0);
            Player1WrongMoves.setText(Integer.toString(player1.getWrongMoves()));
            Player2WrongMoves.setText(Integer.toString(player2.getWrongMoves()));
            CurrentPlayer.setText("Current Player - Player1");
            numMatched = 0;
            numGames=0;
            Winner.setText("");
            cardPanel.repaint();
        }//end actionPerformed
    }//end ResetListner

    // New Game Listener
    private class NewListner implements ActionListener
    {
		 /**********************************************************
		  * Method Name    : NewListner
		  * Author         : Rayan Vakil
		  * Date           : 5/1/2018
		  * Course/Section : CSC264 - 801
		  * Program Description:  New game listener will create a
		  *   new game
		  *
		  * BEGIN NewListner
		  * 	FOR (random number)
		  *     	shuffle cards
		  *		END FOR
		  *	    FOR(number of cards)
		  *			close the cards
		  *		END FOR
		  *		update info
		  *	END NewListner
		 **********************************************************/
        public void actionPerformed ( ActionEvent e)
        {
			//local constants
			//local variables

    	    /********************   Start  *****************/

        	// Shuffle the Cards again
            Random gen = new Random();
            for(int i = 0;i < numButtons ;i++)
            {
                int rand = gen.nextInt(numButtons);
                ImageIcon temp = icons[i];
                icons[i] = icons[rand];
                icons[rand]=temp;
            }//end for

            // Close all cards
        	for(int i = 0; i < 2*files.length;i++)
        	{
                buttons[i].setIcon(closedIcon);
                buttons[i].revalidate();
        	}//end for

        	player1.setturn(true);
        	player2.setturn(false);
        	CurrentPlayer.setText("Current Player - Player1");
        	Winner.setText("");
            numMatched = 0;
            cardPanel.repaint();
        }//end actionPerformed
    }//end NewListner

   // Clear Button Listener
    private class ClearListner implements ActionListener
    {
		 /**********************************************************
		  * Method Name    : ClearListener
		  * Author         : Rayan Vakil
		  * Date           : 5/1/2018
		  * Course/Section : CSC264 - 801
		  * Program Description:  Will clear all open cards to
		  *		closed state
		  *
		  * BEGIN ClearListner
		  * 	FOR(number of cards)
		  *			close the cards
		  *		END FOR
		  *		update panel
		  * END ClearListner
		 **********************************************************/
        public void actionPerformed ( ActionEvent e)
        {
			//local constants
			//local variables

    	    /********************   Start  *****************/

			//close all cards
        	for(int i = 0; i < 2*files.length;i++)
        	{
                buttons[i].setIcon(closedIcon);
                buttons[i].revalidate();
        	}//end for
        	numMatched = 0;
        	cardPanel.repaint();
        } //end actionPerformed
    }//end ClearListner

    private class TimerListener implements ActionListener
    {
		 /**********************************************************
		  * Method Name    : TimerListener
		  * Author         : Rayan Vakil
		  * Date           : 5/1/2018
		  * Course/Section : CSC264 - 801
		  * Program Description:  listener will close the cards if
		  *    there is no match
		  *
		  * BEGIN TimerListener
		  * 	close cards
		  * END TimerListener
		 **********************************************************/
        public void actionPerformed ( ActionEvent e)
        {
			//local constants
			//local variables

    	    /********************   Start  *****************/

            buttons[currentIndex].setIcon(closedIcon);
            buttons[oddClickIndex].setIcon(closedIcon);
            myTimer.stop();
	    }//actionPerformed
    }//TimerListener

    private class TwoPlayerListener implements ActionListener
    {
		 /**********************************************************
		  * Method Name    : TwoPlayerListener
		  * Author         : Rayan Vakil
		  * Date           : 5/1/2018
		  * Course/Section : CSC264 - 801
		  * Program Description:  Handle click on the Two Player
		  * 	Group Button
		  *
		  * BEGIN TwoPlayerListener
		  * 	set all 2 player objects visible
		  * END TwoPlayerListener
		 **********************************************************/
        public void actionPerformed ( ActionEvent e)
        {
			//local constants
			//local variables

    	    /********************   Start  *****************/

			//turn on settings for 2 player
            numPlayers = 2;
            Player2Score.setVisible(true);
            Player2ScoreLabel.setVisible(true);
            Player2WrongMoves.setVisible(true);
            Player2WrongMovesLabel.setVisible(true);
            Winner.setVisible(true);
            CurrentPlayer.setText("Current Player - Player1");
            Winner.setText("");
        }//end actionPerformed
    }//end TwoPlayerListener

    private class OnePlayerListener implements ActionListener
    {
		 /**********************************************************
		  * Method Name    : OnePlayerListener
		  * Author         : Rayan Vakil
		  * Date           : 5/1/2018
		  * Course/Section : CSC264 - 801
		  * Program Description:  Handle Click on the One Player
		  * 	Group Button
		  *
		  * BEGIN OnePlayerListener
		  * 	set only 1 player objects visible
		  * END OnePlayerListener
		 **********************************************************/
        public void actionPerformed ( ActionEvent e)
        {
			//local constants
			//local variables

    	    /********************   Start  *****************/

			//turn on settings for 1 player
            numPlayers = 1;
            Player2Score.setVisible(false);
            Player2ScoreLabel.setVisible(false);
            Player2WrongMoves.setVisible(false);
            Player2WrongMovesLabel.setVisible(false);
            CurrentPlayer.setText("Current Player - Player1");
            Winner.setText("");
            Winner.setVisible(false);
        }//end actionPerformed
    }//end OnePlayerListener

    private class ImageButtonListner implements ActionListener
    {
		 /**********************************************************
		  * Method Name    : ImageButtonListner
		  * Author         : Rayan Vakil
		  * Date           : 5/1/2018
		  * Course/Section : CSC264 - 801
		  * Program Description:  Handle Click on the Cards
		  * BEGIN ImageButtonListner
		  *   IF(timer is running)
		  *	 	 reset pictures
		  *		 increment click
		  *	  END IF
		  *	  FOR(number of cards)
		  *	 	 IF(event = button)
		  *			 index = current button
		  *		 END IF
		  *	  IF(second click)
		  *	 	 IF(current index)
		  *			 decrement num clicks
		  *		 END IF
		  *			 IF(card current != oddclickindex)
		  *			 IF(players = 1)
		  *				 set wrong moves
		  *			 END IF
		  *			 ELSE
		  *				 IF(player1 turn)
		  *				 	 set settings
	      *				 END IF
	      *				 ELSE
		  *				 	 player2 settings
		  *				 END ELSE
		  *			 END ELSE IF ELSE
		  *		  ELSE
		  *			 IF(players = 1)
		  *				 set score
		  *			 ELSE
		  *				 IF(player1 turn)
		  *					 set score
		  *				 END IF
		  *				 ELSE
		  *					 player2 score
		  *				 END ELSE
		  *				 IF(matched all)
		  *					 IF(player1 score > player2)
		  *						 player 1 wins
		  *					 END IF
		  *					 ELSE IF(player 2 score > player1 score)
		  *						 Player 2 wins
		  *					 END ELSE IF
		  *					 ELSE
		  *						 tie
		  *					 END ELSE
		  *				 END IF
		  *			 END ELSE
		  *		 END ELSE
		  *	   END IF
		  *	   ELSE
		  *		 oddclickindex = current
		  *	   END ELSE
		  * END ImageButtonListner
		 **********************************************************/
        public void actionPerformed(ActionEvent e)
        {
			//local constants
			//local variables

    	    /********************   Start  *****************/

        	// If timer is running , we are resetting the pictures so return
            if(myTimer.isRunning())
                return;
            numClicks++;

			// Find the Card that was clicked
            for(int i = 0;i < numButtons ; i++)
            {
                if(e.getSource() == buttons[i])
                {
                    buttons[i].setIcon(icons[i]);
                    currentIndex = i;
                }//end if
            }//end for

            // If second Click
            if(numClicks %2 == 0)
            {
                 if(currentIndex == oddClickIndex)
                 {
                      numClicks--;
                      return;
                 }//end if
                 //Match was not made
                  if(icons[currentIndex] != icons[oddClickIndex])
                  {
                	  // Record the error
                	  if(numPlayers == 1)
                	  {
                		  player1.setWrongMoves(player1.getWrongMoves()+1);
                		  Player1WrongMoves.setText(Integer.toString(player1.getWrongMoves()));
                	  }//end if
                	  else
                	  {
                		  if(player1.getturn())
                		  {
                       		  player1.setWrongMoves(player1.getWrongMoves()+1);
                    		  Player1WrongMoves.setText(Integer.toString(player1.getWrongMoves()));
                			  player1.setturn(false);
                			  player2.setturn(true);
                			  CurrentPlayer.setText("Current Player - Player2");
                		  }//end if
                		  else
                		  {
                       		  player2.setWrongMoves(player2.getWrongMoves()+1);
                    		  Player2WrongMoves.setText(Integer.toString(player2.getWrongMoves()));
                   			  player1.setturn(true);
                			  player2.setturn(false);
                			  CurrentPlayer.setText("Current Player - Player1");
                		  }//end else
                	  }//end else if else

                        myTimer.start();
                  }//end if
                  else
                  {
                	  // Match made  - Record Scores
                	  numMatched++;
                	  if(numPlayers == 1)
                	  {
                		  player1.setScore(player1.getScore()+1);
                		  Player1Score.setText(Integer.toString(player1.getScore()));
                      }//end if
                  	  else
                  	  {
                		  if(player1.getturn())
                		  {
                       		  player1.setScore(player1.getScore()+10);
                    		  Player1Score.setText(Integer.toString(player1.getScore()));

                		  }//end if
                		  else
                		  {
                       		  player2.setScore(player2.getScore()+10);
                    		  Player2Score.setText(Integer.toString(player2.getScore()));

                		  }//end else

                		  // Find Winner
                		  if(numMatched == files.length)
                		  {
                			  if(player1.getScore() > player2.getScore())
                			  {
                				  player1.setScore(player1.getScore()+25);
                				  Player1Score.setText(Integer.toString(player1.getScore()));
                				  Winner.setText("Player 1 is the Winner");
                			  }//end if
                			  else if(player1.getScore() < player2.getScore())
                			  {
                				  player2.setScore(player2.getScore()+25);
                				  Player2Score.setText(Integer.toString(player2.getScore()));
                				  Winner.setText("Player 2 is the Winner");
                			  }//end else if

                			  else
                			  {
                				  Winner.setText("Its a Tie");
                			  }//end else
                		  }//end if
                	  }//end else

                  }//end else
             }//end if
             else
             {
                   oddClickIndex = currentIndex;
             }//end else

        }//end actionPerformed
    }//end ImageButtonListner

	 /**********************************************************
	  * Method Name    : main
	  * Author         : Rayan Vakil
	  * Date           : 5/1/2018
	  * Course/Section : CSC264 - 801
	  * Program Description:  create new instance of game
	  *
	  * BEGIN main
	  *		create new game
	  *	END main
	 **********************************************************/
    public static void main(String[] args)
    {
		//local constants
		//local variables

    	/********************   Start  *****************/

		//create new game
        new MemoryGame();
    }//end main
}//end class



