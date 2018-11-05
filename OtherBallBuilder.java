package application;

import javafx.geometry.Point2D;

public class OtherBallBuilder implements BallBuilder {
	private String color;
	private Point2D position;
	private Point2D velocity;
	private double mass;

	/*
	 * the constructor for normal Ball's creation with the attributes given
	 */
	public OtherBallBuilder(String color, Point2D position, Point2D velocity, Double mass) {
		this.color = color;
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;

	}

	/*
	 * * implements the setBallColor method declared in the BallBuilder interface
	 * set the normal ball's color to the given color
	 */
	@Override
	public void setBallColor(String color) {
		this.color = color;

	}

	/*
	 * * implements the setPosition method declared in the BallBuilder interface set
	 * the normal Ball's position
	 */
	@Override
	public void setPosition(Point2D position) {
		this.position = position;

	}

	/*
	 * * implements the setVelocity method declared in the BallBuilder interface set
	 * the normal Ball's velocity
	 */
	@Override
	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;

	}

	/*
	 * * implements the setMass method declared in the BallBuilder interface set the
	 * normal Ball's mass
	 */
	@Override
	public void setMass(Double mass) {
		this.mass = mass;
	}

	/*
	 * * a method for getting the new OtherBall object
	 * 
	 * @return OtherBall object
	 */
	public OtherBall getOtherBall() {
		System.out.println("velocity for other builder " + velocity);
		return new OtherBall(color, position, velocity, mass);
	}

}
