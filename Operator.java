
public enum Operator {
	ADD, SUBTRACT, MULTIPLY, DIVIDE, EXPONENT, OPAREN, CPAREN
	
	public int precedence(String operator) {
		switch(operator) {
			case "+":
				return 1;
			case "-":
				return 1;
			case "*":
				return 2;
			case "/":
				return 2;
			case "^":
				return 3;
			default:
				return 0;
				
		}
	}
}
