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
import acm.graphics.GPolygon;
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
	
	
	private Point3D[][] pointArray = new Point3D[21][21];
	private Rect3D[][] rectArray = new Rect3D[20][20];
	
	public void run() {
		Point3D z = new Point3D(0, 0, 10);
		Point3D y = new Point3D(0, 10, 0);
		Point3D x = new Point3D(10, 0, 0);
		Point3D origin = new Point3D(0, 0, 0);
		
		Line3D xAxis = new Line3D(origin, x);
		Line3D yAxis = new Line3D(origin, y);
		Line3D zAxis = new Line3D(origin, z);
		
//		Line3D test = new Line3D(x, z);
//		Line3D test2 = new Line3D(y, z);
//		Line3D test3 = new Line3D(x, y);
		
		evaluate();
		for (int i = 0; i <= pointArray.length; i ++) {
			for (int j = 0; j <= pointArray.length; j ++) {
				Value.xValue = 20 * (i / (pointArray.length - 1)) - 10;
				Value.yValue = 20 * (j / (pointArray.length - 1)) - 10;
				pointArray[i][j] = new Point3D(Value.xValue, Value.yValue, evaluatePostfix(result));
			}
		}
		
		for (int i = 0; i <= rectArray.length; i ++) {
			for (int j = 0; j <= rectArray.length; j ++) {
				rectArray[i][j] = new Rect3D(pointArray[i][j], pointArray[i+1][j], pointArray[i+1][j+1], pointArray[i][j+1]);
			}
		}
		
		
		while(true) {
			
			removeAll();
			add(toPixel(xAxis.rotate(theta, phi).to2D()));
			add(toPixel(yAxis.rotate(theta, phi).to2D()));
			add(toPixel(zAxis.rotate(theta, phi).to2D()));
			
			for (int i = 0; i <= rectArray.length; i ++) {
				for (int j = 0; j <= rectArray.length; j ++) {
					add(toPixel(rectArray[i][j].rotate(theta, phi).to2D()));
				}
			}
			
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
	

}
