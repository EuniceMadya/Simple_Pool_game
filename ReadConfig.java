package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.geometry.*;

public class ReadConfig extends Application {
	/* this class is where main is */

	public static PoolTable table;
	public static ArrayList<Ball> balls;
	public static ConcretePoolFactory pool_factory;
	public static PoolProduct pool;
	public static int whiteBallindex;

	/*
	 * this method is for reading all the parameters for constructing balls and the
	 * table objects
	 * 
	 */
	public static void readFile(String filename) {
		JSONParser parser = new JSONParser();
		try {
			pool = PoolProduct.getInstance();

			pool_factory = new ConcretePoolFactory();
			JSONObject wholeObject = (JSONObject) parser.parse(new FileReader(filename));

			JSONObject tableJson = ((JSONObject) wholeObject.get("Table"));
			String tableColor = (String) tableJson.get("colour");

			Double tableFriction = (Double) tableJson.get("friction");
			JSONObject tableSize = (JSONObject) tableJson.get("size");

			Point2D tableSize2D = new Point2D((Long) tableSize.get("x"), (Long) tableSize.get("y"));

			table = pool_factory.createTable(tableColor, tableFriction, tableSize2D, new Rectangle());

			JSONObject Balls = ((JSONObject) wholeObject.get("Balls"));
			JSONArray BallArray = (JSONArray) Balls.get("ball");

			balls = new ArrayList<Ball>();
			for (int i = 0; i < BallArray.size(); i++) {
				JSONObject singleBall = (JSONObject) BallArray.get(i);
				String ballcolor = (String) singleBall.get("colour");

				JSONObject ballVel = (JSONObject) singleBall.get("velocity");
				Point2D ballVel2D = new Point2D((Double) ballVel.get("x"), (Double) ballVel.get("y"));

				JSONObject ballPosition = (JSONObject) singleBall.get("position");
				Point2D ballPosition2D = new Point2D((Double) ballPosition.get("x"), (Double) ballPosition.get("y"));

				Double ballMass = (Double) singleBall.get("mass");

				Ball singleball;
				System.out.println("**");
				if (singleBall.get("colour").equals("white")) {
					whiteBallindex = i;
					WhiteBall whiteball = (WhiteBall) pool_factory.createBall(BallType.White, ballcolor, ballPosition2D,
							ballVel2D, ballMass);
					balls.add(whiteball);
				} else {
					singleball = pool_factory.createBall(BallType.Other, ballcolor, ballPosition2D, ballVel2D,
							ballMass);

					balls.add(singleball);
				}
			}
			pool.putBall(balls);
			pool.putTable(table);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*this method is for playing the game on screen*/
	@Override
	public void start(Stage stage) throws Exception {
		Pane canvas = pool.group(whiteBallindex);
		canvas.setCenterShape(true);

		Scene scene = new Scene(canvas, table.getSize().getX(), table.getSize().getY() + 40);

		scene.setFill(Color.LAVENDER);
		stage.setTitle("Pool Game");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		String configPath;

		if (args.length > 0) {
			configPath = args[0];
		} else {
			configPath = "config.json";
		}
		readFile(configPath);
		launch(args);

	}

}