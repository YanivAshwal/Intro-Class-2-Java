package onlineTest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;

public class FillInTheBlankQuestion<T> extends Question<String[]> implements Serializable{
	private static final long serialVersionUID = 1L;

	public FillInTheBlankQuestion(int examId, int questionNumber, String text,
			double points, String[] answer) {
		super(examId, questionNumber, text, points, answer);
	}

	protected FillInTheBlankQuestion<String[]> copy() { 
		String[] answerCopy = null;
		
		if (this.answer != null) {
			answerCopy = this.answer.clone();
		}
		return new FillInTheBlankQuestion<String[]>(this.examId, this.questionNumber, this.text, 
				this.points, answerCopy); 
	}

	@Override
	public double gradeAnswer(Answer<?> studentAnswer) {
		
		if (!(studentAnswer instanceof FillInTheBlankAnswer)) { //if answer
			return 0.0;
		}
		
		FillInTheBlankAnswer fBstuAnswer = (FillInTheBlankAnswer) studentAnswer;
		
		String[] correctAs = this.answer;
		String[] stuAs = fBstuAnswer.getAnswer();
		
		HashSet<String> correctSet =new HashSet<>(Arrays.asList(correctAs));
		HashSet<String> stuSet =new HashSet<>(Arrays.asList(stuAs));
		
	    int matches = 0;
	    for (String s : stuSet) {
	        if (correctSet.contains(s)) {
	            matches++;
	        }
	    }

	    double pointsPer = points / correctAs.length;
	    return pointsPer * matches;
	}
}
