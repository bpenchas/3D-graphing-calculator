
public enum Operator {
	ADD, SUBTRACT, MULTIPLY, DIVIDE, EXPONENT, OPAREN, CPAREN, NOOP;
	
	public int stackPrecedence() {
		switch(this) {
			case ADD:
				return 2;
			case SUBTRACT:
				return 2;
			case MULTIPLY:
				return 4;
			case DIVIDE:
				return 4;
			case EXPONENT:
				return 5;
			case OPAREN:
				return 0;
			case CPAREN:
				return 99;
			default:
				return 0;
				
		}
	}
	
	public int inputPrecedence() {
		switch(this) {
			case ADD:
				return 1;
			case SUBTRACT:
				return 1;
			case MULTIPLY:
				return 3;
			case DIVIDE:
				return 3;
			case EXPONENT:
				return 6;
			case OPAREN:
				return 100;
			case CPAREN:
				return 0;
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
