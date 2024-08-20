package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class MainTest {
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
//        EventTest eventTest = new EventTest();
//        eventTest.setUp();
//        eventTest.tearDown();
    }

    @Test
    public void testA_AdminLoginSuccess() {
        String choiceInput = "1\n";
        String adminInput = "Admin1\npass1\n";

        InputStream combinedInput = new SequenceInputStream(
                new ByteArrayInputStream(choiceInput.getBytes()),
                new ByteArrayInputStream(adminInput.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        testInitialMenu(output);

        // Assertions for successful admin login and interaction
        assertTrue(output.contains("NOTE: Check respective file for getting credentials"));
        assertTrue(output.contains("Enter Admin's Name:"));
        assertTrue(output.contains("Enter Admin's Password:"));
        testAdminOptionMenu(output);
    }

    @Test
    public void testAdminLoginFailure() {
        String choiceInput = "1\n";
        String adminLoginInput = "Admin\npass1\n";

        InputStream combinedInput = new SequenceInputStream(
                new ByteArrayInputStream(choiceInput.getBytes()),
                new ByteArrayInputStream(adminLoginInput.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        testInitialMenu(output);

        // Assertions for failed admin login
        assertTrue(output.contains("Unable to authenticate, try again"));
        testInitialMenu(output);
    }

    // Assume the Login function is worked correctly
    // In here I will not test the log in function
    // I will mainly focus on the View Details Of All Student
    @Test
    public void testAdminViewDetailsOfAllStudents(){
        String choiceInput = "1\n1\n";
        String adminInput = "Admin1\npass1\n";

        InputStream combinedInput = new SequenceInputStream(
                new ByteArrayInputStream(choiceInput.getBytes()),
                new ByteArrayInputStream(adminInput.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        testStudentDetailList(output);
    }

    @Test
    public void testAdminSearchDetailOfStudentSuccessful(){
        String choiceInput = "1\n2\n7654331\n";
        String adminInput = "Admin1\npass1\n";

        InputStream combinedInput = new SequenceInputStream(
                new ByteArrayInputStream(choiceInput.getBytes()),
                new ByteArrayInputStream(adminInput.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Enter id of the specific Student you want to search details of:"));
        testStudentDetails(output, new BasicData(7654331, "Student8", "p7654331#"));
        assertTrue(output.contains("Search result : true"));
    }

    @Test
    public void testAdminSearchDetailOfStudentFailureByInvalidInputFormat(){
        String choiceInput = "1\n2\na\n";
        String adminInput = "Admin1\npass1\n";

        InputStream combinedInput = new SequenceInputStream(
                new ByteArrayInputStream(choiceInput.getBytes()),
                new ByteArrayInputStream(adminInput.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Enter id of the specific Student you want to search details of:"));
        assertTrue(output.contains("*Sorry we encountered an unusual error, please try again*"));
    }

    @Test
    public void testAdminSearchDetailOfStudentFailureByInvalidID(){
        String choiceInput = "1\n2\n1\n";
        String adminInput = "Admin1\npass1\n";

        InputStream combinedInput = new SequenceInputStream(
                new ByteArrayInputStream(choiceInput.getBytes()),
                new ByteArrayInputStream(adminInput.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Enter id of the specific Student you want to search details of:"));
        assertTrue(output.contains("Search result : false"));
    }

    @Test
    public void testAdminRemoveStudentSuccessful(){
        String choiceInput = "1\n3\n7654332\n";
        String adminInput = "Admin1\npass1\n";

        InputStream combinedInput = new SequenceInputStream(
                new ByteArrayInputStream(choiceInput.getBytes()),
                new ByteArrayInputStream(adminInput.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Enter id of Student you want to remove: "));
        assertTrue(output.contains("Search result : true"));
    }

    @Test
    public void testAdminRemoveStudentFailureByInvalidInputFormat(){
        String choiceInput = "1\n3\na\n";
        String adminInput = "Admin1\npass1\n";

        InputStream combinedInput = new SequenceInputStream(
                new ByteArrayInputStream(choiceInput.getBytes()),
                new ByteArrayInputStream(adminInput.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Enter id of Student you want to remove:"));
        assertTrue(output.contains("*Sorry we encountered an unusual error, please try again*"));
    }

    @Test
    public void testAdminDeleteStudentFailureByInvalidID(){
        String choiceInput = "1\n3\n1\n";
        String adminInput = "Admin1\npass1\n";

        InputStream combinedInput = new SequenceInputStream(
                new ByteArrayInputStream(choiceInput.getBytes()),
                new ByteArrayInputStream(adminInput.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Enter id of Student you want to remove:"));
        assertTrue(output.contains("Search result : false"));
    }

    @Test
    public void testAdminInsertStudentSuccessful(){
        String choiceInput = "1\n4\n";
        String adminInput = "Admin1\npass1\n";
        String studentDetail = "7654333\nStudent10\np7654333#\n";

        InputStream combinedInput = new SequenceInputStream(
                new SequenceInputStream(
                        new ByteArrayInputStream(choiceInput.getBytes()),
                        new ByteArrayInputStream(adminInput.getBytes())
                ),
                new ByteArrayInputStream(studentDetail.getBytes())
        );

        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Enter Student's ID:"));
        assertTrue(output.contains("Enter Student's Name:"));
        assertTrue(output.contains("Enter Student's Password:"));
        assertTrue(output.contains("Student Added Successfully"));
    }

    @Test
    public void testAdminInsertStudentFailureByInvalidID(){
        String choiceInput = "1\n4\n";
        String adminInput = "Admin1\npass1\n";
        String studentDetail = "a\n";

        InputStream combinedInput = new SequenceInputStream(
                new SequenceInputStream(
                        new ByteArrayInputStream(choiceInput.getBytes()),
                        new ByteArrayInputStream(adminInput.getBytes())
                ),
                new ByteArrayInputStream(studentDetail.getBytes())
        );

        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Enter Student's ID:"));
        assertTrue(output.contains("null"));
    }

    @Test
    public void testAdminInsertStudentFailureByExistingID(){
        String choiceInput = "1\n4\n";
        String adminInput = "Admin1\npass1\n";
        String studentDetail = "7654331\n";

        InputStream combinedInput = new SequenceInputStream(
                new SequenceInputStream(
                        new ByteArrayInputStream(choiceInput.getBytes()),
                        new ByteArrayInputStream(adminInput.getBytes())
                ),
                new ByteArrayInputStream(studentDetail.getBytes())
        );
        System.setIn(combinedInput);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Enter Student's ID:"));
        testStudentDetails(output, new BasicData(7654331, "Student8", "p7654331#"));
        assertTrue(output.contains("Student Exists"));
    }

    private void testInitialMenu(String output){
        assertTrue(output.contains("********************************* WELCOME TO THE EVENT MANAGEMENT SYSTEM ***********************************"));
        assertTrue(output.contains("Do you want to proceed as an Admin or a Student?"));
        assertTrue(output.contains("Choose wisely:"));
        assertTrue(output.contains("1 - Admin"));
        assertTrue(output.contains("2 - Student"));
        assertTrue(output.contains("3 - Exit"));
        assertTrue(output.contains("Enter your choice:"));
    }

    private void testAdminOptionMenu(String output){
        assertTrue(output.contains("1 - View details of all Students"));
        assertTrue(output.contains("2 - Search details of a specific Student"));
        assertTrue(output.contains("3 - Delete a Student"));
        assertTrue(output.contains("4 - Insert a Student"));
        assertTrue(output.contains("5 - Counts the number of Students"));
        assertTrue(output.contains("6 - Logout"));
        assertTrue(output.contains("Enter the function you want to perform:"));
    }

    private void testStudentDetailList(String output){
        testStudentDetails(output, new BasicData(7654324, "Student1", "p7654324#"));
        testStudentDetails(output, new BasicData(7654325, "Student2", "p7654325#"));
        testStudentDetails(output, new BasicData(7654326, "Student3", "p7654326#"));
        testStudentDetails(output, new BasicData(7654327, "Student4", "p7654327#"));
        testStudentDetails(output, new BasicData(7654328, "Student5", "p7654328#"));
        testStudentDetails(output, new BasicData(7654329, "Student6", "p7654329#"));
        testStudentDetails(output, new BasicData(7654330, "Student7", "p7654330#"));
        testStudentDetails(output, new BasicData(7654331, "Student8", "p7654331#"));
//        testStudentDetails(output, new BasicData(7654332, "Student9", "p7654332#"));
    }

    private void testStudentDetails(String output, BasicData studentDetail){
        assertTrue(output.contains(basicDataToString(studentDetail)));
    }

    private String basicDataToString(BasicData basicData) {
        return "ID: " + basicData.getID() +
                "\nName: " + basicData.getName() +
                "\nPassword: " + basicData.getPassword() ;
    }
}
