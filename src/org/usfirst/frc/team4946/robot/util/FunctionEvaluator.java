package org.usfirst.frc.team4946.robot.util;
import java.util.regex.Pattern;

/**
 * An engine to evaluate a function in terms of {@code x} following appropriate
 * order of operations. Available operators are +,-,*,/,^,().
 * <p>
 * Formula can either be piecewise or continuous. Piecewise functions should be
 * in the form:
 * <li><code>{condition : value, condition: value, etc}</code></li>
 * <li><code>Eg {x<5 : x^2-(x+6), x>=5 : x*-2}</code></li>
 * 
 * 
 * @author Matthew Reynolds
 *
 */
public class FunctionEvaluator {
	private String m_formula;

	// TODO: Improve negative number recognition

	/**
	 * Create a function with the specified formula
	 * 
	 * @param formula
	 *            the formula
	 */
	public FunctionEvaluator(String formula) {
		m_formula = formula;

		if (formula.length() < 1)
			throw new IllegalArgumentException("Formula is malformed");

		// TODO: Proper formula validation
		if (Pattern.compile("[^0-9{}()<>=+\\-*\\/\\^x. ]").matcher(formula).find())
			throw new IllegalArgumentException("Formula '" + formula + "' is malformed, contains illegal characters");
	}

	/**
	 * Evaluate the function with the specified input
	 * 
	 * @param x
	 *            the input
	 * @return the evaluated result
	 */
	public double f(double x) {
		m_formula = m_formula.replaceAll("x", "" + x);

		if (m_formula.trim().charAt(0) != '{')
			return evaluate(m_formula.replaceAll("[\\s]+", ""));

		String[] pieces = m_formula.replaceAll("[\\s{}]+", "").split(",");
		for (String piece : pieces) {
			if (condition(piece.split(":")[0])) {
				return evaluate(piece.split(":")[1]);
			}
		}

		throw new IllegalArgumentException("Formula is invalid or undefined at x=" + x);
	}

	/**
	 * @param f
	 *            the new formula to set
	 */
	public void setFormula(String f) {
		m_formula = f;
	}

	/**
	 * @return the formula
	 */
	public String getFormula() {
		return m_formula;
	}

	/**
	 * Evaluate the condition specified by f
	 * 
	 * @param f
	 *            the comparison to check
	 * @return the result
	 */
	private boolean condition(String f) {
		// System.out.println("Condition: " + f);

		double lOperand = Double.parseDouble(nextTerm(f, 0));
		if (f.contains("<=")) {
			double rOperand = Double.parseDouble(f.substring(f.indexOf("=") + 1));
			return lOperand <= rOperand;
		} else if (f.contains("<")) {
			double rOperand = Double.parseDouble(f.substring(f.indexOf("<") + 1));
			return lOperand < rOperand;
		} else if (f.contains(">=")) {
			double rOperand = Double.parseDouble(f.substring(f.indexOf("=") + 1));
			return lOperand >= rOperand;
		} else if (f.contains(">")) {
			double rOperand = Double.parseDouble(f.substring(f.indexOf(">") + 1));
			return lOperand > rOperand;
		}

		throw new IllegalArgumentException("Malformed condition " + f);
	}

	/**
	 * Evaluate the formula specified by f
	 * 
	 * @param f
	 *            the formula to evaluate
	 * @return the result
	 */
	private double evaluate(String f) {

		// System.out.println("Evaluate: " + f);

		// Brackets
		int depth = 0;
		int openBracketIndex = 0;
		for (int i = 0; i < f.length(); i++) {
			if (f.charAt(i) == '(') {
				depth++;
				if (depth == 1)
					openBracketIndex = i;
			}
			if (f.charAt(i) == ')') {
				depth--;
				if (depth == 0) {
					String term = f.substring(openBracketIndex + 1, i);
					f = f.substring(0, openBracketIndex) + evaluate(term) + f.substring(i + 1);
					i = 0;
				}
			}
		}

		// Exponents
		int termStart = 0;
		for (int i = 0; i < f.length(); i++) {
			if (f.charAt(i) == '^') {
				double lOperand = Double.parseDouble(f.substring(termStart, i));
				double rOperand = Double.parseDouble(nextTerm(f, i + 1));
				f = f.substring(0, termStart) + Math.pow(lOperand, rOperand)
						+ f.substring(i + 1 + nextOperatorIndex(f.substring(i + 1)));
				termStart = i = 0;
			}

			else if (isOperator(f.charAt(i)))
				if (i != 0 && !isOperator(f.charAt(i - 1)))
					termStart = i + 1;
		}

		// Multiplication and Division
		termStart = 0;
		for (

				int i = 0; i < f.length(); i++) {
			if (f.charAt(i) == '*') {
				double lOperand = Double.parseDouble(f.substring(termStart, i));
				double rOperand = Double.parseDouble(nextTerm(f, i + 1));
				f = f.substring(0, termStart) + (lOperand * rOperand)
						+ f.substring(i + 1 + nextOperatorIndex(f.substring(i + 1)));
				termStart = i = 0;
			}

			else if (f.charAt(i) == '/') {
				double lOperand = Double.parseDouble(f.substring(termStart, i));
				double rOperand = Double.parseDouble(nextTerm(f, i + 1));
				f = f.substring(0, termStart) + (lOperand / rOperand)
						+ f.substring(i + 1 + nextOperatorIndex(f.substring(i + 1)));
				termStart = i = 0;
			}

			else if (isOperator(f.charAt(i)))
				if (i != 0 && !isOperator(f.charAt(i - 1)))
					termStart = i + 1;
		}

		// Addition & Substraction
		termStart = 0;
		for (int i = 0; i < f.length(); i++) {
			if (f.charAt(i) == '+') {
				double lOperand = Double.parseDouble(f.substring(termStart, i));
				double rOperand = Double.parseDouble(nextTerm(f, i + 1));
				f = f.substring(0, termStart) + (lOperand + rOperand)
						+ f.substring(i + 1 + nextOperatorIndex(f.substring(i + 1)));
				termStart = i = 0;
			}

			else if (f.charAt(i) == '-') {
				if (i == 0 || isOperator(f.charAt(i - 1))) {
					termStart = i;
					continue;
				}
				double lOperand = Double.parseDouble(f.substring(termStart, i));
				double rOperand = Double.parseDouble(nextTerm(f, i + 1));
				f = f.substring(0, termStart) + (lOperand - rOperand)
						+ f.substring(i + 1 + nextOperatorIndex(f.substring(i + 1)));
				termStart = i = 0;
			}
		}

		try {
			return Double.parseDouble(f);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Malformed formula " + f);
		}
	}

	/**
	 * Get the next term in the formula
	 * 
	 * @param f
	 *            the formula
	 * @param start
	 *            the starting point to search from (Inclusive)
	 * @return the next term
	 */
	private String nextTerm(String f, int start) {
		return f.substring(start, nextOperatorIndex(f.substring(start)) + start);
	}

	/**
	 * Find the index of the next operator in the formula
	 * 
	 * @param f
	 *            the formula
	 * @return the index
	 */
	private int nextOperatorIndex(String f) {
		int operator = f.length();

		if (f.contains("+"))
			operator = Math.min(f.indexOf('+'), operator);
		if (f.contains("-")) {

			if (f.startsWith("-")) {
				if (f.substring(1).contains("-"))
					operator = Math.min(f.substring(1).indexOf('-') + 1, operator);
			} else
				operator = Math.min(f.indexOf('-'), operator);
		}
		if (f.contains("*"))
			operator = Math.min(f.indexOf('*'), operator);
		if (f.contains("/"))
			operator = Math.min(f.indexOf('/'), operator);
		if (f.contains("^"))
			operator = Math.min(f.indexOf('^'), operator);
		if (f.contains("<"))
			operator = Math.min(f.indexOf('<'), operator);
		if (f.contains(">"))
			operator = Math.min(f.indexOf('>'), operator);
		if (f.contains("="))
			operator = Math.min(f.indexOf('='), operator);
		return operator;
	}

	/**
	 * Check whether the specified characters is one of the known operators
	 * 
	 * @param c
	 *            the character to check
	 * @return {@code true} if the char is an operator
	 */
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '<' || c == '>' || c == '=';
	}
}
