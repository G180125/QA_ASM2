package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

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
    public void adminLogin() {
        //valid name & password
        String simulatedValidInput = "Admin1\npass1\n";
        ByteArrayInputStream simulatedValidInputStream = new ByteArrayInputStream(simulatedValidInput.getBytes());
        System.setIn(simulatedValidInputStream);
        boolean loginSuccessful = this.event.AdminLogin();
        assertTrue(loginSuccessful);

        //invalid name & password
        String simulatedInvalidInput1 = "\n\n";
        ByteArrayInputStream simulatedInvalidInputStream1 = new ByteArrayInputStream(simulatedInvalidInput1.getBytes());
        System.setIn(simulatedInvalidInputStream1);
        boolean loginFailure1 = this.event.AdminLogin();
        assertFalse(loginFailure1);

        //valid name & invalid password
        String simulatedInvalidInput2 = "Admin1\n\n";
        ByteArrayInputStream simulatedInvalidInputStream2 = new ByteArrayInputStream(simulatedInvalidInput2.getBytes());
        System.setIn(simulatedInvalidInputStream2);
        boolean loginFailure2 = this.event.AdminLogin();
        assertFalse(loginFailure2);

        //invalid name & valid password
        String simulatedInvalidInput3 = "\npass1\n";
        ByteArrayInputStream simulatedInvalidInputStream3 = new ByteArrayInputStream(simulatedInvalidInput3.getBytes());
        System.setIn(simulatedInvalidInputStream3);
        boolean loginFailure3 = this.event.AdminLogin();
        assertFalse(loginFailure3);
    }

    @Test
    public void studentLogin() {
        //valid name & password
        String simulatedValidInput = "7654324\np7654324#\n";
        ByteArrayInputStream simulatedValidInputStream = new ByteArrayInputStream(simulatedValidInput.getBytes());
        System.setIn(simulatedValidInputStream);
        boolean loginSuccessful = this.event.StudentLogin();
        assertTrue(loginSuccessful);

        //invalid name & password
    }

    @Test
    public void showStudentEvents() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        this.event.showStudentEvents();

        String actualLines = outputStream.toString();

        String expectedLines =
                "\nList of Events: \n" +
                        "o Wild Hope: Conversations for a Planetary Commons 15 Aug 2023 - 30 Sep 2023\n" +
                        "o Urban Futures Symposium 21 Aug 2023 - 25 Aug 2023\n" +
                        "o ‘Basalt Study’ by Christine McFetridge 22 Aug 2023 - 15 Sep 2023\n" +
                        "o ‘The Dark Botanical Garden’ by Pug 22 Aug 2023 - 15 Sep 2023\n" +
                        "o ‘Off the Well-Worn Path’ by Ryley Clarke 22 Aug 2023 - 15 Sep 2023\n" +
                        "o Future Play Lab: TRON (1982) 19 Sep 2023\n";

        assertEquals(expectedLines, actualLines);
        assertNotNull(actualLines);
    }

    @Test
    public void countStudent() {
        assertEquals(9, this.event.countStudent());
    }

    @Test
    public void addStudent() {
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

        //invalid password prefix
        String simulatedInput3 = "1234567\ntest\na1234567#\n";
        ByteArrayInputStream simulatedInputStream3 = new ByteArrayInputStream(simulatedInput3.getBytes());
        System.setIn(simulatedInputStream3);

        String result3 = this.event.AddStudent();
        assertSame("First letter of the Password should be p", result3);

        //invalid password suffix
        String simulatedInput4 = "1234567\ntest\np1234567@\n";
        ByteArrayInputStream simulatedInputStream4 = new ByteArrayInputStream(simulatedInput4.getBytes());
        System.setIn(simulatedInputStream4);

        String result4 = this.event.AddStudent();
        assertSame("Last letter of the password should be #", result4);

        //valid
        String simulatedInput5 = "1234568\ntest\np1234568#\n";
        ByteArrayInputStream simulatedInputStream5 = new ByteArrayInputStream(simulatedInput5.getBytes());
        System.setIn(simulatedInputStream5);

        String result5 = this.event.AddStudent();
        assertSame("Student Added Successfully", result5);
    }
}