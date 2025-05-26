package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MultipleChoiceQuestion<T> extends Question<String[]> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	ArrayList<String> answerChoices = new ArrayList<>();
	
	public MultipleChoiceQuestion(int examId, int questionNumber, String text,
			double points, String[] answer) {
		super(examId, questionNumber, text, points, answer);
		this.answerChoices = new ArrayList<String>();
	}

	public ArrayList<String> getAnswerChoices() {
		return this.answerChoices;
	}

	public void setAnswerChoices(ArrayList<String> answerChoices) {
		this.answerChoices = answerChoices;
	}
	
	public Question<?> copy() { 
		String[] answerCopy = null;
		 
		if (this.answer != null)  {
			answerCopy = this.answer.clone();
		}
		return new MultipleChoiceQuestion<String[]> (this.examId, questionNumber, this.text,
				this.points, answerCopy);
	}

	@Override
	public double gradeAnswer(Answer<?> studentAnswer) {
		
		if (!(studentAnswer instanceof MultipleChoiceAnswer)) { //if answer
			return 0.0;
		}
		
		MultipleChoiceAnswer mCstuAnswer = (MultipleChoiceAnswer) studentAnswer;
		
		String[] correctAs = this.answer;
		String[] stuAs = mCstuAnswer.getAnswer();
		
		HashSet<String> correctSet =new HashSet<>(Arrays.asList(correctAs));
		HashSet<String> stuSet =new HashSet<>(Arrays.asList(stuAs));
		
	    if (correctSet.equals(stuSet)) {
	        return this.points;
	    } else {
	        return 0.0;
	    }
	}
	
}
