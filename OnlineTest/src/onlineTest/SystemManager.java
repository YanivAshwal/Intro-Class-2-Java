package onlineTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class SystemManager implements Manager, Serializable {
	
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Exam> examBank; //<examId, Exam>
	private TreeMap<String, Student> students; //<stuName, Student>
	private TreeMap<Double, String> gradeCurve; 
	
	public SystemManager() { 
		examBank = new HashMap<>();
		students = new TreeMap<>();
		gradeCurve = new TreeMap<>(Comparator.reverseOrder());
	}
	
	@Override
	public boolean addExam(int examId, String title) {
		if (examId < 0 || title == null) { //null check
			return false;
		} 
		
		Exam exam = new Exam(examId, title);
		return examBank.putIfAbsent(exam.getExamId(), exam) == null; //Adds, if null it means it was added
	}

	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber, String text,
			double points, boolean answer) {
		
		TorFQuestion<Boolean> tFQuestion = new TorFQuestion<Boolean>(examId,
				questionNumber, text, points, answer); //new t/f Question
		
		if (examBank.containsKey(examId)) { //if the exam is in the bank, 
			examBank.get(examId).getQuestions().put(questionNumber, tFQuestion);
			examBank.get(examId).getAnswers().put(questionNumber, new TorFAnswer(answer));
		} else {
			System.out.println("True or False Question not Added.\n\tNo such examId");
		}
		
	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber,
			String text, double points, String[] answer) {
		MultipleChoiceQuestion<String[]> mCQuestion = new MultipleChoiceQuestion<String[]>(examId, questionNumber,
				text, points, answer);
		
		if (examBank.containsKey(examId)) {
			examBank.get(examId).getQuestions().put(questionNumber,mCQuestion);
			examBank.get(examId).getAnswers().put(questionNumber, new MultipleChoiceAnswer(answer));
		} else {
			System.out.println("Multiple Choice Question not Added.\n\tNo such examId");
		}
	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, 
			String text, double points,String[] answer) {
		
		FillInTheBlankQuestion<String[]> fillQuestion =
				new FillInTheBlankQuestion<String[]>(examId, questionNumber, text, points, answer);
		
		if(examBank.containsKey(examId)) { 
			examBank.get(examId).getQuestions().put(questionNumber, fillQuestion);
			examBank.get(examId).getAnswers().put(questionNumber, new FillInTheBlankAnswer(answer));
		} else {
			System.out.println("Fill in the Blank Question not Added.\n\tNo such examId");
		}
	}

	@Override
	public String getKey(int examId) {
		String res = "";
		if(examBank.containsKey(examId)) {
			Exam exam = examBank.get(examId);
			ArrayList<Question<?>> sortedQuestions = new ArrayList<>(exam.getQuestions().values()); 
			sortedQuestions.sort(Comparator.comparingInt(q -> q.getQuestionNumber()));
			
			for (var q: sortedQuestions) { 
				res+= "Question Text: " + q.getText() + "\n";
				res+= "Points: " + q.getPoints() + "\n";
				
				if (q instanceof TorFQuestion) {
					Boolean bAnswer = (Boolean) q.getAnswer();
					String capitalizedA = bAnswer  ? "True" : "False";
					
					res+= "Correct Answer: " + capitalizedA + "\n";
					
				} else if (q instanceof MultipleChoiceQuestion) { 
					String[] correctAnswers = (String[]) q.getAnswer();
					TreeSet<String> sortedAnswers = new TreeSet<>(Arrays.asList(correctAnswers));
					
					res+= "Correct Answer: " + sortedAnswers.toString() + "\n";
					
				} else if (q instanceof FillInTheBlankQuestion) { 
					String[] correctAnswers = (String[]) q.getAnswer();
					TreeSet<String> sortedAnswers = new TreeSet<>(Arrays.asList(correctAnswers));
					
					res+= "Correct Answer: " + sortedAnswers.toString() + "\n";
				}
			}
			return res;
		} 
		return "Exam Not found";
	}

	@Override
	public boolean addStudent(String name) {
		if (name == null) { 
			return false;
		}
		Student student = new Student(name);
		if (!students.containsKey(name)) { 
			students.put(name, student);
			return true;
		}
		return false;
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId,
			int questionNumber, boolean answer) {
	    if (studentName == null) { 
	        return;
	    }
	    if (examBank.containsKey(examId) && students.containsKey(studentName)
	            && examBank.get(examId).getQuestions().containsKey(questionNumber)
	            && examBank.get(examId).getQuestions().get(questionNumber) instanceof TorFQuestion) {
	        
	        Student s = students.get(studentName);
	        
	        s.addNewExamAnswer(examId);
	        
	        s.getStuAnswers().get(examId).put(questionNumber, new TorFAnswer(answer));
	    }
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		if (studentName == null) {
			return;
		}
		if (examBank.containsKey(examId) && students.containsKey(studentName)
				&& examBank.get(examId).getQuestions().containsKey(questionNumber)
				&& examBank.get(examId).getQuestions().get(questionNumber) instanceof MultipleChoiceQuestion) {
			
			Student s = students.get(studentName);
			s.addNewExamAnswer(examId);
			
			s.getStuAnswers().get(examId).put(questionNumber, new MultipleChoiceAnswer(answer));
		}

	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId,
			int questionNumber, String[] answer) {
		if (studentName == null) {
			return;
		}
		if (examBank.containsKey(examId) && students.containsKey(studentName)
				&& examBank.get(examId).getQuestions().containsKey(questionNumber)
				&& examBank.get(examId).getQuestions().get(questionNumber) instanceof FillInTheBlankQuestion) {
			
			Student s = students.get(studentName);
			s.addNewExamAnswer(examId);
			
			s.getStuAnswers().get(examId).put(questionNumber, new FillInTheBlankAnswer(answer));
		}
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		if (studentName == null) {
			throw new IllegalArgumentException("Invalid Student Name ");
		}
		double score = 0.0;
		TreeMap<Integer, Question<?>> questions = examBank.get(examId).getQuestions();
		HashMap<Integer,TreeMap<Integer,Answer<?>>> stuAs = students.get(studentName).getStuAnswers();
		if (stuAs == null) { 
			throw new IllegalArgumentException("No Student Answers found for this Exam");
		}
		
		TreeMap<Integer, Answer<?>> examAnswers = students.get(studentName).getStuAnswers().get(examId);
		if (examAnswers == null) {
			return 0.0;
		}
		
		if (examBank.containsKey(examId) && students.containsKey(studentName)) {		
			for (Entry<Integer, Question<?>> entry : questions.entrySet())  {
				int qNum = entry.getKey();
				Question<?> q = entry.getValue();
				Answer<?> a = stuAs.get(examId).get(qNum);
				
				double questionScore = q.gradeAnswer(a); 
				score  += questionScore;
			}
		}
		return score;
	}

	@Override
	public String getGradingReport(String studentName, int examId) { 
		if (studentName == null) { 
			throw new IllegalArgumentException("Invalid Student Name ");
		}
		
		String result = "";
		
	    if (!examBank.containsKey(examId) || !students.containsKey(studentName)) {
	        return "Exam or Student not found.";
	    }
		
	    Exam exam = examBank.get(examId);
	    TreeMap<Integer, Question<?>> questions = exam.getQuestions();
	    HashMap<Integer, TreeMap<Integer, Answer<?>>> stuAs = students.get(studentName).getStuAnswers();
	    TreeMap<Integer, Answer<?>> examAnswers = stuAs.get(examId);
	    
		if (examAnswers == null) {
		    return "No student answers found for this exam.";
		}
		
		for (Entry<Integer, Question<?>> entry : questions.entrySet())  {
			int qNum = entry.getKey();
			Question<?> q = entry.getValue();
			Answer<?> a = examAnswers.get(qNum);
			
			result += "Question #" + qNum + " " + q.gradeAnswer(a) + " points out of " + q.getPoints() + "\n";
		}
		result += "Final Score: " + getExamScore(studentName, examId) + " out of "
				+ (double) examBank.get(examId).getTotalPoints();
		return result;
	}

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		if (letterGrades == null || cutoffs == null || letterGrades.length != cutoffs.length) {
			throw new IllegalArgumentException("Null Values or different list sizes");
		}
		gradeCurve = new TreeMap<>(Comparator.reverseOrder());
		
		for (int i = 0; i < cutoffs.length;i++) { 
			gradeCurve.put(cutoffs[i], letterGrades[i]);
		}
	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		if (studentName == null) {
			throw new IllegalArgumentException("Invalid Name Argument"); 
		}
		Student stu = students.get(studentName);
		
	    double totalPercentage = 0.0;
	    int examCount = 0;
		
		for (Exam exam : examBank.values()) {
			
			TreeMap<Integer, Answer<?>> stuExamAnswers = stu.getStuAnswers().get(exam.getExamId());
			if (stuExamAnswers != null) {
				double examScore = getExamScore(studentName, exam.getExamId());
				double examPercentage = (examScore / exam.getTotalPoints()) *100;
				totalPercentage += examPercentage;
				examCount++;
			}
		}
		if (examCount == 0) { 
			return 0.0;
		} 
		return totalPercentage / examCount;
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		if (studentName == null) {
			throw new IllegalArgumentException("Invalid Name Argument"); 
		}
		
		double numericGrade = getCourseNumericGrade(studentName);
		for (Entry<Double, String> entry : gradeCurve.entrySet()) { 
			if (numericGrade >= entry.getKey()) { 
				return entry.getValue();
			}
		}
		return "F"; //default, sorry kids.
	}

	@Override
	public String getCourseGrades() {
		String grades = "";
		for (Entry<String, Student> entry: students.entrySet()) {
			String entryName = entry.getValue().getName();
			grades += entryName + getCourseNumericGrade(entryName) + getCourseLetterGrade(entryName);
		}
		return grades;
	}
	

	@Override
	public double getMaxScore(int examId) {
		double maxScore = 0.0;
		
		for (Student student : students.values())  {
	        TreeMap<Integer, Answer<?>> examAs = student.getStuAnswers().get(examId);
	        if (examAs !=null) {
	        	double stuScore = getExamScore(student.getName(), examId);
	        
		        if (stuScore >= maxScore) {
		        	maxScore = stuScore;
		        }
	        }
		}
		return maxScore; 
	}

	@Override
	public double getMinScore(int examId) {
		double minScore = 100.0; //arbitrary just so it doesn't default to 0.0
		
		for (Student student : students.values()) { 
			TreeMap<Integer, Answer<?>> examAs = student.getStuAnswers().get(examId);
			if (examAs != null) {
				double stuScore = getExamScore(student.getName(), examId);
				
				if (stuScore < minScore) {
					minScore = stuScore; 
				}
			}
		}
		return minScore;
	}

	@Override
	public double getAverageScore(int examId) {
		double avg = 0.0;
		double earned = 0.0;
		int numExams = 0;
		for (Student student: students.values()) {
			TreeMap<Integer, Answer<?>> examAs = student.getStuAnswers().get(examId);
			
			if (examAs != null) { 
				earned += getExamScore(student.getName(), examId);
				numExams++;
			}
		}
		if (numExams == 0)  {
			return 0;
		}
		avg = earned / numExams;
		return avg;
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
			out.writeObject(manager);
		} catch (IOException e ) { 
			e.printStackTrace();
		}
	}

	@Override
	public Manager restoreManager(String fileName) {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
			return (Manager) in.readObject();
		} catch (IOException | ClassNotFoundException e) { 
			e.printStackTrace();
		}
		return null;
	}
}
