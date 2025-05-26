package onlineTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

public class Student implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private HashMap<Integer, TreeMap<Integer,Answer<?>>> stuAnswers; //examId, TreeMap of <q#, answer>

	public Student(String name) {
		this.setName(name);
		stuAnswers = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void addNewExamAnswer(int examId) {
		if (!stuAnswers.containsKey(examId)) { 
			stuAnswers.put(examId, new TreeMap<Integer, Answer<?>>());
		}
	}

	public HashMap<Integer, TreeMap<Integer,Answer<?>>> getStuAnswers() {
		return stuAnswers;
	}
	
	
}

