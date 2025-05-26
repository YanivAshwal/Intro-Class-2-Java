package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import onlineTest.SystemManager;

/**
 * 
 * You need student tests if you are looking for help during office hours about
 * bugs in your code.
 * 
 * @author UMCP CS Department
 *
 */
public class StudentTests {

    @Test
    public void testTrueFalse() {
        SystemManager m = new SystemManager();
        m.addExam(1, "CMSC Exam 1");
        try {
            m.addTrueFalseQuestion(2, 1, null, 0, false);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        boolean result = m.addExam(1, "CMSC Exam 2");
        assertEquals(false, result);
        try {
            m.addTrueFalseQuestion(2, 1, "Pineapple is my favorite fruit", 2.0, false);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        m.addTrueFalseQuestion(1, 1, "Pineapple is my favorite fruit", 2.0, false);
        m.addTrueFalseQuestion(1, 2, "Data Structures are fun", 2.0, true);
        
        String actual = m.getKey(1);
        StringBuffer expected = new StringBuffer();
        
        expected.append("Question Text: Pineapple is my favorite fruit\n");
        expected.append("Points: 2.0\n");
        expected.append("Correct Answer: False\n");
        expected.append("Question Text: Data Structures are fun\n");
        expected.append("Points: 2.0\n");
        expected.append("Correct Answer: True\n");
        
        assertEquals(expected.toString(), actual);
    }
    
    @Test
    public void testAnswerTrueFalse() {
        SystemManager m = new SystemManager();
        m.addExam(1, "CMSC Exam 1");
        m.addTrueFalseQuestion(1, 1, "Pineapple is my favorite fruit", 2.0, false);
        m.addTrueFalseQuestion(1, 2, "Data Structures are fun", 2.0, true);
        
        try {
            m.addStudent(null);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        
        m.addStudent("Ashwal,Yaniv");
        boolean result = m.addStudent("Ashwal,Yaniv");
        
        assertEquals(false, result);
        
        m.answerTrueFalseQuestion("Ashwal,Yaniv", 1, 1, false);
        m.answerTrueFalseQuestion("Ashwal,Yaniv", 1, 2, false);
        
        double result2 = m.getExamScore("Ashwal,Yaniv", 1);
        
        assertEquals(2.0, result2, 0);
        
        String result3 = m.getGradingReport("Ashwal,Yaniv", 1);
        
        StringBuffer expected = new StringBuffer();
        
        expected.append("Question #1 2.0 points out of 2.0\n");
        expected.append("Question #2 0.0 points out of 2.0\n");
        expected.append("Final Score: 2.0 out of 4.0");
        
        assertEquals(expected.toString(), result3);
    }
    
    @Test
    public void testAnswerMultipleChoice() {
        SystemManager m = new SystemManager();
        m.addExam(1, "CMSC Exam 1");
        m.addMultipleChoiceQuestion(1, 1, "What did I get on my last Math Test? A: 100 B: >80  C: >70 D: >60", 4.0, new String[] {"D"});
        m.addMultipleChoiceQuestion(1, 2, "Which of the Following are subclasses of the Set interface? A: TreeMap B: HashSet C: HashMap D: TreeSet", 4.0, new String[] {"B", "D"});
        
        m.addStudent("Ashwal,Yaniv");
        
        m.answerMultipleChoiceQuestion("Ashwal,Yaniv", 1, 1, new String[] {"C"});
        m.answerMultipleChoiceQuestion("Ashwal,Yaniv", 1, 2, new String[] {"B", "D"});
        
        double result = m.getExamScore("Ashwal,Yaniv", 1);
        
        assertEquals(4.0, result, 0);
        
        String result1 = m.getGradingReport("Ashwal,Yaniv", 1);
        
        StringBuffer expected = new StringBuffer();
        expected.append("Question #1 0.0 points out of 4.0\n");
        expected.append("Question #2 4.0 points out of 4.0\n");
        expected.append("Final Score: 4.0 out of 8.0");
        
        assertEquals(expected.toString(), result1);
    }
    
    @Test
    public void testAndAnswerFillInTheBlank() {
        SystemManager m = new SystemManager();
        
        m.addExam(1, "CMSC Exam 1");
        m.addFillInTheBlanksQuestion(1, 1, "The Mitochondria is the ________ of the _____", 3.0, new String[] {"powerhouse", "cell"});
        m.addFillInTheBlanksQuestion(1, 2, "The limit as n --> infinity for (1/ 1 + 1/n)^__ = __", 3.0, new String[] {"n", "e"});
        
        m.addStudent("Ashwal,Yaniv");
        m.answerFillInTheBlanksQuestion("Ashwal,Yaniv", 1, 1, new String[] {"powerhouse"});
        m.answerFillInTheBlanksQuestion("Ashwal,Yaniv", 1, 2, new String[] {"n", "e"});
        double result = m.getExamScore("Ashwal,Yaniv", 1);
        
        assertEquals(4.5, result, 0);
        
        String result1 = m.getGradingReport("Ashwal,Yaniv", 1);
        
        StringBuffer expected = new StringBuffer();
        expected.append("Question #1 1.5 points out of 3.0\n");
        expected.append("Question #2 3.0 points out of 3.0\n");
        expected.append("Final Score: 4.5 out of 6.0");
        
        assertEquals(expected.toString(), result1);
    }
    
    @Test
    public void testMultipleStudent() {
        SystemManager m = new SystemManager();
        m.addExam(1, "CMSC Exam 1");
        
        m.addTrueFalseQuestion(1, 1, "Pineapple is my favorite fruit", 5.0, false);
        m.addMultipleChoiceQuestion(1, 2, "What did I get on my last Math Test? A: 100 B: >80  C: >70 D: >60", 4.0, new String[] {"D"});
        m.addFillInTheBlanksQuestion(1, 3, "The Mitochondria is the ________ of the _____", 3.0, new String[] {"powerhouse", "cell"});
        
        m.addStudent("Ashwal,Yaniv");
        m.addStudent("Bibbins,Azi");
        
        m.answerTrueFalseQuestion("Ashwal,Yaniv", 1, 1, false);
        m.answerMultipleChoiceQuestion("Ashwal,Yaniv", 1, 2, new String[] {"D"});
        m.answerFillInTheBlanksQuestion("Ashwal,Yaniv", 1, 3, new String[] {"powerhouse", "cell"});
        
        m.answerTrueFalseQuestion("Bibbins,Azi", 1, 1, true);
        m.answerMultipleChoiceQuestion("Bibbins,Azi", 1, 2, new String[] {"D"});
        m.answerFillInTheBlanksQuestion("Bibbins,Azi", 1, 3, new String[] {"powerhouse"});
        
        double maxScore = m.getMaxScore(1);
        assertEquals(12.0, maxScore, 0.001);
        double minScore = m.getMinScore(1);
        assertEquals(5.5, minScore, 0.001);
        double avgScore = m.getAverageScore(1);
        assertEquals(8.75, avgScore, 0.001);
    }
    
    @Test
    public void testGrades() {
        SystemManager m = new SystemManager();

        m.addExam(1, "CMSC Exam 1");
        m.addTrueFalseQuestion(1, 1, "Pineapple is my favorite fruit", 2.0, false);
        m.addMultipleChoiceQuestion(1, 2, "How many students are in 132? A: 100 B: 300 C: Too Many", 4.0, new String[] {"C"});
        m.addFillInTheBlanksQuestion(1, 3, "The Mitochondria is the ________ of the _____", 3.0, new String[] {"powerhouse", "cell"});

        m.addStudent("Ashwal,Yaniv");
        m.addStudent("Bibbins,Azi");
        
        m.answerTrueFalseQuestion("Ashwal,Yaniv", 1, 1, false);
        m.answerMultipleChoiceQuestion("Ashwal,Yaniv", 1, 2, new String[] {"C"});
        m.answerFillInTheBlanksQuestion("Ashwal,Yaniv", 1, 3, new String[] {"powerhouse", "cell"})
        ;
        m.answerTrueFalseQuestion("Bibbins,Azi", 1, 1, true);
        m.answerMultipleChoiceQuestion("Bibbins,Azi", 1, 2, new String[] {"B"});
        m.answerFillInTheBlanksQuestion("Bibbins,Azi", 1, 3, new String[] {"powerhouse", "cell"});


        m.setLetterGradesCutoffs(
                new String[]{"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F"},
                new double[]{97,93,90,87,83,80,77,73,70,60,0});

        double gradeAshwal = m.getCourseNumericGrade("Ashwal,Yaniv");
        assertEquals(100.0, gradeAshwal, 0.001);

        double gradeBibbins = m.getCourseNumericGrade("Bibbins,Azi");
        assertTrue(gradeBibbins < 100.0);

        String letterBibbins = m.getCourseLetterGrade("Bibbins,Azi");
        assertEquals("F", letterBibbins);

        String courseGrades = m.getCourseGrades();
        StringBuffer expected = new StringBuffer();
        expected.append("Ashwal,Yaniv 100.0 A+\n");
        expected.append("Bibbins,Azi ").append(String.format("%.1f", gradeBibbins)).append(" F\n");

        assertEquals(expected.toString(), courseGrades);
    }
}