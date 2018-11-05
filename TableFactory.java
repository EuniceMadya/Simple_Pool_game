package application;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public interface TableFactory {
	/*
	 * @param color 
	 * set the table color using the parameter passed into this method
	 */
	public void setTableColor(String color);
	/*
	 * @return the color of the table
	 */
	public String getTableColor();

	/*
	 * @param size 
	 * set the table size using the parameter passed into this method
	 */
	public void setSize(Point2D size);
	/*
	 * @return the size of the table
	 */
	public Point2D getSize();

	/*
	 * @param 
	 * friction set the table friction using the parameter passed into this
	 * method
	 */
	public void setFriction(double friction);
	/*
	 * @return the friction of the table
	 */
	public double getFriction();

	/*
	 * set the rectangle according to the parameter passed into the Table object
	 */	
	public void setRect();
	/*
	 * @return the object Rectangle of the table
	 */
	public Rectangle getRect();
}
