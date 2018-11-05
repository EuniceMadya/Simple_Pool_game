package application;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/*
 * This class is for gather all the objects that needs to be shown on the screen.
 * It applies the singleton design pattern.
 */
public class PoolProduct {

	/* The ball list contains all the ball objects that will move on the table */
	private ArrayList<Ball> balls;

	/* The table */
	private PoolTable pool_table;

	/* The group used for group all balls together */
	private Group group_ball;

	/* The line is indicates the cue for hitting the ball */
	private Line line = new Line(10, 10, 100, 100);
	/* The direction_line is for giving a hint on where the white ball will go */
	private Line direction_line = new Line(10, 10, 100, 100);

	/* The text box is for telling player it is okay to hit the white ball */
	private Text text_hint_hit;
	/* The text box is for giving player instructions on how to hit the ball */
	private Text text_hint_strength;
	/* The text box is for telling player to wait till all balls stop */
	private Text text_hint_wait;

	/* The static object is created as a singleton, will be initialized later */
	private static PoolProduct instance = null;

	private PoolProduct() {
		/*
		 * to make sure only one game is created, and cannot be initialized outside this
		 * class
		 * 
		 */
	}

	/*
	 * This method is the only way to access this singleton object while there's no
	 * actual PoolProduct object yet
	 * 
	 * @ return instance: the PoolProduct object
	 */
	public static PoolProduct getInstance() {
		if (instance == null) {
			instance = new PoolProduct();
		}
		return instance;
	}

	/*
	 * This method is for assign the ball lists reading from the json file
	 * 
	 * @param balls: list of balls
	 */
	public void putBall(ArrayList<Ball> balls) {
		this.balls = balls;
	}

	/*
	 * @return balls: list of balls shown on the table
	 */
	public ArrayList<Ball> getBall() {
		return balls;
	}

	/*
	 * This method is for assign the table object reading from the json file
	 * 
	 * @param pool_table: PoolTable pool_tale
	 */
	public void putTable(PoolTable pool_table) {
		this.pool_table = pool_table;
	}

	/*
	 * @return pool_table: PoolTable object
	 */
	public PoolTable getTable() {
		return this.pool_table;
	}

	/*
	 * This method is for handling all the animation that will appear on the table
	 * 
	 * @param whiteBallindex: index of the white ball
	 * 
	 */
	public Pane group(int whiteBallindex) {
		/*
		 * Initialize the group object for grouping the shapes, and the Pane for the
		 * scene
		 */
		group_ball = new Group();
		Pane canvas = new Pane();

		/* Initialize the text boxes and the sentences for each of them */
		text_hint_hit = new Text(20, pool_table.getSize().getY() + 20, "Press you mouse to start hitting");
		text_hint_strength = new Text(20, pool_table.getSize().getY() + 35,
				"Drag the cue so that you can hit the ball with different strength and angle");
		text_hint_wait = new Text(20, pool_table.getSize().getY() + 25, "please wait until the balls stop");
		text_hint_hit.setFill(Color.GRAY);
		text_hint_strength.setFill(Color.GRAY);
		text_hint_wait.setFill(Color.GRAY);

		/* Set the line attributes when shown on the scene */
		line.setStrokeLineCap(StrokeLineCap.BUTT);
		line.setStrokeWidth(balls.get(whiteBallindex).getCircle().getRadius() / 2);
		line.setStroke(Color.web(pool_table.getTableColor()));
		line.setMouseTransparent(true);

		direction_line.getStrokeDashArray().addAll(25d, 10d);
		direction_line.setStroke(Color.web(pool_table.getTableColor()));
		direction_line.setStrokeDashOffset(10);

		/* Add all the things that will be shown on table to the canvas */
		canvas.getChildren().add(pool_table.getRect());
		canvas.getChildren().add(line);
		canvas.getChildren().add(direction_line);
		canvas.getChildren().add(text_hint_hit);
		canvas.getChildren().add(text_hint_strength);
		canvas.getChildren().add(text_hint_wait);

		/* Set the bounds for balls' movements */
		Bounds bounds = pool_table.getRect().getBoundsInLocal();

		/* Group all the balls into group_ball object */
		for (int i = 0; i < balls.size(); i++) {
			group_ball.getChildren().add(balls.get(i).getCircle());
			balls.get(i).getCircle().setMouseTransparent(true);
		}

		/* Set time frame */
		int timeframe = 10;

		/* Creating a infinite time line for the game */
		Timeline collisionsingle = new Timeline();

		/* Creating key frame for collisions between balls and bouncing off walls */
		KeyFrame kf_collide = new KeyFrame((Duration.millis(timeframe)), new EventHandler<ActionEvent>() {

			double newfriction = pool_table.getFriction() / 1000;

			@Override
			public void handle(ActionEvent t) {
				/* A variable recording if all balls stops or not */
				boolean pause = true;

				/*
				 * Loop through the ball list to see if all balls have stopped, and change text
				 * boxes and line colors accordingly
				 */
				for (int i = 0; i < balls.size(); i++) {
					if (balls.get(i).get_status() == false) {
						line.setStroke(Color.TRANSPARENT);
						direction_line.setStroke(Color.TRANSPARENT);
						text_hint_hit.setFill(Color.LAVENDER);
						text_hint_strength.setFill(Color.LAVENDER);
						text_hint_wait.setFill(Color.RED);
						pause = false;

					}
				}
				if (pause == true) {
					text_hint_hit.setFill(Color.BLUE);
					text_hint_wait.setFill(Color.TRANSPARENT);
				}

				/*
				 * Loop through the balls to detect their collision with boundaries and each
				 * other
				 */
				for (int i1 = 0; i1 < balls.size(); i1++) {

					if (i1 == whiteBallindex) {
						/*
						 * since the velocity of a ball cannot be "completely" 0, a velocity setting to
						 * 0 is needed when human cannot capture it moving anymore
						 */
						if (Math.abs(balls.get(whiteBallindex).getVelocity().getX()) < 0.007
								&& Math.abs(balls.get(whiteBallindex).getVelocity().getY()) < 0.007) {
							balls.get(whiteBallindex).setVelocity(new Point2D(0, 0));
						}
					}
					for (int i2 = i1; i2 < balls.size(); i2++) {
						if (i1 != i2) {
							/*
							 * Since the ball itself always overlap itself, there is no point checking under
							 * that case
							 */
							int indexB1 = i1;
							int indexB2 = i2;
							/*
							 * Create 2 velocities for recording the velocity change and further update
							 * after this time frame
							 */
							Point2D[] newvel = new Point2D[2];
							newvel[0] = new Point2D(0, 0);
							newvel[1] = new Point2D(0, 0);

							/* recording positions of both balls */
							Point2D b1pos = new Point2D(balls.get(indexB1).getCircle().getLayoutX(),
									balls.get(indexB1).getCircle().getLayoutY());

							double dxOne = balls.get(indexB1).getVelocity().getX();
							double dyOne = balls.get(indexB1).getVelocity().getY();

							Point2D b2pos = new Point2D(balls.get(indexB2).getCircle().getLayoutX(),
									balls.get(indexB2).getCircle().getLayoutY());
							double dxTwo = balls.get(indexB2).getVelocity().getX();
							double dyTwo = balls.get(indexB2).getVelocity().getY();

							/*
							 * update positions of both balls according to the their previous velocity
							 * attribute
							 */
							balls.get(indexB1).getCircle()
									.setLayoutX(balls.get(indexB1).getCircle().getLayoutX() + dxOne);
							balls.get(indexB1).getCircle()
									.setLayoutY(balls.get(indexB1).getCircle().getLayoutY() + dyOne);

							balls.get(indexB2).getCircle()
									.setLayoutX(balls.get(indexB2).getCircle().getLayoutX() + dxTwo);
							balls.get(indexB2).getCircle()
									.setLayoutY(balls.get(indexB2).getCircle().getLayoutY() + dyTwo);

							/* change their velocity whenever they bounce off the edges */
							if (dxOne > 0) {
								dxOne -= newfriction;
							}
							if (dxOne < 0) {
								dxOne += newfriction;
							}
							if (dyOne > 0) {
								dyOne -= newfriction;
							}
							if (dyOne < 0) {
								dyOne += newfriction;
							}

							if (dxTwo > 0) {
								dxTwo -= newfriction;
							}
							if (dxTwo < 0) {
								dxTwo += newfriction;
							}
							if (dyTwo > 0) {
								dyTwo -= newfriction;
							}
							if (dyTwo < 0) {
								dyTwo += newfriction;
							}

							if ((balls.get(indexB1).getCircle().getLayoutY() >= (bounds.getMaxY()
									- balls.get(indexB1).getCircle().getRadius() + 2))) {
								dyOne = -dyOne;
							}
							if ((balls.get(indexB1).getCircle()
									.getLayoutY() <= (balls.get(indexB1).getCircle().getRadius() - 2))) {
								dyOne = -dyOne;
							}
							if ((balls.get(indexB1).getCircle().getLayoutX() >= (bounds.getMaxX()
									- balls.get(indexB1).getCircle().getRadius() + 2))) {
								dxOne = -dxOne;
							}
							if ((balls.get(indexB1).getCircle()
									.getLayoutX() <= (balls.get(indexB1).getCircle().getRadius() - 2))) {
								dxOne = -dxOne;
							}

							if ((balls.get(indexB2).getCircle().getLayoutY() >= (bounds.getMaxY()
									- balls.get(indexB2).getCircle().getRadius() + 2))) {
								dyTwo = -dyTwo;

							}
							if ((balls.get(indexB2).getCircle()
									.getLayoutY() <= (balls.get(indexB2).getCircle().getRadius() - 2))) {
								dyTwo = -dyTwo;
							}
							if ((balls.get(indexB2).getCircle().getLayoutX() >= (bounds.getMaxX()
									- balls.get(indexB2).getCircle().getRadius() + 2))) {
								dxTwo = -dxTwo;
							}
							if ((balls.get(indexB2).getCircle()
									.getLayoutX() <= (balls.get(indexB2).getCircle().getRadius() - 2))) {
								dxTwo = -dxTwo;
							}

							balls.get(indexB1).setVelocity(new Point2D(dxOne, dyOne));
							balls.get(indexB1)
									.setPosition(new Point2D(dxOne, dyOne).add(balls.get(indexB1).getPosition()));

							balls.get(indexB2).setVelocity(new Point2D(dxTwo, dyTwo));
							balls.get(indexB2)
									.setPosition(new Point2D(dxTwo, dyTwo).add(balls.get(indexB2).getPosition()));

							/* Detect whether there is a collision with two balls */
							double distance = b1pos.distance(b2pos);

							if (distance <= balls.get(indexB1).getCircle().getRadius()
									+ balls.get(indexB2).getCircle().getRadius()) {
								newvel = collide(balls.get(indexB1), balls.get(indexB2));
							}
							dxOne += newvel[0].getX();
							dyOne += newvel[0].getY();

							dxTwo += newvel[1].getX();
							dyTwo += newvel[1].getY();

							Point2D newvelOne = new Point2D(dxOne, dyOne);
							Point2D newvelTwo = new Point2D(dxTwo, dyTwo);

							/* Update their velocity and position again after the detection of collision */
							balls.get(indexB1).getCircle()
									.setLayoutX(balls.get(indexB1).getCircle().getLayoutX() + dxOne);
							balls.get(indexB1).getCircle()
									.setLayoutY(balls.get(indexB1).getCircle().getLayoutY() + dyOne);

							balls.get(indexB1).setVelocity(newvelOne);
							balls.get(indexB1).setPosition(newvelOne.add(balls.get(indexB1).getPosition()));

							balls.get(indexB2).getCircle()
									.setLayoutX(balls.get(indexB2).getCircle().getLayoutX() + dxTwo);
							balls.get(indexB2).getCircle()
									.setLayoutY(balls.get(indexB2).getCircle().getLayoutY() + dyTwo);

							balls.get(indexB2).setVelocity(newvelTwo);
							balls.get(indexB2).setPosition(newvelTwo.add(balls.get(indexB2).getPosition()));

							/*
							 * Set them to be in stop status when the velocity is less then 0.007 and
							 * greater than -0.007
							 */
							if (Math.abs(newvelOne.getX()) < 0.007 && Math.abs(newvelOne.getY()) < 0.007) {
								balls.get(indexB1).set_stop();
								if (balls.get(indexB1).getCircle().getLayoutX() < 10) {
									balls.get(indexB1).getCircle().setLayoutX(10);
									System.out.println("reset " + indexB1);
								}
								if (balls.get(indexB1).getCircle().getLayoutY() < 10) {
									balls.get(indexB1).getCircle().setLayoutY(10);
									System.out.println("reset " + indexB1);

								}

								if (balls.get(indexB1).getCircle().getLayoutX() > pool_table.getSize().getX() - 10) {
									balls.get(indexB1).getCircle().setLayoutX(pool_table.getSize().getX() - 10);
									System.out.println("reset " + indexB1);

								}
								if (balls.get(indexB1).getCircle().getLayoutY() > pool_table.getSize().getY() - 10) {
									balls.get(indexB1).getCircle().setLayoutY(pool_table.getSize().getX() - 10);
									System.out.println("reset " + indexB1);

								}
							} else {

								balls.get(indexB1).set_move();
							}
							if (Math.abs(newvelTwo.getX()) < 0.007 && Math.abs(newvelTwo.getY()) < 0.007) {
								balls.get(indexB2).set_stop();
								if (balls.get(indexB2).getCircle().getLayoutX() < 10) {
									balls.get(indexB2).getCircle().setLayoutX(10);
								}
								if (balls.get(indexB2).getCircle().getLayoutY() < 10) {
									balls.get(indexB2).getCircle().setLayoutY(10);
								}

								if (balls.get(indexB2).getCircle().getLayoutX() > pool_table.getSize().getX() - 10) {
									balls.get(indexB2).getCircle().setLayoutX(pool_table.getSize().getX() - 10);
								}
								if (balls.get(indexB2).getCircle().getLayoutY() > pool_table.getSize().getX() - 10) {
									balls.get(indexB2).getCircle().setLayoutY(pool_table.getSize().getX() - 10);
								}

							} else {
								balls.get(indexB2).set_move();

							}

						}
					}
				}
			}

		});
		collisionsingle.getKeyFrames().add(kf_collide);

		/*
		 * This event handler is for the cue hitting the balls, when the balls stop
		 * moving, if the user pressed the mouse and dragged, the line will show up and
		 * move accordingly
		 */
		pool_table.getRect().setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent movement) {
				/* This variable is for checking whether the it is okay to hit the ball */
				boolean pause = true;
				for (int ind = 0; ind < balls.size(); ind++) {
					if (balls.get(ind).get_status() == false) {
						pause = false;
					}
				}

				if (pause == true) {
					line.setStroke(Color.BLACK);
					direction_line.setStroke(Color.BLACK);
					text_hint_strength.setFill(Color.RED);
					text_hint_hit.setFill(Color.GRAY);

					line.setStartX(balls.get(whiteBallindex).getPosition().getX());
					line.setStartY(balls.get(whiteBallindex).getPosition().getY());
					direction_line.setStartX(balls.get(whiteBallindex).getPosition().getX());
					direction_line.setStartY(balls.get(whiteBallindex).getPosition().getY());

					Point2D vector_notnorm = new Point2D(
							movement.getSceneX() - balls.get(whiteBallindex).getPosition().getX(),
							movement.getSceneY() - balls.get(whiteBallindex).getPosition().getY());

					Point2D vector = vector_notnorm.normalize();
					double distance = vector_notnorm.magnitude();
					if (distance > 200) {
						distance = 200;
					}
					if (distance < 50) {
						distance = 50;
					}
					line.setEndX(balls.get(whiteBallindex).getPosition().getX() + vector.getX() * distance);
					line.setEndY(balls.get(whiteBallindex).getPosition().getY() + vector.getY() * distance);

					direction_line.setEndX(balls.get(whiteBallindex).getPosition().getX() - vector.getX() * 40);
					direction_line.setEndY(balls.get(whiteBallindex).getPosition().getY() - vector.getY() * 40);

				}

			}

		});

		/*
		 * When the player release the mouse, the white ball will move at the direction
		 * of the cue hitting it
		 */
		pool_table.getRect().setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				boolean pause = true;

				for (int ind = 0; ind < balls.size(); ind++) {
					if (balls.get(ind).get_status() == false) {
						pause = false;
					}
				}
				if (pause == true) {
					System.out.println("valid drop");
					System.out.println("x" + mouseEvent.getSceneX());
					System.out.println("y" + mouseEvent.getSceneY());
					Point2D newVel_for_white = new Point2D(line.getEndX(), line.getEndY());
					Point2D vector_distance = balls.get(whiteBallindex).getPosition().subtract(newVel_for_white);
					System.out.println(vector_distance.magnitude());
					double rate = vector_distance.magnitude() / 100;
					Point2D vector_norm = vector_distance.normalize();
					vector_norm = vector_norm.multiply(rate);
					balls.get(whiteBallindex).setVelocity(vector_norm.add(balls.get(whiteBallindex).getVelocity()));

				}
			}

		});

		/* Set the time frame playing infinitely for stage one of the design */
		collisionsingle.setCycleCount(Timeline.INDEFINITE);
		collisionsingle.play();

		canvas.getChildren().add(group_ball);
		return canvas;
	}

	/*
	 * Handle the collision in real physics when two balls collide this is the codes
	 * referenced from the assignment clarification with all due respect and
	 * gratefulness
	 */
	public Point2D[] collide(Ball b1, Ball b2) {
		Point2D[] twochanges = new Point2D[2];
		twochanges[0] = new Point2D(0, 0);
		twochanges[1] = new Point2D(0, 0);
		Circle circle = b1.getCircle();
		Point2D posA = new Point2D(circle.getLayoutX(), circle.getLayoutY());
		Point2D velA = b1.getVelocity();
		double massA = b1.getMass();
		Circle ball = b2.getCircle();
		Point2D posB = new Point2D(ball.getLayoutX(), ball.getLayoutY());
		Point2D velB = b2.getVelocity();
		double massB = b2.getMass();

		double mR = massB / massA;
		Point2D collisionVector = posB.subtract(posA);
		collisionVector = collisionVector.normalize();

		double vA = collisionVector.dotProduct(velA);
		double vB = collisionVector.dotProduct(velB);

		double a = -(mR + 1);
		double b = 2 * (mR * vB + vA);
		double c = -((mR - 1) * vB * vB + 2 * vA * vB);
		double discriminant = Math.sqrt(b * b - 4 * a * c);
		double root = (-b + discriminant) / (2 * a);

		if (root - vB < 0.01) {
			root = (-b - discriminant) / (2 * a);
		}
		// The resulting changes in velocity for ball A and B
		Point2D deltaVA = collisionVector.multiply(mR * (vB - root));
		twochanges[0] = deltaVA;
		Point2D deltaVB = collisionVector.multiply(root - vB);
		twochanges[1] = deltaVB;
		return twochanges;

	}
}
