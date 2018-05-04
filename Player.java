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

// Implementing the Players
public class Player
{
	/**********************************************************
	 * Method Name    : Player
	 * Author         : Rayan Vakil
	 * Date           : 5/1/2018
	 * Course/Section : CSC264 - 801
	 * Program Description:  encapsulates functionality of player
	 *
	 * BEGIN Player
	 *     set all player functionality
	 * END Player
    **********************************************************/
    //local constants
	//local variables
	private int     score; //variable for score
	private Boolean turn; //keeps track of player turn
	private int     wrongMoves; //number of wrong moves
	public Player ( ) //set player
	{
		score      = 0;
		turn       = false;
		wrongMoves = 0;
	}//end Player
	Boolean getturn() { return turn;} //getter player turn
	void setturn(Boolean turn) { this.turn = turn;} //setter player turn
	int getScore() { return score;} //getter score
	void setScore(int score ) { this.score  = score;} //setter score
	int getWrongMoves() { return wrongMoves;} //getter wrong moves
	void setWrongMoves(int wrongMoves ) { this.wrongMoves  = wrongMoves;} //setter wrong moves

}//end Player
