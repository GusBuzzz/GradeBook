import java.io.*;
import java.util.Arrays;

public class Gradebook {
    public static void main(String[] args) throws IOException {
        Gradebook g = new Gradebook("src/Gradebook3.csv");
        System.out.println(Arrays.deepToString(g.Grades));

        //Method 1
        g.printRow(0);
        System.out.println();
        System.out.println();
        //Method 2
        double [] avgPerCol = g.averagePerAssignment();
        for (int i = 0; i < avgPerCol.length; i++){
            System.out.println(avgPerCol[i]);
        }
        System.out.println();
        //Method 3
        System.out.println(g.largestVariation());
        //Method 4
        System.out.println();
        g.sortStudentsByAverage();
        //Method 5
        System.out.println();
        g.sortStudentsByName();


    }

    /* ATTRIBUTES *******************************************************/
    private String[] Roster;
    private String[] Assignments;
    private int[][] Grades;

    /* CONSTRUCTORS *****************************************************/
    /* Default constructor */
    public Gradebook() {}

    /* Constructor based on data from file */
    public Gradebook(String filename) throws FileNotFoundException, IOException {
        int numStudents = numLines(filename) - 1;
        String[] line;

        FileReader fr = new FileReader(filename);
        BufferedReader textReader = new BufferedReader(fr);

        Assignments = textReader.readLine().substring(21).split(",");
        Grades = new int[numStudents][Assignments.length];
        Roster = new String[numStudents];

        /* for each student, we fill out:
         *  	- the student's name in Roster
         *  	- the student's grades in the corresponding row of Grades
         */
        for (int i = 1; i <= numStudents; i++) {
            line = textReader.readLine().split(",");
            Roster[i-1] = line[0] + " " + line[1];
            for (int j=0; j<Assignments.length; j++) {
                Grades[i-1][j] = Integer.valueOf(line[j+2]);
            }
        }

        textReader.close();
    }

    public int numLines(String filename) throws FileNotFoundException, IOException {
        int lines = 0;
        FileReader fr = new FileReader(filename);
        BufferedReader textReader = new BufferedReader(fr);

        while (textReader.ready()) {
            textReader.readLine();
            lines++;
        }

        textReader.close();
        return lines;
    }

    /* GETTERS AND SETTERS ***********************************************/
    /**
     * @return the roster
     */
    public String[] getRoster() {
        return Roster;
    }

    /**
     * @return the assignments
     */
    public String[] getAssignments() {
        return Assignments;
    }

    /**
     * @return the grades
     */
    public int[][] getGrades() {
        return Grades;
    }

    /**
     * @param roster the roster to set
     */
    public void setRoster(String[] roster) {
        Roster = roster;
    }

    /**
     * @param assignments the assignments to set
     */
    public void setAssignments(String[] assignments) {
        Assignments = assignments;
    }

    /**
     * @param grades the grades to set
     */
    public void setGrades(int[][] grades) {
        Grades = grades;
    }

    /* OTHER METHODS *******************************************************/
    /* This is where your work starts
     * Please follow instructions below and in the lab description.
     */

    /* Method 1: named printRow
     * 		• Inputs: an int value n that corresponds to a given row index
     * 			in Grades and an index in Roster.
     * 		• Output: nothing
     * 		• What it does: it prints out a given row: student’s name followed
     * 			by this student’s grades, and finally this student’s average grade
     */
    public void printRow(int n) {
        // Your code goes here
        double avg = 0.0; // keeps track of student's individual avg grades
        System.out.print(Roster[n] + ": ");
        for(int i = 0; i < Grades[n].length; i++) {
            avg += Grades[n][i];
            System.out.print(Grades[n][i] + " ");
        }
        System.out.print(" (" + Math.round(avg / Assignments.length * 100.0)/100.0 + ")");
    }

    /* Method 2: named averagePerAssignment
     * 		•	Inputs: nothing
     * 		•	Output: a 1D array of doubles of size Assignments.length
     * 		•	What it does: it computes and returns an array of the average
     * 				grades of all assignments, one average grade per assignment.
     */
    public double[] averagePerAssignment() {
        double[] result = new double[Assignments.length];
        // Your code goes here
        double gradeAvg = 0.0;
        for(int i = 0; i < Assignments.length; i++){
            for(int j = 0; j < Grades.length; j++){
                gradeAvg += Grades[j][i];
            }
            result[i] = Math.round(gradeAvg / Grades.length * 100.0)/ 100.0;
            gradeAvg = 0;
        }
        return result;
    }

    /* Method 3: named largestVariation
     * 		•	Inputs: nothing
     * 		•	Output: a string that corresponds to the name of a students
     * 		•	What it does: it checks, for each student, the variation between
     * 				their lowest and highest grade, and returns the name of the student
     * 				with the largest such variation.
     */
    public String largestVariation() {
        String result = "";
        // Your code goes here
        int lowGrade = Grades[0][0];
        int highGrade = Grades[0][0];
        int [] largestVariation = new int[Roster.length]; //stores all variations
        int largeDiff = 0;
        for(int i = 0; i < Grades.length; i++){
            for(int j = 0; j < Grades[i].length; j++){
                if(Grades[i][j] > highGrade){
                    highGrade = Grades[i][j];
                }
                if(Grades[i][j] < lowGrade){
                    lowGrade = Grades[i][j];
                }
            }
            largestVariation[i] = highGrade - lowGrade;
            highGrade = Grades[0][0];
            lowGrade = Grades[0][0];
        }
        for(int i = 0; i < largestVariation.length; i++){ // searches for highest variation
            if(largeDiff < largestVariation[i]){
                largeDiff = largestVariation[i];
                result = Roster[i]; //name of student with the highest variation
            }
        }
        return result;
    }

    /* Method 4: named sortStudentsByAverage
     * 		•	Inputs: nothing
     * 		•	Output: nothing
     * 		•	What it does: it sorts the students by highest to lowest average
     * 				of their grades. The execution of this method results in a reordering
     * 				of the Roster array as well as a corresponding reordering of the Grades
     * 				array, so that the rows of Grades still correspond to the correct student.
     * 		•	Note: you should use insertion sort.
     */
    public void sortStudentsByAverage() {
        // Your code goes here
        double [] avgGrade = new double [Roster.length]; //stores all averages
        int avg = 0;
        for(int i = 0; i < Grades.length; i++) { // calculates average grade per student
            for (int j = 0; j < Grades[i].length; j++){
                avg += Grades[i][j];
            }
            avgGrade[i] = Math.round(avg / Assignments.length * 100.0)/ 100.0;
            avg = 0;
        }

        for (int i = 1; i < avgGrade.length; ++i) { //This sorts the average grades with name
            double temp = avgGrade[i];
            String copyName = Roster[i];
            int j = i - 1;
            while (j >= 0 && avgGrade[j] < temp) {// the < goes from highest to lowest average
                avgGrade[j + 1] = avgGrade[j];
                Roster[j + 1] = Roster[j];
                Grades[j + 1] = Grades[j];
                j = j - 1;
            }
            avgGrade[j + 1] = temp;
            Roster[j + 1] = copyName;
        }
        /*Debug prints array name with average grade
        for (int i = 0; i < Roster.length; i++){
            System.out.println(Roster[i] + " (" + avgGrade[i] + ")");
        }

         */


    }

    /* Method 5: named sortStudentsByName
     * 		•	Inputs: nothing
     * 		•	Output: nothing
     * 		•	What it does: it sorts the students by alphabetical order of their last names
     * 				then first names (like a phone book or a dictionary). The execution of
     * 				this method results in a reordering of the Roster array as well as a
     * 				corresponding reordering of the Grades array, so that the rows of Grades
     * 				still correspond to the correct student.
     * 		•	Note: you should use selection sort.
     */
    public void sortStudentsByName() { // does not sort by last name it sorts by first name
        // Your code goes here
        int i, j; // Works
        int smallIndex;
        for (i = 0; i < Roster.length; i++) {
            smallIndex = i;
            for (j = i + 1; j < Roster.length; j++) {
                //Roster[j].substring(Roster[i].indexOf(" "))): This substring gets the last name of each person
                if(Roster[j].substring(Roster[j].indexOf(" ")).compareToIgnoreCase(Roster[smallIndex].substring(Roster[smallIndex].indexOf(" "))) < 0){
                    smallIndex = j;
                }
                //If there's 2 people with same last name sort by first name
                if(Roster[j].substring(Roster[j].indexOf(" ")).equalsIgnoreCase(Roster[smallIndex].substring(Roster[smallIndex].indexOf(" ")))){
                    if(Roster[j].substring(0,Roster[j].indexOf(" ")).compareToIgnoreCase(Roster[smallIndex].substring(0,Roster[smallIndex].indexOf(" "))) < 0){
                        smallIndex = j;
                        String tempFirst =  Roster[j];
                        Roster[j] = Roster[smallIndex];
                        Roster[smallIndex] = tempFirst;
                    }
                }
            }
            String temp =  Roster[i];
            Roster[i] = Roster[smallIndex];
            Roster[smallIndex] = temp;
        }
        /*Debug prints array of names
        for (int k = 0; k < Roster.length; k++){
            System.out.println(Roster[k]);
        }
         */
    }

}
