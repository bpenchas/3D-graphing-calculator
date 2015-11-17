/*
 * File: GraphicsContest.java

 * --------------------------
 */

import java.awt.event.MouseEvent;

import acm.program.*;
import acm.graphics.*;
import acm.io.IODialog;
import java.util.*;

public class GraphicsContest extends GraphicsProgram {
	
	public void init() {
		addMouseListeners();
	}
	

	private int oldMouseX;
	private int oldMouseY;
	
	public double theta = 0;
	public double phi = 0;
	
	String DELIMITERS = "+-*/^()xXyY";
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
	
	
	private void evaluate() {
		IODialog dialog = getDialog();
		String equation = dialog.readLine("Enter an equation:");
		
		StringTokenizer st = new StringTokenizer(equation, DELIMITERS, true);
		LinkedList result = new LinkedList();
		Stack<Operator> operators = new Stack<Operator>();
		
		while(st.hasMoreTokens()) {
			
			String token = st.nextToken();
			System.out.println(token);
			Operator currentOperator = Operator.fromString(token);
			
			if (token.matches("[0-9]+")) {
				result.add(Integer.parseInt(token));
			} else if (token.matches("[x,X,y,Y]")) {
				 result.add(token);
			} else {
				if (operators.isEmpty() || operators.peek().stackPrecedence() < currentOperator.stackPrecedence()) {
					operators.push(currentOperator);
				} else {
					while (!operators.isEmpty() && operators.peek().stackPrecedence() >= currentOperator.inputPrecedence()) {
						result.add(operators.pop());
					}
					operators.push(currentOperator);
				}
			}
		
		}
		
		while (!operators.isEmpty()) {
			result.add(operators.pop());
		}
		Iterator iterator = result.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	private void evaluatePostfix (LinkedList expression) {
		
		Stack value = new Stack();
		Iterator iterator = expression.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getClass() == Operator()) {
				value.push(iterator.next());
			}
		}
		
	}
	
	private class Line3D {
		private Point3D first;
		private Point3D second;

		private GLine to2D() {
			return lineFromPoints(first.to2D(), second.to2D());
		}
		
		public Line3D(Point3D first, Point3D second) {
			this.first = first;
			this.second = second;
		}
		
		public Line3D rotate(){
			return new Line3D(first.rotate(theta, phi), second.rotate(theta, phi));
		}
	}
	
	public void run() {
		Point3D z = new Point3D(0, 0, 10);
		Point3D y = new Point3D(0, 10, 0);
		Point3D x = new Point3D(10, 0, 0);
		Point3D origin = new Point3D(0, 0, 0);
		
		Line3D xAxis = new Line3D(origin, x);
		Line3D yAxis = new Line3D(origin, y);
		Line3D zAxis = new Line3D(origin, z);
		
		Line3D test = new Line3D(x, z);
		Line3D test2 = new Line3D(y, z);
		Line3D test3 = new Line3D(x, y);
		
		evaluate();
		
		
		while(true) {
			
			removeAll();
			add(toPixel(xAxis.rotate().to2D()));
			add(toPixel(yAxis.rotate().to2D()));
			add(toPixel(zAxis.rotate().to2D()));
			add(toPixel(test.rotate().to2D()));
			add(toPixel(test2.rotate().to2D()));
			add(toPixel(test3.rotate().to2D()));
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	private GLine lineFromPoints(GPoint first, GPoint second) {
		return new GLine(first.getX(), first.getY(), second.getX(), second.getY());
	}
	
	private GPoint toPixel(GPoint pt) {
		return new GPoint((pt.getX() + 10) / 20 * getWidth(), (10 - pt.getY()) / 20 * getHeight());
	}
	
	private GLine toPixel(GLine line) {
		return lineFromPoints(toPixel(line.getStartPoint()), toPixel(line.getEndPoint()));
	}
	

}
