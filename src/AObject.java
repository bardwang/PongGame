// Assignment: 11 Final Project
// Names: Wang Xun and Regan Yee
// NEU IDs: 0615 (Regan) and 0114 (Wang)

import geometry.*;
import colors.*;

/** Represents a <code>{@link AObject AObject}</code>: an abstract object that
 * in the <code>{@link GameWorld GameWorld}</code> */
public abstract class AObject {
	/** The <code>{@link Posn Posn}</code> of the 
	 * <code>{@link AObject AObject}</code> in pixels. */
	Posn posn;
	
	/** The <code>{@link IColor IColor}</code> of the 
	 * <code>{@link AObject AObject}</code>. */
	int color;
	
	AObject(Posn posn, int color){
		this.posn = posn;
		this.color = color;
	}
	
	/** To assign a <code>{@link IColor IColor}</code> to the 
	 * object based on a number 0-4.
	 0 = Green, 1 = Blue, 2 = Black, 3 = Black */
	IColor colorChoice(){
		if (this.color == 0){
			return new Green();}
		else{if (this.color== 1){
			return new Blue();}
		else{if (this.color == 2){
			return new Black();}
		else{if (this.color == 3){
			return new Red();}
		else{return new Blue();}}}}}

}
