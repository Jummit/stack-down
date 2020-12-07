package com.jummit.stackmodify.util;

public class StringOperaterUtils {
	
	public static int calculate(int a, int b, String operator) {
		switch (operator) {
			case "=":
				return b;
			case "*":
				return a * b;
			case "+":
				return a + b;
			case "-":
				return a - b;
			case "/":
				return a / b;
			
			default:
				return 0;
		}
	}
}
