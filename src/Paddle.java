// Assignment: 11 Final Project
// Names: Wang Xun and Regan Yee
// NEU IDs: 0615 (Regan) and 0114 (Wang)

import isdraw.*;
import geometry.*;

/** Represents a <code>{@link Paddle Paddle}</code>: a object that
 * hits the ball in the <code>{@link GameWorld GameWorld
 * }</code> */
public class Paddle extends AObject{
	/** The width of the <code>{@link Paddle Paddle}</code> in pixels. */
	int width;
	
	/** The height of the <code>{@link Paddle Paddle}</code> in pixels. */
	int height;
	
	/** The speed of the <code>{@link Paddle Paddle}</code> in pixels. */
	int speed;
	
	Paddle(Posn posn, int color){
		super(posn, color);
		this.width = GameWorld.PADDLE_WIDTH;
		this.height = GameWorld.PADDLE_HEIGHT;
		this.speed = GameWorld.PADDLE_SPEED;
	}
	
	/** EFFECT: Moves this <code>{@link Paddle Paddle}</code> up. */
	public void moveUp(){
		this.posn.y = this.posn.y - this.speed;
	}
	
	/** EFFECT: Moves this <code>{@link Paddle Paddle}</code> down. */
	public void moveDown(){
		this.posn.y = this.posn.y + this.speed;
	}
    /** EFFECT: Moves this <code>{@link Paddle Paddle}</code> forward */
	public void moveforward() {
		this.posn.x = this.posn.x + 20;
	}
	  /** EFFECT: Moves this <code>{@link Paddle Paddle}</code> backward */
	public void movebackward() {
		this.posn.x = this.posn.x - 20;
	}
	/** EFFECT: Makes this first <code>{@link Paddle Paddle}</code> go back */
	public void goback1() {
		this.posn.x = this.posn.x - 1;
	}
	/** EFFECT: Makes this second <code>{@link Paddle Paddle}</code> go back */
	public void goback2() {
		this.posn.x = this.posn.x + 1;
	}
	/** To draw the <code>{@link Paddle Paddle}</code> */
	void draw(Canvas c){
		c.drawRect(this.posn, this.width, this.height, 
				this.colorChoice());
	}
}
