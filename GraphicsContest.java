/*
 * File: GraphicsContest.java

 * --------------------------
 */

import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.io.IODialog;
import acm.program.GraphicsProgram;

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
	
	private LinkedList<Element> result = new LinkedList<Element>();
	
	
	private void evaluate() {
		IODialog dialog = getDialog();
		String equation = dialog.readLine("Enter an equation:");
		
		StringTokenizer st = new StringTokenizer(equation, DELIMITERS, true);
		
		Stack<Operator> operators = new Stack<Operator>();
		boolean wasNum = false;
		boolean nextIsNeg = false;
		Value negOne = Value.makeConstValue(-1);
		while(st.hasMoreTokens()) {
			
			String token = st.nextToken();
			//System.out.println(token);
			Operator currentOperator = Operator.fromString(token);
			
			if (currentOperator == Operator.NOOP) {
				wasNum = true;
				result.add(Value.fromString(token));
				if (nextIsNeg) {
					result.add(negOne);
					result.add(Operator.MULTIPLY);
					nextIsNeg = false;
				}
				
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
		Iterator iterator = result.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	private void evaluatePostfix (LinkedList<Element> expression) {
		
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
		System.out.println(value.pop());
		
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
		evaluatePostfix(result);
		
		
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
