package onlineTest;

public class MultipleChoiceAnswer extends Answer<String[]> {
    private static final long serialVersionUID = 1L;

	public MultipleChoiceAnswer(String[] answer) {
        this.answer = answer;
    }
    @Override
    public String[] getAnswer() {
        return answer;
    }

	public void setMCAnswer(String[] answer) {
		this.answer = answer;
	}
	
	
}
