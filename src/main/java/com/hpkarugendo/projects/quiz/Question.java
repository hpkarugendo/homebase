package com.hpkarugendo.projects.quiz;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private int id;
	private String question;
	private List<Answer> answers;
	
	public Question() {
		this.id = 0;
		this.answers = new ArrayList<Answer>();
		this.question = "";
	}
	public Question(int id, String question) {
		super();
		this.id = id;
		this.question = question;
		this.answers = new ArrayList<Answer>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public void printAnswer() {
		System.out.println("Question (" + id + "): " + question);
		for(Answer a : answers) {
			String ans = "";
			if(a.getCorrect()) {
				ans = " <-THIS";
			}
			System.out.println("Answer " + a.getLabel() + ": " + a.getAnswer() + ans);
		}
	}
	
}
