/*
 * File: GraphicsContest.java

 * --------------------------
 */

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.io.IODialog;
import acm.program.GraphicsProgram;

public class GraphicsContest extends GraphicsProgram {
	
	public void init() {
		addMouseListeners();
		
		mainGraphicsContest = this;
		
//		expressionField = new JTextField(10);
//		add(new JLabel("Zoom Out"), NORTH);
//		add(expressionField, NORTH);
//		expressionField.addActionListener(this);
		addButton = new JButton("Zoom Out");
		clearButton = new JButton("Zoom In");
		add(addButton, NORTH);
		add(clearButton, NORTH);
		addActionListeners();
	}
	
	private JTextField expressionField;
	private JButton addButton;
	private JButton clearButton;
	
	public static GraphicsContest mainGraphicsContest;
	
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
	
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == expressionField) {
//			equation = expressionField.getText();
//		} else if (e.getSource() == clearButton) {
//			equation = "";
//		} else
		if (e.getSource() == addButton) {
			Point3D.cameraToOrigin += 10;
		} else if (e.getSource() == clearButton) {
			Point3D.cameraToOrigin += -10;
		}
	}
	
	private String equation = "";
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
				Value temp = Value.fromString(token);
				result.add(temp);
				if (temp.state == Value.ValueEnum.X || temp.state == Value.ValueEnum.Y) {
					if(wasNum) result.add(Operator.MULTIPLY);
				} else {
					wasNum = true;
				}
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
	
	
	
	private static final int resolution = 100;
	private Point3D[][] pointArray = new Point3D[resolution + 1][resolution + 1];
	private Line3D[][][] lineArray = new Line3D[resolution][resolution][2];
	
//	private Line3D xAxis;
//	private Line3D yAxis;
//	private Line3D zAxis;
	
	public void run() {
		
		Point3D z = new Point3D(0, 0, 10);
		Point3D y = new Point3D(0, 10, 0);
		Point3D x = new Point3D(10, 0, 0);
		Point3D origin = new Point3D(0, 0, 0);
		
		Line3D xAxis = new Line3D(origin, x);
		Line3D yAxis = new Line3D(origin, y);
		Line3D zAxis = new Line3D(origin, z);
		
		add(xAxis.to2D());
		add(yAxis.to2D());
		add(zAxis.to2D());
		
		evaluate();
		for (int i = 0; i < pointArray.length; i ++) {
			for (int j = 0; j < pointArray.length; j ++) {
				Value.xValue = 20 * (i / (pointArray.length - 1.0)) - 10;
				Value.yValue = 20 * (j / (pointArray.length - 1.0)) - 10;
				pointArray[i][j] = new Point3D(Value.xValue, evaluatePostfix(result), Value.yValue);
			}
		}
		
		for (int i = 0; i < resolution; i ++) {
			for (int j = 0; j < resolution; j ++) {
				lineArray[i][j][0] = new Line3D(pointArray[i][j], pointArray[i][j + 1]);
				lineArray[i][j][1] = new Line3D(pointArray[i][j], pointArray[i + 1][j]);
				add(lineArray[i][j][0].to2D());
				add(lineArray[i][j][1].to2D());
			}
		}
		
		
		
		while(true) {
			
//				Point3D z = new Point3D(0, 0, 10);
//				Point3D y = new Point3D(0, 10, 0);
//				Point3D x = new Point3D(10, 0, 0);
//				Point3D origin = new Point3D(0, 0, 0);
//				
//				Line3D xAxis = new Line3D(origin, x);
//				Line3D yAxis = new Line3D(origin, y);
//				Line3D zAxis = new Line3D(origin, z);
			
			//removeAll();
			xAxis.rotate(theta, phi);
			yAxis.rotate(theta, phi);
			zAxis.rotate(theta, phi);
//			add(xAxis.rotate(theta, phi).to2D());
//			add(yAxis.rotate(theta, phi).to2D());
//			add(zAxis.rotate(theta, phi).to2D());
			
//				xAxis.to2D().setStartPoint(xAxis.rotate(theta, phi).to2D().getStartPoint().getX(), xAxis.rotate(theta, phi).to2D().getStartPoint().getY());
//				xAxis.to2D().setEndPoint(xAxis.rotate(theta, phi).to2D().getEndPoint().getX(), xAxis.rotate(theta, phi).to2D().getEndPoint().getY());
//				
//				yAxis.to2D().setStartPoint(yAxis.rotate(theta, phi).to2D().getStartPoint().getX(), yAxis.rotate(theta, phi).to2D().getStartPoint().getY());
//				yAxis.to2D().setEndPoint(yAxis.rotate(theta, phi).to2D().getEndPoint().getX(), yAxis.rotate(theta, phi).to2D().getEndPoint().getY());
//				
//				zAxis.to2D().setStartPoint(zAxis.rotate(theta, phi).to2D().getStartPoint().getX(), zAxis.rotate(theta, phi).to2D().getStartPoint().getY());
//				zAxis.to2D().setEndPoint(zAxis.rotate(theta, phi).to2D().getEndPoint().getX(), zAxis.rotate(theta, phi).to2D().getEndPoint().getY());
//				
			
			for (int i = 0; i < resolution; i ++) {
				for (int j = 0; j < resolution; j ++) {
					lineArray[i][j][0].rotate(theta, phi);
					lineArray[i][j][1].rotate(theta, phi);
				}
			}
			
			
			try {
				Thread.sleep(24);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		Line3D test = new Line3D(x, z);
//		Line3D test2 = new Line3D(y, z);
//		Line3D test3 = new Line3D(x, y);
//		while (true) {
//			if (equation == "") {
//				evaluate();
//				for (int i = 0; i < pointArray.length; i ++) {
//					for (int j = 0; j < pointArray.length; j ++) {
//						Value.xValue = 20 * (i / (pointArray.length - 1.0)) - 10;
//						Value.yValue = 20 * (j / (pointArray.length - 1.0)) - 10;
//						pointArray[i][j] = new Point3D(Value.xValue, evaluatePostfix(result), Value.yValue);
//						
//					}
//				}
//				
//				for (int i = 0; i < rectArray.length; i ++) {
//					for (int j = 0; j < rectArray.length; j ++) {
//						rectArray[i][j] = new Rect3D(pointArray[i][j], pointArray[i+1][j], pointArray[i+1][j+1], pointArray[i][j+1]);
//					}
//				}
//			
//
//			
//			
//			
//				while(true) {
//					
//	//				Point3D z = new Point3D(0, 0, 10);
//	//				Point3D y = new Point3D(0, 10, 0);
//	//				Point3D x = new Point3D(10, 0, 0);
//	//				Point3D origin = new Point3D(0, 0, 0);
//	//				
//	//				Line3D xAxis = new Line3D(origin, x);
//	//				Line3D yAxis = new Line3D(origin, y);
//	//				Line3D zAxis = new Line3D(origin, z);
//					
//					removeAll();
//					add(xAxis.rotate(theta, phi).to2D());
//					add(yAxis.rotate(theta, phi).to2D());
//					add(zAxis.rotate(theta, phi).to2D());
//					
//	//				xAxis.to2D().setStartPoint(xAxis.rotate(theta, phi).to2D().getStartPoint().getX(), xAxis.rotate(theta, phi).to2D().getStartPoint().getY());
//	//				xAxis.to2D().setEndPoint(xAxis.rotate(theta, phi).to2D().getEndPoint().getX(), xAxis.rotate(theta, phi).to2D().getEndPoint().getY());
//	//				
//	//				yAxis.to2D().setStartPoint(yAxis.rotate(theta, phi).to2D().getStartPoint().getX(), yAxis.rotate(theta, phi).to2D().getStartPoint().getY());
//	//				yAxis.to2D().setEndPoint(yAxis.rotate(theta, phi).to2D().getEndPoint().getX(), yAxis.rotate(theta, phi).to2D().getEndPoint().getY());
//	//				
//	//				zAxis.to2D().setStartPoint(zAxis.rotate(theta, phi).to2D().getStartPoint().getX(), zAxis.rotate(theta, phi).to2D().getStartPoint().getY());
//	//				zAxis.to2D().setEndPoint(zAxis.rotate(theta, phi).to2D().getEndPoint().getX(), zAxis.rotate(theta, phi).to2D().getEndPoint().getY());
//	//				
//					
//					for (int i = 0; i < rectArray.length; i ++) {
//						for (int j = 0; j < rectArray.length; j ++) {
//							GPolygon temp = rectArray[i][j].rotate(theta, phi).to2D();
//							if (temp != null) {
//								add(temp);
//							}
//							
//						}
//					}
//					
//					try {
//						Thread.sleep(24);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
		
			}
		
		
		
		
	
	
	private GLine lineFromPoints(GPoint first, GPoint second) {
		return new GLine(first.getX(), first.getY(), second.getX(), second.getY());
	}
	

}
