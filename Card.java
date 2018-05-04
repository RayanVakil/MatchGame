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

//import statement
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Card extends JButton
{
	/**********************************************************
	 * Method Name    : Card
	 * Author         : Rayan Vakil
	 * Date           : 5/1/2018
	 * Course/Section : CSC264 - 801
	 * Program Description: Implements the picture buttons
	 *
	 * BEGIN Card
	 *     set Icon
	 * END Card
    **********************************************************/

	//local constants
	//local variables
	ImageIcon icon;

	/********************   Start  *****************/

	public Card(String fileName , ImageIcon closedIcon)
	{
		icon = new ImageIcon(fileName);
		setIcon(closedIcon);
	}//end Card
}//end Card
