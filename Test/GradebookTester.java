import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;


class GradebookTester {
    Gradebook test = new Gradebook("src/Gradebook.csv");
    Gradebook testTwo = new Gradebook("src/Gradebook2.csv");
    Gradebook testThree = new Gradebook("src/Gradebook3.csv");

    private final ByteArrayOutputStream captured_out = new ByteArrayOutputStream();
    private final PrintStream original_out = System.out;

    public GradebookTester() throws IOException {
    }

    @Before
    public void setupTest() {System.setOut(new PrintStream(captured_out));}

    @After
    public void desetupTest(){System.setOut(original_out);}

    //Method 1
    @Test
    void printRow(){
        System.setOut(new PrintStream(captured_out));
        test.printRow(0);
        String capture = captured_out.toString();
        assertEquals("Peter Parker: 62 96 62 62 99 67 71  (74.14)", capture);
        System.setOut(original_out);

    }

    @org.junit.jupiter.api.Test
    void printRow2(){
        System.setOut(new PrintStream(captured_out));
        testTwo.printRow(7);
        String capture = captured_out.toString();
        assertEquals("Carol Denvers: 99 84 76 71 99 66 82  (82.43)", capture);
        System.setOut(original_out);
    }

    @org.junit.jupiter.api.Test
    void printRow3(){
        System.setOut(new PrintStream(captured_out));
        testThree.printRow(20);
        String capture = captured_out.toString();
        assertEquals("Arthur Curry: 61 73 64 86 93 89 78  (77.71)", capture);
        System.setOut(original_out);
    }

    //Method 2
    @Test
    void averagePerAssignment() {
        double [] check = test.averagePerAssignment();
        double [] expected = {76.93,80.93,75.37,80.27,81.8,80.53,80.67};
        assertArrayEquals(expected,test.averagePerAssignment(),.001);

    }

    @Test
    void averagePerAssignment2() {
        double [] check = testTwo.averagePerAssignment();
        double [] expected = {70.85,80.62,73.85,74.77,83.08,79.38,79.15};
        assertArrayEquals(expected,testTwo.averagePerAssignment(),.001);


    }

    @Test
    void averagePerAssignment3() {
        double [] check = testThree.averagePerAssignment();
        double [] expected = {74.13,82.04,77.92,78.79,81.13,77.83,77.83};
        assertArrayEquals(expected,testThree.averagePerAssignment(),.001);

    }

    //Method 3
    @Test
    void largestVariation() {
        String check = test.largestVariation();
        String expected = "Clint Barton";
        assertEquals(expected, check);
    }

    @Test
    void largestVariation2() {
        String check = testTwo.largestVariation();
        String expected = "Peter Quill";
        assertEquals(expected, check);
    }
    @Test
    void largestVariation3() {
        String check = testThree.largestVariation();
        String expected = "Natasha Romanoff";
        assertEquals(expected, check);
    }

    //Method 4
    @Test
    void sortStudentsByAverage() {
        String [] expexted = {"Bruce Wayne", "Sam Wilson", "Dick Grayson",
                "Clint Barton", "Nick Fury", "Dinah Lance", "Oliver Queen",
                "Carol Denvers", "Hal Jordan", "Bruce Banner", "Luke Cage",
                "Scott Lang", "Ray Palmer", "Natasha Romanoff", "Stephen Strange",
                "Arthur Curry", "Victor Stone", "Garfield Logan", "Peter Quill",
                "Jessica Jones", "Rachel Roth", "Tony Stark", "Steve Rogers",
                "Clarke Kent", "Peter Parker", "Bucky Barnes", "James Rhodes",
                "Matt Murdock", "Wanda Maximoff", "Barry Allen"};
        test.sortStudentsByAverage();
        assertArrayEquals(expexted,test.getRoster());
    }

    @Test
    void sortStudentsByAverage2() {
        String [] expexted = {"Clint Barton", "Nick Fury", "Carol Denvers", "Bruce Banner", "Scott Lang",
                "Tony Stark", "Steve Rogers", "Peter Parker", "Becky Banner", "James Rhodes",
                "Matt Murdock", "Wanda Maximoff", "Peter Quill"};
        testTwo.sortStudentsByAverage();
        assertArrayEquals(expexted,testTwo.getRoster());
    }

    @Test
    void sortStudentsByAverage3() {
        String [] expexted = {"Bruce Wayne", "Dinah Lance", "Sam Wilson", "Oliver Queen",
                "Dick Grayson", "Nick Fury", "Hal Jordan", "Luke Cage", "Scott Lang",
                "Stephen Stone", "Peter Quill", "Jessica Jones", "Arthur Curry", "Rachel Roth",
                "Clarke Kent", "Ray Palmer", "James Rhodes", "Garfield Logan", "Stephen Strange",
                "Tony Stark", "Wanda Maximoff", "Barry Allen", "Peter Parker", "Natasha Romanoff"};
        testThree.sortStudentsByAverage();
        assertArrayEquals(expexted,testThree.getRoster());
    }

    //Method 5
    @Test
    void sortStudentsByName() { // regular sort with no repetitive names
        String [] expexted = {"Barry Allen", "Bruce Banner", "Bucky Barnes", "Clint Barton", "Luke Cage",
                "Arthur Curry", "Carol Denvers", "Nick Fury", "Dick Grayson", "Jessica Jones", "Hal Jordan",
                "Clarke Kent", "Dinah Lance", "Scott Lang", "Garfield Logan", "Wanda Maximoff", "Matt Murdock",
                "Ray Palmer", "Peter Parker", "Oliver Queen", "Peter Quill", "James Rhodes", "Steve Rogers",
                "Natasha Romanoff", "Rachel Roth", "Tony Stark", "Victor Stone", "Stephen Strange", "Bruce Wayne",
                "Sam Wilson"};
        test.sortStudentsByName();
        assertArrayEquals(expexted,test.getRoster());
    }

    @Test
    void sortStudentsByName2() { // when 2 people have the same last name
        String [] expexted = {"Becky Banner", "Bruce Banner", "Clint Barton", "Carol Denvers", "Nick Fury",
                "Scott Lang", "Wanda Maximoff", "Matt Murdock", "Peter Parker", "Peter Quill", "James Rhodes",
                "Steve Rogers", "Tony Stark"};
        testTwo.sortStudentsByName();
        assertArrayEquals(expexted,testTwo.getRoster());
    }

    @Test
    void sortStudentsByName3() { // when 2 people have the same first name
        String [] expexted = {"Barry Allen", "Luke Cage", "Arthur Curry", "Nick Fury", "Dick Grayson", "Jessica Jones",
                "Hal Jordan", "Clarke Kent", "Dinah Lance", "Scott Lang", "Garfield Logan", "Wanda Maximoff",
                "Ray Palmer", "Peter Parker", "Oliver Queen", "Peter Quill", "James Rhodes", "Natasha Romanoff",
                "Rachel Roth", "Tony Stark", "Stephen Stone", "Stephen Strange", "Bruce Wayne", "Sam Wilson"};
        testThree.sortStudentsByName();
        assertArrayEquals(expexted,testThree.getRoster()); // Stephen Stone and Stephen Strange
    }
}