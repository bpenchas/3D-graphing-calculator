
public enum Value implements Element {
	CONST, X, Y;
	
	public double constValue = 0;
	public static double xValue = 1;
	public static double yValue = 1;
	
	public double getValue() {
		switch(this) {
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
			Value newValue = CONST;
			newValue.constValue = Double.parseDouble(token);
			return newValue;
		} else if (token.matches("[xX]")) {
			return X;
		} else if (token.matches("[yY]")) {
			return Y;
		} else {
			return null;
		}
	}
	
	public String toString() {
		switch(this) {
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
