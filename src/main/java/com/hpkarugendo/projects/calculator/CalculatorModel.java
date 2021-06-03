package com.hpkarugendo.projects.calculator;

public class CalculatorModel {
	private String problem, solution, msg;

	public CalculatorModel() {
		this.problem = "";
		this.solution = "";
		this.msg= null;
	}

	public CalculatorModel(String problem, String solution) {
		this.problem = problem;
		this.solution = solution;
		this.msg = null;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "CalculatorModel [problem=" + problem + ", solution=" + solution + ", msg=" + msg + "]";
	}
	
	
}
