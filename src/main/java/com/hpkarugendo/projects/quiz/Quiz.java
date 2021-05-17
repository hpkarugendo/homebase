package com.hpkarugendo.projects.quiz;

public class Quiz {
	private int question;
	private char answer, correct;
	
	
	public Quiz() {
		this.question = 0;
		this.answer = ' ';
		this.correct = ' ';
	}
	public Quiz(int question, char correct) {
		this.question = question;
		this.answer = ' ';
		this.correct = correct;
	}
	
	public int getQuestion() {
		return question;
	}
	public void setQuestion(int question) {
		this.question = question;
	}
	public char getAnswer() {
		return answer;
	}
	public void setAnswer(char answer) {
		this.answer = answer;
	}
	public char getCorrect() {
		return correct;
	}
	public void setCorrect(char correct) {
		this.correct = correct;
	}
	@Override
	public String toString() {
		return "Quiz [question=" + question + ", answer=" + answer + ", correct=" + correct + "]";
	}
	
}
