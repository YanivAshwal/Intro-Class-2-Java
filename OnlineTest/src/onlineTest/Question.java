package onlineTest;

import java.io.Serializable;

public abstract class Question<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	int examId, questionNumber;
	String text; 
	double points;
	T answer;
	
	
	public Question(int examId, int questionNumber, String text, double points,
			T answer) {
		this.examId = examId;
		this.questionNumber = questionNumber;
		this.text = text;
		this.points = points;
		this.answer = answer;
		
	}
	
	public int getExamId() {
		return this.examId;
	}

	public int getQuestionNumber() {
		return this.questionNumber;
	}

	public String getText() {
		return this.text;
	}
	
	public double getPoints() {
		return this.points;
	}

	public T getAnswer() {
		return this.answer;
	}

	protected abstract Question<?> copy(); 
	
	public abstract double gradeAnswer(Answer<?> studentAnswer);

}
