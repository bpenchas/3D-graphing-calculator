/*
 * File: GraphicsContest.java
 * 
 * Author: Ben Penchas
 * 
 * Section Leader: Kat Gregory
 * 
 * This file implements a 3D Graphing Calculator.
 * 
 * Interesting/difficult features:
 * 
 * 1. I wrote my own parser for expressions entered into the dialog box.
 * 	This parser first converts the expression to reverse polish notation
 * and then evaluates the expression for a variety of x and y values.
 * 
 * 2. The parser can handle parentheses.
 * 
 * 3. The sense of 3D is created using projection matrices.
 * 
 * 4. The user can explore the 3D graph, a result of constantly applied rotation matrices.
 * 
 * 5. The user can zoom in and out of the graph.
 * 
 * 

 * --------------------------
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JTextField;

import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.io.IODialog;
import acm.program.GraphicsProgram;

public class GraphicsContest extends GraphicsProgram {
	
	public void init() {
		addMouseListeners();	
		mainGraphicsContest = this;
		zoomInButton = new JButton("Zoom Out");
		zoomOutButton = new JButton("Zoom In");
		resolutionUpButton = new JButton("Increase Resoltuion");
		add(zoomInButton, NORTH);
		add(zoomOutButton, NORTH);
		add(resolutionUpButton, SOUTH);
		addActionListeners();
	}
	
	public static GraphicsContest mainGraphicsContest;
	
	private JTextField expressionField;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton resolutionUpButton;
	
	private int resolution = 100;
	private String equation = "";
	
	private LinkedList<Element> result = new LinkedList<Element>();
	private Point3D[][] pointArray = new Point3D[resolution + 1][resolution + 1];
	private Line3D[][][] lineArray = new Line3D[resolution][resolution][2];
	
	private int oldMouseX;
	private int oldMouseY;
	
	public double theta = 0;
	public double phi = 0;
	
	public int colorCounter = 100;
	
	private String DELIMITERS = "+-*/^()xXyY";
	
	public void mousePressed(MouseEvent e) {
		oldMouseX = e.getX();
		oldMouseY = e.getY();
	}
	
	public void mouseDragged(MouseEvent e) {
		theta -= (e.getX() - oldMouseX) / 85.0;
		phi += (e.getY() - oldMouseY) / 85.0;
		oldMouseX = e.getX();
		oldMouseY = e.getY();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == zoomInButton) {
			Point3D.cameraToOrigin += 10;
		} else if (e.getSource() == zoomOutButton) {
			if (Point3D.cameraToOrigin > 10) Point3D.cameraToOrigin += -10;
		} else if (e.getSource() == resolutionUpButton) {
			resolution += 10;
		}
	}
	
	public void run() {
		Point3D z = new Point3D(0, 0, 10);
		Point3D y = new Point3D(0, 10, 0);
		Point3D x = new Point3D(10, 0, 0);
		Point3D origin = new Point3D(0, 0, 0);
		
		Line3D xAxis = new Line3D(origin, x, colorCounter);
		Line3D yAxis = new Line3D(origin, y, colorCounter);
		Line3D zAxis = new Line3D(origin, z, colorCounter);
		
		add(xAxis.to2D());
		add(yAxis.to2D());
		add(zAxis.to2D());
		
		try {
			convertToPostfix();
			addMesh();
			
			while(true) {
				xAxis.rotate(theta, phi);
				yAxis.rotate(theta, phi);
				zAxis.rotate(theta, phi);		
				for (int i = 0; i < resolution; i ++) {
					for (int j = 0; j < resolution; j ++) {
						lineArray[i][j][0].rotate(theta, phi);
						lineArray[i][j][1].rotate(theta, phi);
					}
				}
				try {
					Thread.sleep(24);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	private void convertToPostfix() {
		IODialog dialog = getDialog();
//		dialog.println("Welcome to the 3D Graphing Calculator!");
//		dialog.print("1. Enter an equation in terms of X and Y ( Z = your equation)");
//		dialog.print("2. The following operators are permitted: ^, *, /, + -");
//		dialog.print("3. The function will be drawn in 3D. Click and drag to explore the function in 3D");
//		dialog.print("4. Use the zoom buttons to change the viewing distance from the graph");
		String equation = dialog.readLine("Z = ");
		
		StringTokenizer st = new StringTokenizer(equation, DELIMITERS, true);
		Stack<Operator> operators = new Stack<Operator>();
		boolean wasNum = false;
		boolean nextIsNeg = false;
		Value negOne = Value.makeConstValue(-1);
		
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
//			token.trim();
//			if(token == "") token = st.nextToken();
			Operator currentOperator = Operator.fromString(token);
			if (currentOperator == Operator.NOOP) {
				Value temp = Value.fromString(token);
				result.add(temp);
				if (temp.state == Value.ValueEnum.X || temp.state == Value.ValueEnum.Y) {
					if(wasNum) result.add(Operator.MULTIPLY);
				}
				if (nextIsNeg) {
					result.add(negOne);
					result.add(Operator.MULTIPLY);
					nextIsNeg = false;
				}
				wasNum = true;
			}
			 else {
				if (currentOperator == Operator.SUBTRACT && !wasNum) {
					nextIsNeg = true;
				} else {
					if (operators.isEmpty() || operators.peek().stackPrecedence() < currentOperator.inputPrecedence()) {
						operators.push(currentOperator);
					} else {
						while (!operators.isEmpty() && operators.peek().stackPrecedence() >= currentOperator.inputPrecedence()) {
							result.add(operators.pop());
						}
						operators.push(currentOperator);
					}
				}
				wasNum = false;
			}
		}
		
		while (!operators.isEmpty()) {
			result.add(operators.pop());
		}
		
	}
	
	private double evaluatePostfix(LinkedList<Element> expression) {
		Stack<Double> value = new Stack<Double>();
		Iterator iterator = expression.iterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			if (element.getClass() == Value.class) {
				value.push(((Value) element).getValue());
			} else {
				((Operator) element).apply(value);
			}
		}
		return value.pop();
	}
	
	private void addMesh() {
		for (int i = 0; i < pointArray.length; i ++) {
			for (int j = 0; j < pointArray.length; j ++) {
				
				Value.xValue = 20 * (i / (pointArray.length - 1.0)) - 10;
				Value.yValue = 20 * (j / (pointArray.length - 1.0)) - 10;
				pointArray[i][j] = new Point3D(Value.xValue, evaluatePostfix(result), Value.yValue);
			}
		}
		
		for (int i = 0; i < resolution; i ++) {
			if(colorCounter < 250) colorCounter++;
			for (int j = 0; j < resolution; j ++) {
				
				lineArray[i][j][0] = new Line3D(pointArray[i][j], pointArray[i][j + 1], colorCounter);
				lineArray[i][j][1] = new Line3D(pointArray[i][j], pointArray[i + 1][j], colorCounter);
				Color current = new Color(colorCounter, colorCounter, colorCounter);
				lineArray[i][j][0].to2D().setColor(current);
				lineArray[i][j][1].to2D().setColor(current);
				add(lineArray[i][j][0].to2D());
				add(lineArray[i][j][1].to2D());
				
			}
		}
		
		
	}
	
	
	

}
