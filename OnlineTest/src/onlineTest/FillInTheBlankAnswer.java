package onlineTest;

public class FillInTheBlankAnswer extends Answer<String[]>{
    private static final long serialVersionUID = 1L;

	public FillInTheBlankAnswer(String[] answer) {
        this.answer = answer;
    }
    
    @Override
    public String[] getAnswer() {
        return answer;
    }

	public void setFBAnswer(String[] answer) {
		this.answer = answer;
	}
	
}