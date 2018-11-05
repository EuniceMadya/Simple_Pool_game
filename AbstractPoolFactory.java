package application;

import javafx.geometry.Point2D;
import javafx.scene.shape.*;

public interface AbstractPoolFactory {
	/*
	 * *
	 * 
	 * @param type
	 * 
	 * @param color, will be set to "red" if the parameter is null
	 * 
	 * @param Position
	 * 
	 * @param velocity, will be set to 0,0 if the parameter is null
	 * 
	 * @param Mass, will be set to 1.0 if the parameter is null
	 * 
	 * @return the Ball object created by the Builder, the type of ball created will
	 * depend on the BallType input
	 */
	public abstract Ball createBall(BallType type, String color, Point2D Position, Point2D velocity, Double Mass);

	/*
	 * *
	 * 
	 * @param color, will be set to "green" if the parameter is null
	 * 
	 * @param friction, will be set to 0.0 if the parameter is null
	 * 
	 * @param size, will be set to 600,300 if the parameter is null
	 * 
	 * @param rect
	 * 
	 * @return the PoolTable object created by concreteFactory, the type of ball
	 * created will depend on the BallType input
	 */
	public abstract PoolTable createTable(String color, Double friction, Point2D size, Rectangle rect);

}