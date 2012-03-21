/**

  Hexagons.java

  This class has the main method and sets up the window and
  does some initialization.  The bulk of the work is done in
  DrawAndHandleInput.java
  
  =========================================================

  Adam Steinberger
  Skidmore College

  =========================================================

  This is a program that
  	- draws six hexagons at different intensities

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
  University.  Mike Eckmann based some of this code on the
  code found in JOGL: A Beginner's Guide and Tutorial
  By Kevin Conroy
  http://www.cs.umd.edu/~meesh/kmconroy/JOGLTutorial/

  =========================================================

  @author	
  	Adam Steinberger steinz08@gmail.com

 */

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.sun.opengl.util.Animator;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

public class Hexagons {

	private static DrawAndHandleInput dahi;
	public static Frame testFrame;

	public static void main(String[] args) {

		// Create the Frame
		testFrame = new Frame("Hexagons");

		// set the coordinates on the screen of the upper left corner of the
		// window, so the window will start off at 10,10 (near the upper left
		// corner of the screen)
		testFrame.setLocation(10, 10);

		// set the window to be 400x400 pixels
		testFrame.setSize(410, 452);

		// This allows us to define some attributes
		// about the capabilities of GL for this program
		// such as color depth, and whether double buffering is
		// used.
		GLCapabilities glCapabilities = new GLCapabilities();
		glCapabilities.setRedBits(8);
		glCapabilities.setGreenBits(8);
		glCapabilities.setBlueBits(8);
		glCapabilities.setAlphaBits(8);

		// create the GLCanvas that is to be added to our Frame
		GLCanvas canvas = new GLCanvas(glCapabilities);
		testFrame.add(canvas);

		// create the Animator and attach the GLCanvas to it
		Animator a = new Animator(canvas);

		// create an instance of the Class that listens to all events
		// (GLEvents, Keyboard, Mouse and Menu events)
		// add this object as all these listeners to the canvas and menu
		dahi = new DrawAndHandleInput();
		canvas.addGLEventListener(dahi);
		canvas.addKeyListener(dahi);
		canvas.addMouseListener(dahi);

		// code to set up a menu and add it to our frame
		// and register an event listener to it
		Menu menu = new Menu("File");
		MenuItem mu1 = new MenuItem("Quit");
		mu1.addActionListener(dahi);
		menu.add(mu1);
		MenuBar menuBar = new MenuBar();
		menuBar.add(menu);
		testFrame.setMenuBar(menuBar);

		// if user closes the window by clicking on the X in
		// upper right corner
		testFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			} // end windowClosing()
		});

		testFrame.setVisible(true);
		a.start(); // start the Animator, which periodically calls display() on
					// the GLCanvas

	} // end main()
	
} // end class
