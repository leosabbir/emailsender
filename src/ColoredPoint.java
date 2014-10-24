import java.lang.Math;
import java.util.Scanner;

public class ColoredPoint {

	public static void main(String[] args) {

		System.out.println("CS 202.100x - Assignment #3:");
		System.out.println("Three-dimensional Colored Point Class Program:");

		System.out
				.println("Enter the x-, y-, and z-coordinates and the R-, G-, B- values");
		Point point1 = new Point(0, 0, 0, 0, 0, 0);
		point1.readPoint();

		System.out.println("\nThe values of the point you entered: ");
		point1.print();

		System.out.println("\nShifting the point by 2.0 in all coordinates");
		point1.shift_X(2.0);
		point1.shift_Y(2.0);
		point1.shift_Z(2.0);
		System.out.println("Values of the point after shifting: ");
		point1.print();

		System.out.println("\nRotating the point by 90 degrees along X-axis.");
		point1.rotate_X(90.0);
		System.out.println("Values of the point after rotation: ");
		point1.print();

		System.out.println("\nRotating the point by 180 degrees along Y-axis.");
		point1.rotate_Y(180.0);
		System.out.println("Values of the point after rotation: ");
		point1.print();

		System.out.println("\nIncreasing R by 50, G by 70 and B by 100. ");
		point1.shift_R(50);
		point1.shift_G(70);
		point1.shift_B(100);
		System.out.println("Values of the point after shifting RGB: ");
		point1.print();
	}

}

class Point {

	private double x, y, z;
	private int r, g, b;

	public Point(double initial_x, double initial_y, double initial_z,
			int initial_R, int initial_g, int initial_b) {
		this.x = initial_x;
		this.y = initial_y;
		this.z = initial_z;
		this.r = initial_R;
		this.g = initial_g;
		this.b = initial_b;
	}

	public double get_y() {
		return y;
	}

	public void set_y(double y) {
		this.y = y;
	}

	public double get_z() {
		return z;
	}

	public void set_z(double z) {
		this.z = z;
	}

	public int get_R() {
		return r;
	}

	public void set_R(int r) {
		this.r = r;
	}

	public int get_G() {
		return g;
	}

	public void set_G(int g) {
		this.g = g;
	}

	public int get_B() {
		return b;
	}

	public void set_B(int b) {
		this.b = b;
	}

	public double get_x() {
		return x;
	}

	public void set_x(double x) {
		this.x = x;
	}

	// Postcondition:
	// The point has been moved by x_amount along the x axis.
	void shift_X(double x_amount) {
		x += x_amount;
	}

	// Postcondition:
	// The point has been moved by y_amount along the y axis.
	void shift_Y(double y_amount) {
		y += y_amount;
	}

	// Postcondition:
	// The point has been moved by z_amount along the z axis.
	void shift_Z(double z_amount) {
		z += z_amount;
	}

	// Postcondition:
	// The r color value is is shifted by amount. If it goes
	// below 0 then it is set to 0, if it goes beyond 255 it is set to 255.
	void shift_R(int amount) {
		r += amount;
		if (r > 255) { // Color value cannot exceed 255
			r = 255;
		} else if (r < 0) { // Color value sholuld be below 0
			r = 0;
		}
	}

	// Postcondition:
	// The g color value is is shifted by amount. If it goes
	// below 0 then it is set to 0, if it goes beyond 255 it is set to 255.
	void shift_G(int amount) {
		g += amount;
		if (g > 255) { // Color value cannot exceed 255
			g = 255;
		} else if (g < 0) { // Color value sholuld be below 0
			g = 0;
		}
	}

	// Postcondition:
	// The b color value is is shifted by amount. If it goes
	// below 0 then it is set to 0, if it goes beyond 255 it is set to 255.
	void shift_B(int amount) {
		b += amount;
		if (b > 255) { // Color value cannot exceed 255
			b = 255;
		} else if (b < 0) { // Color value sholuld be below 0
			b = 0;
		}
	}

	// Postcondition:
	// The point is rotated deg degrees along X-axis.
	void rotate_X(double deg) {
		double radians = deg * Math.PI / 180;

		double new_y = y * Math.cos(radians) - z * Math.sin(radians);
		double new_z = y * Math.sin(radians) + z * Math.cos(radians);

		y = new_y;
		z = new_z;

	}

	// Postcondition:
	// The point is rotated deg degrees along Y-axis.
	void rotate_Y(double deg) {
		double radians = deg * Math.PI / 180;

		double new_x = x * Math.cos(radians) + z * Math.sin(radians);
		double new_z = -x * Math.sin(radians) + z * Math.cos(radians);

		x = new_x;
		z = new_z;

	}

	// Postcondition:
	// The point is rotated deg degrees along Y-axis.
	void rotate_Z(double deg) {
		double radians = deg * Math.PI / 180;

		double new_x = x * Math.cos(radians) - y * Math.sin(radians);
		double new_y = x * Math.sin(radians) + y * Math.cos(radians);

		x = new_x;
		y = new_y;
	}

	// Postcondition:
	// The point is rotated repeatedly until it reaches the
	// upper right corner (with the negative x- or y-coordinate).
	void rotate_to_upper_right() {
		while ((x < 0) || (y < 0)) {
			rotate_X(90);
		}
	}

	// Postcondition:
	// The return value is true if the point is identical to point p.
	boolean equals(Point p) {
		return (x == p.x) && (y == p.y) && (z == p.z);
	}

	// Postcondition:
	// The return value is true if the point is not identical to point p.
	boolean notEquqls(Point p) {
		return (x != p.x) || (y != p.y) || (z != p.z);
	}

	// Postcondition:
	// Returns the number of rotations needed until the point
	// reaches the upper right corner (with the negative x- or y-coordinate)
	int rotations_needed() {
		int answer;

		answer = 0;
		while ((x < 0) || (y < 0)) {
			rotate_X(90.0);
			++answer;
		}

		return answer;
	}

	// Postcondition:
	// The value returned is the distance to point p.
	double distance(Point p) {
		double a, b, c, c_squared;
		// Calculate differences in x and y coordinates
		a = x - p.x; // Difference in x coordinates
		b = y - p.y; // Difference in y coordinates
		c = z - p.z; // Difference in z coordinates

		// Pythagorean Theorem to calculate square of distance between points
		c_squared = a * a + b * b + z * z;

		return Math.sqrt(c_squared); // sqrt calculates square root (from
										// math.h)
	}

	// Postcondition:
	// The point returned is halfway to point p.
	Point middle(Point p) {
		double x_midpoint, y_midpoint, z_midpoint;

		// Compute the x and y midpoints
		x_midpoint = (x + p.x) / 2;
		y_midpoint = (y + p.y) / 2;
		z_midpoint = (z + p.z) / 2;

		// Construct a new point and return it
		Point midpoint = new Point(x_midpoint, y_midpoint, z_midpoint, 0, 0, 0);
		return midpoint;
	}

	// Postcondition:
	// The sum of p1 and p2 is returned.
	Point add(Point p1, Point p2) {
		double x_sum, y_sum, z_sum;

		// Compute the x and y of the sum
		x_sum = (p1.x + p2.x);
		y_sum = (p1.y + p2.y);
		z_sum = (p1.z + p2.z);

		Point sum = new Point(x_sum, y_sum, z_sum, 0, 0, 0);
		return sum;
	}

	// Postcondition:
	// The x and y coordinates of source have been
	// written to outs. The return value is the ostream outs.
	void print() {
		System.out.println("x: " + x + "  y: " + y + "  z: " + z + "  r: " + r
				+ "  g: " + g + "  b: " + b);
	}

	// Postcondition:
	// The x and y coordinates of target have been
	// read from ins. The return value is the istream ins.
	void readPoint() {
		// Library facilities used: iostream
		// Friend of: point class
		Scanner s = new Scanner(System.in);
		this.x = s.nextDouble();
		this.y = s.nextDouble();
		this.z = s.nextDouble();
		this.r = s.nextInt();
		this.g = s.nextInt();
		this.b = s.nextInt();
	}
}
