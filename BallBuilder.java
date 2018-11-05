package application;

import javafx.geometry.Point2D;


public interface BallBuilder {
	/*
	 * @param color
	 * set the color of the ball needed to be created
	 */
	public void setBallColor(String color);
	/*
	 * @param position
	 * set the position of the ball needed to be created
	 */
	public void setPosition(Point2D position);
	/*
	 * @param velocity
	 * set the velocity of the ball needed to be created
	 */
	public void setVelocity(Point2D velocity);
	/*
	 * @param mass
	 * set the mass of the ball needed to be created
	 */
	public void setMass(Double mass);
}

