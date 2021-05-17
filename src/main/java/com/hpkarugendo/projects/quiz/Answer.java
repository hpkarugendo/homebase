package com.hpkarugendo.projects.quiz;

public class Answer {
	private char label;
	private String answer;
	private boolean correct;
	
	public Answer() {
		this.label = ' ';
		this.answer = "";
		this.correct = false;
	}
	public Answer(char label, String answer) {
		this.label = label;
		this.answer = answer;
		this.correct = false;
	}
	
	public char getLabel() {
		return label;
	}
	public void setLabel(char label) {
		this.label = label;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean getCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	@Override
	public String toString() {
		return "Answer [label=" + label + ", answer=" + answer + ", correct=" + correct + "]";
	}
	
}
