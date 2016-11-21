// Assignment: 11 Final Project
// Names: Wang Xun and Regan Yee
// NEU IDs: 0615 (Regan) and 0114 (Wang)

import geometry.*;
import isdraw.*;

/** Represents a <code>{@link Ball Ball}</code>: a object that
 * bounces on the <code>{@link Paddle Paddle}</code> in the
 *  <code>{@link GameWorld GameWorld}</code> */
public class Ball extends AObject{
	/** The <code>{@link Velocity Velocity}</code> 
	 * of the <code>{@link Ball Ball}</code>*/
	Velocity velocity;
	
	/** The <code> radius </code> of the <code>{@link Ball Ball}</code>*/
	int rad;
	
	Ball(Posn posn, int color, Velocity velocity){
		super(posn, color);
		this.velocity = velocity;
		this.rad = GameWorld.BALL_RADIUS;
	}
	
	/** Moves this <code>{@link Ball Ball}</code> with its velocity. */
	public void ballMove(){
		this.posn.x = this.posn.x + this.velocity.x;
		this.posn.y = this.posn.y + this.velocity.y;
	}
	
	
	/** Bounces this <code>{@link Ball Ball}</code> 
	 * off the top or bottom walls. */
	public void bounceWall(){
			this.velocity.y = this.velocity.y * -1;
			this.posn.x = this.posn.x + this.velocity.x;
			this.posn.y = this.posn.y + this.velocity.y;
	}
    /** makes the <code>{@link Ball Ball}</code> moves right faster */
 	public void moverightfaster(){
 		this.velocity.x = this.velocity.x - 5;
	}
 	 /** makes the <code>{@link Ball Ball}</code> moves left faster */
 	public void moveleftfaster(){
 		this.velocity.x = this.velocity.x + 5;
	}
	/** To draw the <code>{@link Ball Ball}</code> */
	public void draw(Canvas c){
		c.drawDisk(this.posn, this.rad, this.colorChoice());
	}
	

}
