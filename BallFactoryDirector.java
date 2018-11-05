package application;

import javafx.geometry.Point2D;

public class BallFactoryDirector {

	/*
	 * *
	 * 
	 * @param type
	 * 
	 * @param color
	 * 
	 * @param Position
	 * 
	 * @param velocity, will be set to 0,0 if the parameter is null
	 * 
	 * @param Mass, will be set to 1.0 if the parameter is null
	 * 
	 * 
	 * @return the WhiteBall object returned by WhiteBallBuilder if it is WHITE
	 * BallType
	 * 
	 * @return OtherBall object returned by OtherBallBuilder if it is OTHER BallType
	 * 
	 * @return null in default case
	 */
	public Ball construct(BallType type, String color, Point2D Position, Point2D velocity, Double Mass) {
		Ball ball;
		switch (type) {
		case White:
			WhiteBallBuilder whitebuilder = new WhiteBallBuilder(color, Position, velocity, Mass);
			ball = whitebuilder.getWhiteBall();
			break;
		case Other:
			OtherBallBuilder otherbuilder = new OtherBallBuilder(color, Position, velocity, Mass);
			ball = otherbuilder.getOtherBall();
			break;
		default:
			ball = null;
			break;
		}
		return ball;

	}
}
