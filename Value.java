
public enum Value {
	CONST, X, Y;
	
	public double constValue = 0;
	
	
	public Value fromString(String token) {
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

}
