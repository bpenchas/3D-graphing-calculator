
public class Value implements Element {
	
	public enum ValueEnum {
		CONST, X, Y;
	}
	
	public ValueEnum state = ValueEnum.CONST;
	private double constValue = 0;
	
	public static double xValue = 1;
	public static double yValue = 1;
	
	public double getValue() {
		switch(state) {
		case CONST:
			return constValue;
		case X:
			return xValue;
		case Y:
			return yValue;
		default:
			return 0.0;
			
		}
	}
	
	public static Value fromString(String token) {
		if (token.matches("[0-9]+")) {
			return makeConstValue(Double.parseDouble(token));
		} else if (token.matches("[xX]")) {
			return xValue();
		} else if (token.matches("[yY]")) {
			return yValue();
		} else {
			return null;
		}
	}
	
	public static Value makeConstValue(double input) {
		Value value = new Value();
		value.constValue = input;
		value.state = ValueEnum.CONST;
		return value;
		
	}
	

	private static Value xValue() {
		Value value = new Value();
		value.state = ValueEnum.X;
		return value;
		
	}
	private static Value yValue() {
		Value value = new Value();
		value.state = ValueEnum.Y;
		return value;
		
	}

	
	public String toString() {
		switch(state) {
		case CONST:
			return "CONST: " + Double.toString(constValue);
		case X:
			return "X: " + Double.toString(xValue);
		case Y:
			return "Y: " + Double.toString(yValue);
		default:
			return "";
			
		}
	}

}
