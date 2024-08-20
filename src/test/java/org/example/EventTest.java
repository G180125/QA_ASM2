package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EventTest {
    private final InputStream originalSystemIn = System.in;
    Event event;

    @Before
    public void setUp() {
        this.event = new Event();
    }

    @After
    public void tearDown() {
        System.setIn(originalSystemIn);
        resetStudentData();
    }

    private void resetStudentData() {
        event.Student.clear();

        addStudent("7654324", "Student1", "p7654324#");
        addStudent("7654325", "Student2", "p7654325#");
        addStudent("7654326", "Student3", "p7654326#");
        addStudent("7654327", "Student4", "p7654327#");
        addStudent("7654328", "Student5", "p7654328#");
        addStudent("7654329", "Student6", "p7654329#");
        addStudent("7654330", "Student7", "p7654330#");
        addStudent("7654331", "Student8", "p7654331#");
        addStudent("7654332", "Student9", "p7654332#");
    }

    private void addStudent(String id, String name, String password) {
        String simulatedInput = id + "\n" + name + "\n" + password + "\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        event.AddStudent();
    }

    @Test
    public void constructor() {
        assertNotNull(this.event.Admin);
        assertNotNull(this.event.Student);

        BasicData[] actualAdminArray = this.event.Admin.toArray(new BasicData[0]);
        BasicData[] expectedAdminArray = new BasicData[]{
                new BasicData(1, "Admin1", "pass1"),
                new BasicData(2, "Admin2", "pass2"),
                new BasicData(3, "Admin3", "pass3")
        };


        BasicData[] actualStudentArray = this.event.Student.toArray(new BasicData[0]);
        BasicData[] expectedStudentArray = new BasicData[]{
                new BasicData(7654324, "Student1", "p7654324#"),
                new BasicData(7654325, "Student2", "p7654325#"),
                new BasicData(7654326, "Student3", "p7654326#"),
                new BasicData(7654327, "Student4", "p7654327#"),
                new BasicData(7654328, "Student5", "p7654328#"),
                new BasicData(7654329, "Student6", "p7654329#"),
                new BasicData(7654330, "Student7", "p7654330#"),
                new BasicData(7654331, "Student8", "p7654331#"),
                new BasicData(7654332, "Student9", "p7654332#")
        };

        assertArrayEquals(expectedAdminArray, actualAdminArray);
        assertArrayEquals(expectedStudentArray, actualStudentArray);
    }

    @Test
    public void testAdminLogin() {
        //valid name & password
        testAdminLoginSuccessful();

        //invalid name & password
        testAdminLoginFailureByInvalidNameAndPassword();

        //invalid name & valid password
        testAdminLoginFailureByInvalidName();

        //valid name & invalid password
        testAdminLoginFailureByInvalidPassword();
    }

    public void testAdminLoginSuccessful(){
        String simulatedInput = "Admin1\npass1\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        boolean loginStatus = this.event.AdminLogin();
        assertTrue(loginStatus);
    }

    public void testAdminLoginFailureByInvalidNameAndPassword(){
        String simulatedInput = "\n\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        boolean loginStatus = this.event.AdminLogin();
        assertFalse(loginStatus);
    }

    private void testAdminLoginFailureByInvalidPassword(){
        String simulatedInput = "Admin1\n\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        boolean loginStatus = this.event.AdminLogin();
        assertFalse(loginStatus);
    }

    private void testAdminLoginFailureByInvalidName(){
        String simulatedInput = "\npassword1\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        boolean loginStatus = this.event.AdminLogin();
        assertFalse(loginStatus);
    }

    @Test(expected=Exception.class)
    public void testStudentLoginNameException() {
        //valid name & password
        String simulatedValidInput = "a\np7654324#\n";
        ByteArrayInputStream simulatedValidInputStream = new ByteArrayInputStream(simulatedValidInput.getBytes());
        System.setIn(simulatedValidInputStream);
        boolean loginStatus = this.event.StudentLogin();
        assertFalse(loginStatus);
    }

    @Test(expected=Exception.class)
    public void testStudentLoginPasswordException() {
        //valid name & password
        String simulatedValidInput = "7654324\n\n";
        ByteArrayInputStream simulatedValidInputStream = new ByteArrayInputStream(simulatedValidInput.getBytes());
        System.setIn(simulatedValidInputStream);
        boolean loginStatus = this.event.StudentLogin();
        assertFalse(loginStatus);
    }

    @Test
    public void testStudentLogin() {
        //valid name & password
        testStudentLoginSuccessful();

        //correct format but invalid value name & password
        testStudentLoginFailureByInvalidNameAndPassword();

        //correct format but invalid name
        testStudentLoginFailureByInvalidName();

        //correct format but invalid password
        testStudentLoginFailureByInvalidPassword();
    }

    private void testStudentLoginSuccessful(){
        String simulatedInput = "7654324\np7654324#\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        boolean loginStatus = this.event.StudentLogin();
        assertTrue(loginStatus);
    }

    private void testStudentLoginFailureByInvalidNameAndPassword(){
        String simulatedInput = "1111111\n1\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        boolean loginStatus = this.event.StudentLogin();
        assertFalse(loginStatus);
    }

    private void testStudentLoginFailureByInvalidName(){
        String simulatedInput = "1111111\np7654324#\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        boolean loginStatus = this.event.StudentLogin();
        assertFalse(loginStatus);
    }

    private void testStudentLoginFailureByInvalidPassword(){
        String simulatedInput = "7654324\n1\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        boolean loginStatus = this.event.StudentLogin();
        assertFalse(loginStatus);
    }

//    @Test
//    public void showStudentEvents() {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//
//        this.event.showStudentEvents();
//
//        String actualLines = outputStream.toString();
//        System.out.println(actualLines);
//
//        String expectedLines =
//                "\nList of Events: \n" +
//                        "o Wild Hope: Conversations for a Planetary Commons 15 Aug 2023 - 30 Sep 2023\n" +
//                        "o Urban Futures Symposium 21 Aug 2023 - 25 Aug 2023\n" +
//                        "o ‘Basalt Study’ by Christine McFetridge 22 Aug 2023 - 15 Sep 2023\n" +
//                        "o ‘The Dark Botanical Garden’ by Pug 22 Aug 2023 - 15 Sep 2023\n" +
//                        "o ‘Off the Well-Worn Path’ by Ryley Clarke 22 Aug 2023 - 15 Sep 2023\n" +
//                        "o Future Play Lab: TRON (1982) 19 Sep 2023\n";
//
//        assertEquals(expectedLines, actualLines);
//        assertNotNull(actualLines);
//    }

    @Test
    public void testViewStudentDetails(){
        testViewStudentDetailsSuccessful();
        testViewStudentDetailsFailure();
    }

    private void testViewStudentDetailsSuccessful(){
        assertTrue(this.event.viewStudentDetails());
    }

    private void testViewStudentDetailsFailure(){
        ArrayList<Integer> idList = new ArrayList<>();
        for(int i = 0; i < this.event.Student.size(); i++){
            idList.add(this.event.Student.get(i).getID());
        }

        for (Integer id : idList) {
            this.event.removeStudent(id);
        }

        assertFalse(this.event.viewStudentDetails());
    }

    @Test
    public void testSearchStudentDetails(){
        testSearchStudentDetailsSuccessful();
        testSearchStudentDetailsFailure();
    }

    private void testSearchStudentDetailsSuccessful(){
        assertTrue(this.event.searchStudentDetails(7654324));
    }

    private void testSearchStudentDetailsFailure(){
        assertTrue(this.event.searchStudentDetails(7654324));
        assertTrue(this.event.removeStudent(7654324));
        assertFalse(this.event.searchStudentDetails(7654324));
    }

    @Test
    public void countStudent() {
        assertEquals(9, this.event.countStudent());

        String simulatedInput = "1234567\ntest\np1234567#\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);

        String result = this.event.AddStudent();
        assertSame("Student Added Successfully", result);

        assertEquals(10, this.event.countStudent());
    }

    @Test
    public void addStudent() {
        assertFalse(this.event.searchStudentDetails(1234567));

        //student exists
        String input = "7654324\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        String result1 = this.event.AddStudent();
        assertEquals("Student Exists", result1);

        //invalid password length
        String simulatedInput1 = "1234567\ntest\nvalidPassword123!\n";
        ByteArrayInputStream simulatedInputStream1 = new ByteArrayInputStream(simulatedInput1.getBytes());
        System.setIn(simulatedInputStream1);

        String result2 = this.event.AddStudent();
        assertSame("Password length should be 9", result2);
        assertFalse(this.event.searchStudentDetails(1234567));

        //invalid password prefix
        String simulatedInput3 = "1234567\ntest\na1234567#\n";
        ByteArrayInputStream simulatedInputStream3 = new ByteArrayInputStream(simulatedInput3.getBytes());
        System.setIn(simulatedInputStream3);

        String result3 = this.event.AddStudent();
        assertSame("First letter of the Password should be p", result3);
        assertFalse(this.event.searchStudentDetails(1234567));

        //invalid password suffix
        String simulatedInput4 = "1234567\ntest\np1234567@\n";
        ByteArrayInputStream simulatedInputStream4 = new ByteArrayInputStream(simulatedInput4.getBytes());
        System.setIn(simulatedInputStream4);

        String result4 = this.event.AddStudent();
        assertSame("Last letter of the password should be #", result4);
        assertFalse(this.event.searchStudentDetails(1234567));

        //valid
        String simulatedInput5 = "1234567\ntest\np1234567#\n";
        ByteArrayInputStream simulatedInputStream5 = new ByteArrayInputStream(simulatedInput5.getBytes());
        System.setIn(simulatedInputStream5);

        String result5 = this.event.AddStudent();
        assertSame("Student Added Successfully", result5);
        assertEquals(10, this.event.countStudent());
        assertTrue(this.event.searchStudentDetails(1234567));
    }

    @Test
    public void testRemoveStudent() {
        testRemoveStudentSuccessful();
        testRemoveStudentFailure();
    }

    private void testRemoveStudentSuccessful(){
        String simulatedInput5 = "1234567\ntest\np1234567#\n";
        ByteArrayInputStream simulatedInputStream5 = new ByteArrayInputStream(simulatedInput5.getBytes());
        System.setIn(simulatedInputStream5);

        this.event.AddStudent();
        assertTrue(this.event.searchStudentDetails(1234567));
        assertTrue(this.event.removeStudent(1234567));
        assertFalse(this.event.searchStudentDetails(1234567));
    }

    private void testRemoveStudentFailure(){
        assertFalse(this.event.searchStudentDetails(1234567));
        assertFalse(this.event.removeStudent(1234567));
    }

    public static void rewriteStudentFile(){
        Event.rewriteStudentFile();
    }
}