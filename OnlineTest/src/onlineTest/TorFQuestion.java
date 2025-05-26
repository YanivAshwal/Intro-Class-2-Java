package onlineTest;

import java.io.Serializable;

public class TorFQuestion<T> extends Question<Boolean> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public TorFQuestion(int examId, int questionNumber, String text, double points,
			Boolean answer) {
		super(examId, questionNumber, text, points, answer);
	}
	
	public Question<?> copy() {
		return new TorFQuestion<>(this.examId, this.questionNumber, this.text, this.points, this.answer);
	}

	public double gradeAnswer(Answer<?> studentAnswer) {

		if (!(studentAnswer instanceof TorFAnswer)) { //if answer isnt a bool
			return 0.0; 
		}
		
		TorFAnswer tFstuAnswer = (TorFAnswer) studentAnswer; //cast to 
		
		if (this.answer.equals(tFstuAnswer.getAnswer())) { 
			return this.points;
		} else { 
			return 0.0;
		}
	}
}
