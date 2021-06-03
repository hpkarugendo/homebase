package com.hpkarugendo.projects.calculator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CalculatorLogic {

	private String problem, solution;
	private List<String> list;
	private CalculatorModel temp;

	public CalculatorLogic() {
	}

	public CalculatorLogic(CalculatorModel temp) {
		this.list = new ArrayList<String>();
		this.temp = temp;
		this.problem = temp.getProblem();
		this.solution = "";
	}

	public CalculatorModel calculate() {
		list = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(problem, "+-*/", true);
		while (token.hasMoreTokens()) {
			list.add(token.nextToken().toString());
		}
		
		while(list.size() > 2) {
			int opIndex = 1;
			int valueAindex = opIndex - 1;
			int valueBindex = opIndex + 1;
			String valueAstring = "";
			String valueBstring = "";
			String initialOp = "";
			String op = "";
			float valueA = 0;
			float valueB = 0;
			float result = 0;
			String resultString = "";
			
			
			if (isOperator(list.get(0))) {
				initialOp = list.remove(0);
			}

			if (!initialOp.isEmpty()) {
				valueAstring = initialOp + list.get(valueAindex);
				initialOp = "";
			} else {
				valueAstring = list.get(valueAindex);
			}

			valueBstring = list.get(valueBindex);
			op = list.get(opIndex);
			valueA = Float.parseFloat(valueAstring);
			valueB = Float.parseFloat(valueBstring);

			switch (op) {
			case "*":
				result = (valueA * valueB);
				resultString = String.valueOf(result);
				list.set(valueAindex, resultString);
				list.remove(1);
				list.remove(1);
				break;
			case "/":
				result = (valueA / valueB);
				resultString = String.valueOf(result);
				list.set(valueAindex, resultString);
				list.remove(1);
				list.remove(1);
				break;
			case "+":
				result = (valueA + valueB);
				resultString = String.valueOf(result);
				list.set(valueAindex, resultString);
				list.remove(1);
				list.remove(1);
				break;
			case "-":
				result = (valueA - valueB);
				resultString = String.valueOf(result);
				list.set(valueAindex, resultString);
				list.remove(1);
				list.remove(1);
				break;
			}
		}
		
		float ans = Float.valueOf(list.get(0));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		
		temp.setSolution(nf.format(ans));
		return temp;

	}

	public boolean isZeroDivision() {
		boolean ans = false;
		StringTokenizer token = new StringTokenizer(problem, "+-*/", true);

		while (token.hasMoreTokens()) {
			list.add(token.nextToken().toString());
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals("/") && Float.parseFloat(list.get(i+1)) == 0) {
				ans = true;
			}
		}

		return ans;
	}

	public boolean isValid() {
		boolean ans = true;

		if (problem.trim().length() < 3) {
			ans = false;
		} else {

			for (int a = 0; a < problem.length(); a++) {

				if (a == 0) {
					if (problem.charAt(a) == '*' || problem.charAt(a) == '/') {
						ans = false;
					}
				} else {
					if (a == problem.length() - 1) {
						if (!Character.isDigit(problem.charAt(a))) {
							ans = false;
						}
					} else {
						if (!Character.isDigit(problem.charAt(a)) && !Character.isDigit(problem.charAt(a + 1))) {
							ans = false;
						}
					}
				}

			}
		}

		return ans;
	}

	private boolean isOperator(String c) {
		boolean ans = false;

		switch (c) {
		case "*":
			ans = true;
			break;
		case "/":
			ans = true;
			break;
		case "+":
			ans = true;
			break;
		case "-":
			ans = true;
			break;
		}

		return ans;
	}

	@Override
	public String toString() {
		return "CalculatorLogic [problem=" + problem + ", solution=" + solution + "]";
	}

}
