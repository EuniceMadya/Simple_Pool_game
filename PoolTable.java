package application;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PoolTable implements TableFactory {
	private String color;
	private Double friction;
	private Point2D size;
	private Rectangle rect;

	/*
	 * * Public constructor It initialize attribute for this table, calls the
	 * setRect method to initialize the attribute of the Rectangle object using the
	 * other parameters needed.
	 */
	public PoolTable(String color, Double friction, Point2D size, Rectangle rect) {
		this.color = color;
		this.friction = friction;
		this.rect = rect;
		this.size = size;
		setRect();
	}

	/*
	 * Sets the color of the table to the color passed by the method.
	 */
	public void setTableColor(String color) {
		this.color = color;
	}
	
	/*
	 * Returns the color of the table.
	 */
	public String getTableColor() {
		return this.color;
	}

	/*
	 * Sets the size of the table to the size passed by the method.
	 */
	public void setSize(Point2D size) {
		this.size = size;
	}
	
	/*
	 * Returns the size of the table.
	 */
	public Point2D getSize() {
		return this.size;
	}

	/*
	 * Sets the friction of the table to the friction passed by the method.
	 */
	public void setFriction(double friction) {
		this.friction = friction;
	}
	
	/*
	 * Returns the size of the table.
	 */
	public double getFriction() {
		return this.friction;
	}

	/*
	 * Sets the rectangle object owned by this table with all the attributes specified.
	 */
	public void setRect() {
		rect = new Rectangle(size.getX(), size.getY());
		rect.setFill(Color.web(color));
	}
	
	/*
	 * Returns the Rectangle object of the table.
	 */
	public Rectangle getRect() {
		return this.rect;
	}

}
