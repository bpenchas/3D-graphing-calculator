
public enum Operator {
	ADD, SUBTRACT, MULTIPLY, DIVIDE, EXPONENT, OPAREN, CPAREN, NOOP;
	
	public int precedence() {
		switch(this) {
			case ADD:
				return 1;
			case SUBTRACT:
				return 1;
			case MULTIPLY:
				return 2;
			case DIVIDE:
				return 2;
			case EXPONENT:
				return 3;
			case OPAREN:
				return 4;
			case CPAREN:
				return 5;
			default:
				return 0;
				
		}
	}
	
	public static Operator fromString(String operator) {
		switch(operator) {
		case "+":
			return ADD;
		case "-":
			return SUBTRACT;
		case "*":
			return MULTIPLY;
		case "/":
			return DIVIDE;
		case "^":
			return EXPONENT;
		case "(":
			return OPAREN;
		case ")":
			return CPAREN;
		default:
			return NOOP;
			
	}
		
	}
}
