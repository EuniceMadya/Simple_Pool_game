package application;

import javafx.geometry.Point2D;

public class WhiteBallBuilder implements BallBuilder {
	private String color;
	private Point2D position;
	private Point2D velocity;
	private double mass;

	/*
	 * the constructor for white Ball's creation with the attributes given
	 */
	public WhiteBallBuilder(String color, Point2D position, Point2D velocity, Double mass) {
		this.color = color;
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
	}

	/*
	 * * implements the setBallColor method declared in the BallBuilder interface
	 * set the white ball's color to white
	 */
	@Override
	public void setBallColor(String color) {
		this.color = "white";

	}

	/*
	 * * implements the setPosition method declared in the BallBuilder interface set
	 * the whiteBall's position
	 */
	@Override
	public void setPosition(Point2D position) {
		this.position = position;

	}

	/*
	 * * implements the setVelocity method declared in the BallBuilder interface set
	 * the whiteBall's velocity
	 */
	@Override
	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;

	}

	/*
	 * * implements the setMass method declared in the BallBuilder interface set the
	 * whiteBall's mass
	 */
	@Override
	public void setMass(Double mass) {
		this.mass = mass;
	}

	/*
	 * * a method for getting the new WhiteBall object
	 * 
	 * @return WhiteBall object
	 */
	public WhiteBall getWhiteBall() {
		System.out.println("velocity for white builder " + velocity);
		return new WhiteBall(color, position, velocity, mass);
	}

}
