package application;

import javafx.geometry.Point2D;
import javafx.scene.shape.*;

public class ConcretePoolFactory implements AbstractPoolFactory {

	/*
	 * * implements the createBall method declared in the AbstractPoolFactory
	 * interface set any null value to a default value
	 */
	@Override
	public Ball createBall(BallType type, String color, Point2D Position, Point2D velocity, Double Mass) {
		BallFactoryDirector director = new BallFactoryDirector();
		/* If the color is not specified in json file */
		if (color == null) {
			color = "red";
		}

		/* If the velocity is not specified in json file */
		if (velocity == null) {
			velocity = new Point2D(0, 0);
		}

		/* If the Mass is not specified in json file */
		if (Mass == null) {
			Mass = 1.0;
		}

		/*
		 * Let the director determine what kind of ball needs to be returned using
		 * Ball Builder
		 */
		Ball ball = director.construct(type, color, Position, velocity, Mass);
		return ball;

	}

	/*
	 * * implements the createTable method declared in the AbstractPoolFactory
	 * interface set any null value to a default value
	 */
	@Override
	public PoolTable createTable(String color, Double friction, Point2D size, Rectangle rect) {

		/* If the color is not specified in json file */
		if (color == null) {
			color = "green";
		}
		/* If the friction is not specified in json file */
		if (friction == null) {
			friction = 1.0;
		}

		/* If the size is not specified in json file */
		if (size == null) {
			size = new Point2D(600, 300);
		}
		/*
		 * Since under this case there's only one kind of table needed, we can just
		 * return this certain kind of table
		 */
		return new PoolTable(color, friction, size, rect);
	}

}
