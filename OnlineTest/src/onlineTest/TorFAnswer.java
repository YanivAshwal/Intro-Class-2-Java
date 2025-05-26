package onlineTest;

public class TorFAnswer extends Answer<Boolean>{
    private static final long serialVersionUID = 1L;

	public TorFAnswer(Boolean answer) {
        this.answer = answer;
    }
    
    @Override
    public Boolean getAnswer() {
        return answer;
    }

	public void setTFAnswer(Boolean answer) {
		this.answer = answer;
	}
	
}
