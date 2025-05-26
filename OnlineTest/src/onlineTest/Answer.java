package onlineTest;

import java.io.Serializable;

public abstract class Answer<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	T answer;

	abstract T getAnswer();
	
}
