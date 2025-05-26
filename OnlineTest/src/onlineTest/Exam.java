package onlineTest;


import java.util.TreeMap;
import java.io.Serializable;
import java.util.Map.Entry;

public class Exam implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int examId; 
	private String title;
	private int totalPoints;
	private TreeMap<Integer, Question<?>> questions; //questionNumber, Question //the questions on a test
	private TreeMap<Integer, Answer<?>> answers; //questionNumber, its Answer
	
	//Default Constructor
	public Exam(int examId, String title) {
		this.examId = examId;
		this.title = title;
		this.questions = new TreeMap<>(); 
		this.answers = new TreeMap<>();
	}
	
	//Copy Constructor
	public Exam(Exam original) {
		this.examId = original.examId;
		this.totalPoints = original.totalPoints;
		this.title = original.title;
		this.questions = new TreeMap<Integer, Question<?>>();
		this.answers = new TreeMap<Integer, Answer<?>>();
		
		for (Entry<Integer, Question<?>> q: original.getQuestions().entrySet() ) {
			this.questions.put(q.getKey(), q.getValue().copy());
		}
	}

	public int getExamId() {
		return this.examId;
	}

	public String getTitle() {
		return this.title;
	}

	public TreeMap<Integer, Question<?>> getQuestions() {
		return this.questions;
	}

	public int getTotalPoints() {
		int totalPoints = 0;
		for (Entry<Integer, Question<?>> q : questions.entrySet()) {
			totalPoints += q.getValue().getPoints();
		}
		return totalPoints;
	}

	public TreeMap<Integer, Answer<?>> getAnswers() {
		return answers;
	}
	
	
}
