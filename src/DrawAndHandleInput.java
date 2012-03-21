/**

  DrawAndHandleInput.java
  
  This class handles all the drawing and the input.
  
  =========================================================
  
  Adam Steinberger
  Skidmore College

  =========================================================
  
  This is a program that
  	- draws six hexagons at different intensities
  	- for red, the intensities look the same at 60%, 57%, 54%, 51%, 48%
  	- for green, the intensities look the same at 72%, 67%, 62%, 57%, 52%
  	- for blue, the intensities look the same at 66%, 62%, 58%, 54%, 50%

  This shows world coordinates of 
   0,0 -> 200, 200
  All polygons are defined to live in those coordinates.

  The window size is initialized to be:
   400 x 400 pixels (but can be resized by the user)

  The glu.gluOrtho2D function says to show the whole world 
  from 0,0 to 200,200
  within that 400 x 400 window (or whatever size window
  there is after user resizing).

  =========================================================

  Credit where credit is due:
  
  This program is a modified version of a program user in 
  Michael Eckmann's Computer Graphics course at Skidmore 
  College, which is a modified version of a program used
  in G. Drew Kessler's Computer Graphics course at Lehigh
  University.

  =========================================================

  @author	
  	Adam Steinberger steinz08@gmail.com
  
 */

import java.awt.Point;
import java.awt.event.*;

import javax.media.opengl.GL;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class DrawAndHandleInput implements GLEventListener, KeyListener,
		MouseListener, ActionListener {

	// provide access to OpenGL methods
	private GL gl;
	private GLU glu;

	// define the world coordinate limits
	public static final int MIN_X = 0;
	public static final int MIN_Y = 0;
	public static final int MAX_X = 200;
	public static final int MAX_Y = 200;

	// define hexagon parameters
	private Point center = new Point(100, 100);
	private static float intDiff = 0.08f;
	private static float[] intensities = { 0.5f, 0.58f, 0.66f, 0.74f, 0.82f,
			0.90f };
	private static String color = "green";
	private static boolean drawLines = true;
	private static boolean dispIntense = true;

	public DrawAndHandleInput() {
		;
	} // end constructor

	// GLEventListener Methods
	/**
	 * This method is called by the drawable to do initialization.
	 * 
	 * @param drawable
	 *            - The GLCanvas that will be drawn to
	 */
	public void init(GLAutoDrawable drawable) {

		this.gl = drawable.getGL();
		this.glu = new GLU();

		// set the clear color to white
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

		// sets up the projection matrix from world to window coordinates
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();

		// show the whole world within the window
		glu.gluOrtho2D(MIN_X, MAX_X, MIN_Y, MAX_Y);

		// wraps the GL to provide error checking and so
		// it will throw a GLException at the point of failure
		drawable.setGL(new DebugGL(drawable.getGL()));

	} // end init()

	/**
	 * This method is called when the screen needs to be drawn.
	 * 
	 * @param drawable
	 *            The GLCanvas that will be drawn to
	 */
	public void display(GLAutoDrawable drawable) {

		// clear the color buffer
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// draw hexagons from the outside in
		for (int i = 5; i > 0; i--) {

			gl.glBegin(GL.GL_POLYGON);

			// get color intensity
			float intensity = intensities[i] + (intDiff * (i + 1));

			// set color for hexagon
			if (color.equals("red")) {
				gl.glColor3f(intensity, 0.0f, 0.0f);
			} else if (color.equals("green")) {
				gl.glColor3f(0.0f, intensity, 0.0f);
			} else if (color.equals("blue")) {
				gl.glColor3f(0.0f, 0.0f, intensity);
			} // end if

			// display new intensity values
			if (dispIntense) {
				dispIntense = false;
				for (int j = 5; j > 0; j--) {
					System.out.println(Integer.toString(j)
							+ ": "
							+ Float.toString(intensities[j]
									+ (intDiff * (j + 1))));
				} // end for
				System.out.println("========");
			} // end if

			// get distance of radius and the corresponding dx,dy for 60 deg
			// from center d1 distance away
			float d1 = 10 * (i + 1);
			float d1x = d1 * 0.5f; // cos(60)
			float d1y = d1 * 0.866025404f; // sin(60)

			// draw hexagon
			gl.glVertex2f(this.center.x + d1, this.center.y);
			gl.glVertex2f(this.center.x + d1x, this.center.y + d1y);
			gl.glVertex2f(this.center.x - d1x, this.center.y + d1y);
			gl.glVertex2f(this.center.x - d1, this.center.y);
			gl.glVertex2f(this.center.x - d1x, this.center.y - d1y);
			gl.glVertex2f(this.center.x + d1x, this.center.y - d1y);
			gl.glEnd();

			// force any buffered calls to actually be executed
			gl.glFlush();

		} // end for

		// draw hexagon lines if toggle for lines is on
		if (drawLines) {

			for (int i = 0; i < 6; i++) {

				gl.glBegin(GL.GL_LINE_LOOP);
				gl.glColor3f(0, 0, 0);

				// get distance of radius and the corresponding dx,dy for 60 deg
				// from center d1 distance away
				float d1 = 10 * (i + 1);
				float d1x = d1 * 0.5f; // cos(60)
				float d1y = d1 * 0.866025404f; // sin(60)

				// draw hexagon lines
				gl.glVertex2f(this.center.x + d1, this.center.y);
				gl.glVertex2f(this.center.x + d1x, this.center.y + d1y);
				gl.glVertex2f(this.center.x - d1x, this.center.y + d1y);
				gl.glVertex2f(this.center.x - d1, this.center.y);
				gl.glVertex2f(this.center.x - d1x, this.center.y - d1y);
				gl.glVertex2f(this.center.x + d1x, this.center.y - d1y);
				gl.glEnd();

				// force any buffered calls to actually be executed
				gl.glFlush();

			} // end for

		} // end if

	}// end display()

	/**
	 * This method is called when the window is resized.
	 * 
	 * @param drawable
	 *            The GLCanvas that will be drawn to
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
	} // end reshape()

	/**
	 * Called by the drawable when the display mode or the display device
	 * associated with the GLDrawable has changed.
	 * 
	 * @param drawable
	 *            The GLCanvas that will be drawn to
	 * @param modeChanged
	 *            not implemented
	 * @param deviceChanged
	 *            not implemented
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	} // end displayChanged()

	// Keyboard Events
	public void keyReleased(KeyEvent ke) {
	} // end keyReleased()

	public void keyPressed(KeyEvent ke) {
	} // end keyPressed()

	public void keyTyped(KeyEvent ke) {
		char ch = ke.getKeyChar();
		switch (ch) {
		case 'Q':
		case 'q':
			// quit program if user types Q or q
			System.exit(0);
			break;
		case 'R':
		case 'r':
			// change color to red if user types R or r
			color = "red";
			break;
		case 'G':
		case 'g':
			// change color to green if user types G or g
			color = "green";
			break;
		case 'B':
		case 'b':
			// change color to blue if user types B or b
			color = "blue";
			break;
		case 'L':
		case 'l':
			// toggle drawing lines on/off if user types L or l
			drawLines = !drawLines;
			break;
		case '+':
			// increase color intensities if user types +
			dispIntense = true;
			intDiff += 0.01f;
			break;
		case '-':
			// decrease color intensities if user types -
			dispIntense = true;
			intDiff -= 0.01f;
			break;
		} // end switch
	} // end keyTyped()

	// Mouse Events
	public void mouseReleased(MouseEvent me) {
	} // end mouseReleased()

	public void mouseEntered(MouseEvent me) {
	} // end mouseEntered()

	public void mouseExited(MouseEvent me) {
	} // end mouseExited()

	public void mouseClicked(MouseEvent me) {
	} // end mouseClicked()

	public void mousePressed(MouseEvent me) {
	} // end mousePressed()

	// Menu Option Events
	public void actionPerformed(ActionEvent ae) {
		if ("Quit".equals(ae.getActionCommand()))
			System.exit(0);
	} // end actionPerformed()

} // end class

