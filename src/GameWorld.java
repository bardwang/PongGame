import colors.Black;
import geometry.Posn;
import isdraw.*;
import tester.*;
import tunes.*;

// Assignment: 11 Final Project
// Names: Wang Xun and Regan Yee
// NEU IDs: 0615 (Regan) and 0114 (Wang)

/** Represents a <code>{@link GameWorld GameWorld}</code>:
 * the playing board consisting of two <code>{@link Paddle Paddle}</code>
 * and <code>{@link Ball Ball}</code>. */
public class GameWorld extends World{
	GameWorld(){}
	// Posns:
	Posn beginning = new Posn(GameWorld.GAME_WORLD_WIDTH/2, 
							  GameWorld.GAME_WORLD_HEIGHT/2);
	Posn leftside = new Posn(0, 10);
	Posn rightside = new Posn(GameWorld.GAME_WORLD_WIDTH, 10);
	Posn leftPaddlePosn = new Posn(GameWorld.LOSS_AREA, 
			(GameWorld.GAME_WORLD_HEIGHT / 2 - GameWorld.PADDLE_HEIGHT / 2));
	Posn rightPaddlePosn = new Posn(
			GameWorld.GAME_WORLD_WIDTH - 
			GameWorld.LOSS_AREA - PADDLE_WIDTH, 
			(GameWorld.GAME_WORLD_HEIGHT / 2 - GameWorld.PADDLE_HEIGHT / 2));
	Posn topMiddle = new Posn(GameWorld.GAME_WORLD_WIDTH / 2, 0);
	Posn botMiddle = new Posn(GameWorld.GAME_WORLD_WIDTH / 2, GameWorld.GAME_WORLD_HEIGHT);
	
	// Velocities:
	Velocity v1 = new Velocity(10,0);
	Velocity v2 = new Velocity(-10,-1);
	
	// Balls:
	Ball ball = new Ball(this.beginning, 0, this.v1); // Initial Ball
	// A example where Player 1 loses.
	Ball player1loseball = new Ball(this.leftside, 2, this.v2); 
	// A example where Player 2 loses.
	Ball player2loseball = new Ball(this.rightside, 2, this.v1);
	
	// Paddles:
	Paddle paddle1 = new Paddle(this.leftPaddlePosn, 2);
	Paddle paddle2 = new Paddle(this.rightPaddlePosn, 2);
	
	/** The height of the screen in pixels. */
	public static int GAME_WORLD_HEIGHT = 600;
	
	/** The width of the screen in pixels. */
	public static int GAME_WORLD_WIDTH = 1000;
	
	/** The size of the <code>{@link Ball Ball}</code>'s radius 
	 * in pixels. */
	public static int BALL_RADIUS = 10;
	
	/** The width of the <code>{@link Paddle Paddle}</code> 
	 * in pixels. */
	public static int PADDLE_WIDTH = 10;
	
	/** The height of the <code>{@link Paddle Paddle}</code> 
	 * in pixels. */
	public static int PADDLE_HEIGHT = 150;
	
	/** The speed that the <code>{@link Paddle Paddle}</code> 
	 * moves in pixels. */
	public static int PADDLE_SPEED = GAME_WORLD_HEIGHT / 10;
	
	/** Amount of space for loss area */
	public static int LOSS_AREA = 5;
	
	/** The score needed to win.*/
	public static int MAX_SCORE = 10;
	
	/** The number of ticks per second.*/
	public static double GAME_SPEED = 0.05;
	
	/** The score of Player 1 (left paddle).*/
	public int PLAYER1_SCORE = 0;
	
	/** The score of Player 2 (right paddle).*/
	public int PLAYER2_SCORE = 0;
	
	/** The number of collisions so far */
	public int NUM_COLLISIONS = 0;
	
	/** The length of the background music*/
	public int tunelength = gameMusic.length;
	
	
	/** Tells us where we are in the background music*/
	public int tickCount = 0;
	
	
	
	
	/** Is the given <code>{@link Ball Ball}</code> within 
	 * the given <code>{@link Paddle Paddle}</code>'s Y coordinate. */
	public boolean withinPaddleY(Paddle p, Ball b){
		return
		b.posn.y > p.posn.y &&
		(b.posn.y < (p.posn.y + GameWorld.PADDLE_HEIGHT));
	}
	
	/** Is there a collision with a <code>{@link Ball Ball}</code>
	 *  and a <code>{@link Paddle Paddle}</code> 
	 *  in this <code>{@link GameWorld GameWorld}</code>? */
	public boolean isCollision(){
		return
		this.isCollidePaddle1() || this.isCollidePaddle2();
	}
	
	/** Is there a collision with a <code>{@link Ball Ball}</code>
	 *  and Player 1's <code>{@link Paddle Paddle}</code> 
	 *  in this <code>{@link GameWorld GameWorld}</code>? */
	public boolean isCollidePaddle1(){
		return
		(this.ball.posn.x - GameWorld.BALL_RADIUS <= 
			this.paddle1.posn.x + GameWorld.PADDLE_WIDTH && 
		this.withinPaddleY(this.paddle1, this.ball));
	}
	
	/** Is there a collision with a <code>{@link Ball Ball}</code>
	 *  and Player 2's <code>{@link Paddle Paddle}</code> 
	 *  in this <code>{@link GameWorld GameWorld}</code>? */
	public boolean isCollidePaddle2(){
		return
		(this.ball.posn.x + GameWorld.BALL_RADIUS >= this.paddle2.posn.x &&
				this.withinPaddleY(this.paddle2, this.ball));
	}
	
	/** EFFECT: Bounce the <code>{@link Ball Ball}</code> off 
	 *  the <code>{@link Paddle Paddle}</code> 
	 *  in this <code>{@link GameWorld GameWorld}</code>. */
	public void bouncePaddle(){
		if(this.isCollision()){
			this.ball.velocity.x = this.ball.velocity.x * -1;
			this.ball.velocity.y = this.ball.velocity.y + 
									(((this.paddle1.posn.y + GameWorld.PADDLE_HEIGHT / 2) - this.ball.posn.y) / 10);
			if(this.isCollidePaddle1()){
			this.ball.posn.x = this.paddle1.posn.x + GameWorld.BALL_RADIUS + GameWorld.PADDLE_WIDTH + this.ball.velocity.x;
			}
			if(this.isCollidePaddle2()){
			this.ball.posn.x = this.paddle2.posn.x - GameWorld.BALL_RADIUS + this.ball.velocity.x;
			}
			this.ball.posn.y = this.ball.posn.y + this.ball.velocity.y;
			this.NUM_COLLISIONS = this.NUM_COLLISIONS + 1;}
		else{
			return;}
		}
	
	/** EFFECT: Changes the <code>{@link Ball Ball}</code>'s color
	 *  the more collisions that occur. */
	public void changeColor(){
		if(this.NUM_COLLISIONS <= 5){
			this.ball.color = 0;
		}
		else if(this.NUM_COLLISIONS > 5 &&
			   this.NUM_COLLISIONS <= 10){
				this.ball.color = 1;
			}
		else if(this.NUM_COLLISIONS > 10 &&
			   this.NUM_COLLISIONS <= 20){
			this.ball.color = 2;
		}
		else this.ball.color = 3;
	}
	
	/** EFFECT: Resets the GameWorld for another round.*/
	public void reset(){
		this.ball.posn = new Posn(GameWorld.GAME_WORLD_WIDTH/2, 
				  			 GameWorld.GAME_WORLD_HEIGHT/2);
		this.ball.velocity = new Velocity(10,1);;
		this.ball.color = 0;
		this.paddle1.posn = new Posn(GameWorld.LOSS_AREA, 
				(GameWorld.GAME_WORLD_HEIGHT / 2 - 
						GameWorld.PADDLE_HEIGHT / 2));
		this.paddle2.posn = new Posn(
				GameWorld.GAME_WORLD_WIDTH - 
				GameWorld.LOSS_AREA - PADDLE_WIDTH, 
				(GameWorld.GAME_WORLD_HEIGHT / 2 - 
						GameWorld.PADDLE_HEIGHT / 2));
		this.NUM_COLLISIONS = 0;
	}
	
	
	
	/** EFFECT: Draws this <code>{@link World World}</code> 
	 *  on the <code>{@link Canvas Canvas}</code> */
	public void draw(){
		this.ball.draw(theCanvas);
		this.paddle1.draw(theCanvas);
		this.paddle2.draw(theCanvas);
		theCanvas.drawLine(this.topMiddle, this.botMiddle , new Black());
	}

	/** EFFECT: To produce a new <code>{@link World World}</code>  
	 * from this <code>{@link World World}</code> based on a keystroke.*/
	public void onKeyEvent(String key) {
		if (key.equals("w") && (this.paddle1.posn.y > 0)){
			this.paddle1.moveUp();
			this.keyTunes.addNote(15, noteC);}
		
		else if (key.equals("s") && 
				(this.paddle1.posn.y < 
						GameWorld.GAME_WORLD_HEIGHT - GameWorld.PADDLE_HEIGHT)){
			this.paddle1.moveDown();
			this.keyTunes.addNote(15, noteC);}
		
		else if(key.equals("up") && (this.paddle2.posn.y > 0)){
			this.paddle2.moveUp();
			this.keyTunes.addNote(15, noteD);}
		
		else if (key.equals("down") && 
				(this.paddle2.posn.y < 
						GameWorld.GAME_WORLD_HEIGHT - GameWorld.PADDLE_HEIGHT)){
			this.paddle2.moveDown();
			this.keyTunes.addNote(15, noteD);}
		else if (key.equals("j")){
		    this.paddle1.moveforward();
		    if (this.isCollision())
		    	this.ball.moverightfaster();
		    this.keyTunes.addNote(10, noteA);}
		else if (key.equals("k")){
		    this.paddle2.movebackward();
		    if (this.isCollision())
		    	this.ball.moveleftfaster();
		    this.keyTunes.addNote(10, noteB);}
	}

	/** EFFECT: To produce a new <code>{@link World World}</code> 
	 * from this <code>{@link World World}</code> after one tick. */
	public void onTick() {
		// Bounce off the top or bottom wall.
		if (this.ball.posn.y - GameWorld.BALL_RADIUS <= 0 ||
			this.ball.posn.y + GameWorld.BALL_RADIUS >= 
							   GameWorld.GAME_WORLD_HEIGHT)
			ball.bounceWall();
		
		// Player 1 scores.
		else if (this.ball.posn.x > 
				 GameWorld.GAME_WORLD_WIDTH + 10){
			this.tickTunes.addNote(tunes.SoundConstants.SAX, noteC);
			this.reset();
			this.PLAYER1_SCORE = this.PLAYER1_SCORE + 1;
			System.out.println("Player 1 Scores! Score: " 
					           + this.PLAYER1_SCORE + ":" + this.PLAYER2_SCORE);}
		
		// Player 2 scores.
		else if (this.ball.posn.x < -10){
			this.tickTunes.addNote(tunes.SoundConstants.SAX, noteC);
			this.reset();
			this.PLAYER2_SCORE = this.PLAYER2_SCORE + 1;
			System.out.println("Player 2 Scores! Score: " 
							   + this.PLAYER1_SCORE + ":" + this.PLAYER2_SCORE);}
		
		// Player 1 wins.
		else if (this.PLAYER1_SCORE == GameWorld.MAX_SCORE){
			this.tickTunes.addNote(tunes.SoundConstants.CHOIR, noteA);
			this.endOfWorld("Player 1 Wins!");}
		
		// Player 2 wins.
		else if (this.PLAYER2_SCORE == GameWorld.MAX_SCORE){
			this.tickTunes.addNote(tunes.SoundConstants.CHOIR, noteB);
			this.endOfWorld("Player 2 Wins!");}
		

		// Paddle collision.
		else if (isCollision()){
			this.bouncePaddle();
			this.changeColor();
			this.tickTunes.addNote(TELEPHONE, noteA);
			}
		
		// Ball continues.
		else {this.ball.ballMove();
		// to make both paddles go back
		if(this.paddle1.posn.x != GameWorld.LOSS_AREA &&
		this.paddle2.posn.x != GameWorld.GAME_WORLD_WIDTH - PADDLE_WIDTH -GameWorld.LOSS_AREA){
			this.paddle1.goback1();
			this.paddle2.goback2();
		}
		// to make the paddle1 go back to their position
		else if(this.paddle1.posn.x != GameWorld.LOSS_AREA){
			this.paddle1.goback1();
		}
		// to make the paddle2 go back to their position
		else if(this.paddle2.posn.x != GameWorld.GAME_WORLD_WIDTH - PADDLE_WIDTH -GameWorld.LOSS_AREA){
			this.paddle2.goback2();
		}
		}
		
		// Plays the background music
		this.tickTunes.addNote(PIANO, gameMusic[this.tickCount]);
		//tunes.SoundConstants.
		
		// Adds 1 to the tune counter
		this.tickCount = (this.tickCount + 1) % this.tunelength;
	}
	
	
	
	/** Background Elite4 Music that plays during the game */
	public static int gameMusic[] = {
		
		noteC,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,noteDownG,0,0,noteC,0,0,noteDownAp,
		0,0,0,0,0,0,0,0,0,0,0,0,noteDownF,0,0,noteDownAp,0,0,noteDownA,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,noteDownA,0,0,0,0,0,0,0,0,noteDownAp,
		0,0,0,0,0,0,0,0,0,0,0,noteC,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,noteDownG,0,0,noteC,0,0,0,0,noteD,0,0,0,0,0,0,0,noteDownAp,
		noteDownAp,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,noteC,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,noteDownG,0,0,noteC,0,
		0,0,0,noteDownAp,0,0,noteDownAp,0,0,noteC,0,0,noteD,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,noteE,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,noteD,0,0,noteE,0,0,0,0,noteF,0,0,0,0,0,0,noteD,0,0,noteD,0,
		0,0,0,0,0,noteAp,0,0,0,0,0,0,noteF,0,0,noteF,0,0,0,0,0,0,0,0,0,noteE,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,noteD,0,0,noteE,0,0,noteF,0,0,
		noteD,0,0,noteF,0,0,noteAp,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		noteG,0,0,0,0,0,0,0,0,0,0,0,0,0,0,noteUpC,0,0,0,0,0,0,0,0,0,0,0,0,noteG,0,0,
		0,0,0,0,0,noteAp,0,0,0,0,0,0,0,noteA,0,0,0,0,0,0,0,noteG,0,0,0,0,0,0,0,noteF,
		0,0,0,0,0,0,0,noteE,0,0,noteE,0,0,noteF,0,0,noteG,0,0,0,0,0,0,0,0,0,0,0,0,noteG,
		0,0,noteUpC,0,0,noteG,0,0,0,0,0,noteAp,0,0,0,0,0,0,0,noteA,0,0,0,0,0,0,0,noteG,
		0,0,0,0,0,0,0,noteE,0,0,noteF,0,0,noteG,0,0,0,0,0,0,0,0,0,0,0,0,noteUpC,0,0,0,
		0,0,0,0,0,0,0,0,0,noteG,0,0,0,0,0,0,0,noteAp,0,0,0,0,0,0,0,noteA,0,0,0,0,0,0,0,
		noteG,0,0,0,0,0,0,0,noteF,0,0,0,0,0,noteE,0,0,noteE,0,0,noteF,0,0,noteG,0,0,0,
		0,0,noteG,0,0,noteG,0,0,noteA,0,0,noteAp,0,0,0,0,0,noteUpC,0,0,noteUpC,0,0,
		noteUpD,0,0,noteUpE,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,noteUpC,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,noteG,0,0,noteUpC,0,0,noteAp,0,0,0,0,0,0,0,0,0,0,0,0,noteF,
		0,0,noteAp,0,0,noteA,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,noteA,0,0,
		0,0,0,0,0,0,noteAp,0,0,0,0,0,0,0,0,0,0,0,noteUpC,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
	};
	
	
	public static void main(String[] arv){
		GameWorld world = new GameWorld();
		world.bigBang(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, GAME_SPEED);
	}
	
	

}
