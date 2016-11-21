// Assignment: 11 Final Project
// Names: Wang Xun and Regan Yee
// NEU IDs: 0615 (Regan) and 0114 (Wang)

import tester.*;
import geometry.*;
import colors.*;

/** Tests for methods in <code>{@link GameWorld GameWorld}</code>
 * and classes that make up a <code>{@link GameWorld GameWorld}</code>.
 */
public class ExamplesPong {
	ExamplesPong(){}
	
	// Colors:
	IColor red = new Red();
	IColor blue = new Blue();
	IColor yellow = new Yellow();
	IColor green = new Green();
	
	// Posns:
	Posn beginning = new Posn(GameWorld.GAME_WORLD_WIDTH/2, 
							  GameWorld.GAME_WORLD_HEIGHT/2);
	Posn leftside = new Posn(0, 10);
	Posn rightside = new Posn(GameWorld.GAME_WORLD_WIDTH, 10);
	Posn leftPaddlePosn = new Posn(GameWorld.LOSS_AREA, 
			(GameWorld.GAME_WORLD_HEIGHT / 2 - GameWorld.PADDLE_HEIGHT / 2));
	Posn rightPaddlePosn = new Posn(
			GameWorld.GAME_WORLD_WIDTH - 
			GameWorld.LOSS_AREA - GameWorld.PADDLE_WIDTH, 
			(GameWorld.GAME_WORLD_HEIGHT / 2 - GameWorld.PADDLE_HEIGHT / 2));
	
	// Velocities:
	Velocity v1 = new Velocity(10,1);
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
	
	// GameWorld Examples:
	GameWorld initialWorld = new GameWorld();
	
	Posn leftPaddleMoveUp = new Posn (GameWorld.LOSS_AREA, 
			(GameWorld.GAME_WORLD_HEIGHT / 2 - GameWorld.PADDLE_HEIGHT / 2) - GameWorld.PADDLE_SPEED);
	
	// TESTS:
	
	// Resets all changes done.
	public void reset(){
		this.beginning = new Posn(GameWorld.GAME_WORLD_WIDTH/2, 
				  GameWorld.GAME_WORLD_HEIGHT/2);
		this.leftside = new Posn(0, 10);
		this.rightside = new Posn(GameWorld.GAME_WORLD_WIDTH, 10);
		this.leftPaddlePosn = new Posn(GameWorld.LOSS_AREA, 
				(GameWorld.GAME_WORLD_HEIGHT / 2 - GameWorld.PADDLE_HEIGHT / 2));
		this.rightPaddlePosn = new Posn(
				GameWorld.GAME_WORLD_WIDTH - 
				GameWorld.LOSS_AREA - GameWorld.PADDLE_WIDTH, 
				(GameWorld.GAME_WORLD_HEIGHT / 2 - GameWorld.PADDLE_HEIGHT / 2));
		this.v1 = new Velocity(10,1);
		this.v2 = new Velocity(-10,-1);
		this.ball = new Ball(this.beginning, 0, this.v1);
		this.player1loseball = new Ball(this.leftside, 2, this.v2);
		this.player2loseball = new Ball(this.rightside, 2, this.v1);
		this.paddle1 = new Paddle(this.leftPaddlePosn, 2);
		this.paddle2 = new Paddle(this.rightPaddlePosn, 2);
		this.initialWorld = new GameWorld();
	}
	
	// Tests for methods in the class Paddle.
	// Tests the method moveUp in the class Paddle.
	public void testMoveUp(Tester t){
		this.paddle1.moveUp();
		t.checkExpect(this.paddle1.posn, this.leftPaddleMoveUp);
		this.reset();
		t.checkExpect(this.paddle1.posn, this.leftPaddlePosn);
		this.reset();
	}
	
	
	// Tests for the methods in the class GameWorld.
	// Tests the method withinPaddleY in the class GameWorld.
	public void testwithinPaddleY(Tester t){
		this.reset();
		
		t.checkExpect(this.initialWorld.withinPaddleY(this.paddle1, this.ball), true);
		t.checkExpect(this.initialWorld.isCollision(), false);
		this.ball.posn = new Posn(25,150);
		t.checkExpect(this.initialWorld.isCollidePaddle1(), true);
	}

}
