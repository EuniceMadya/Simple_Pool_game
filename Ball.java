package application;

import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/*
 * An abstract class for declaring the common Ball attributes sharing between normal balls and the white cue ball
 * 
 */
public abstract class Ball {
	private BallType type;
	private String color;
	private Point2D position;
	private Point2D velocity;
	private Double mass;
	private Circle circle;
	private boolean stop;

	/*
	 * The constructor will initialize the following attribute for either creating a
	 * new whiteBall or OtherBall object. After the assignment of the other values,
	 * the constructor will initialize the Circle object included in this Ball
	 * object as well
	 */
	public Ball(String color, Point2D position, Point2D velocity, Double mass) {
		this.color = color;
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
		this.stop = false;
		setCircle();
	}

	/*
	 * set the ball's status to be stop
	 */
	public void set_stop() {
		this.stop = true;
	}
	
	/*
	 * @return the status of the ball: whether it stops or is still moving
	 */
	public boolean get_status() {
		return this.stop;
	}

	/*
	 * set the ball's status to be "move"
	 */
	public void set_move() {
		this.stop = false;
	}
	
	/*
	 * set the ball's type to be either normal ball or white ball
	 */
	public void setType(BallType type) {
		this.type = type;
	}

	/*
	 * get the ball's type
	 */
	public BallType getType() {
		return type;
	}

	/*
	 * @param color: 
	 * set the ball's color using the parameter passed into this
	 * method
	 */
	public void setBallColor(String color) {
		this.color = color;
	}

	/*
	 * @return the color of the ball
	 */
	public String getBallColor() {
		return color;
	}

	/*
	 * @param position: 
	 * set the ball's color using the parameter passed into this method
	 */
	public void setPosition(Point2D position) {
		this.position = position;
	}

	/*
	 * @return the position of the table
	 */
	public Point2D getPosition() {
		return position;
	}

	/*
	 * @param velocity: 
	 * set the ball's velocity using the parameter passed into this method
	 */
	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}
	/*
	 * @return the position of the table
	 */
	public Point2D getVelocity() {
		return velocity;
	}

	/*
	 * @param mass: 
	 * set the ball's mass using the parameter passed into this method
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}
	/*
	 * @return the mass of the table
	 */
	public double getMass() {
		return mass;
	}

	/*
	 * Initialize the Circle object needed to be shown on the Scene for the game
	 */
	public void setCircle() {
		this.circle = new Circle();
		this.circle.setFill(Color.web(color));
		this.circle.setRadius(10);
		this.circle.setLayoutX(position.getX());
		this.circle.setLayoutY(position.getY());
	}

	public Circle getCircle() {
		return circle;
	}

}
