package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    private final InputStream originalSystemIn = System.in;
    Event event;

    @BeforeEach
    void setUp() {
        this.event = new Event();
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
    }

    @Test
    void constructor() {
        assertNotNull(this.event.Admin, "Admin list should not be null");

        BasicData[] actualArray = this.event.Admin.toArray(new BasicData[0]);

        BasicData[] expectedArray = new BasicData[]{
                new BasicData(1, "Admin1", "pass1"),
                new BasicData(2, "Admin2", "pass2"),
                new BasicData(3, "Admin3", "pass3")
        };

        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void adminLogin() {
        String simulatedValidInput = "Admin1\npass1\n";
        ByteArrayInputStream simulatedValidInputStream = new ByteArrayInputStream(simulatedValidInput.getBytes());
        System.setIn(simulatedValidInputStream);
        boolean loginSuccessful = this.event.AdminLogin();
        assertTrue(loginSuccessful);

        String simulatedInvalidInput = "\n\n";
        ByteArrayInputStream simulatedInvalidInputStream = new ByteArrayInputStream(simulatedInvalidInput.getBytes());
        System.setIn(simulatedInvalidInputStream);
        boolean loginFailure = this.event.AdminLogin();
        assertFalse(loginFailure);
    }

    @Test
    void studentLogin() {
        String simulatedInput = "7654324\np7654324#\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);
        boolean loginSuccessful = this.event.StudentLogin();
        assertTrue(loginSuccessful);
    }

    @Test
    void showStudentEvents() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        this.event.showStudentEvents();

        List<String> actualLines = Arrays.asList(outputStream.toString().split(System.lineSeparator()));

        List<String> expectedLines = Arrays.asList(
                "List of Events: ",
                "o Wild Hope: Conversations for a Planetary Commons 15 Aug 2023 - 30 Sep 2023",
                "o Urban Futures Symposium 21 Aug 2023 - 25 Aug 2023",
                "o ‘Basalt Study’ by Christine McFetridge 22 Aug 2023 - 15 Sep 2023",
                "o ‘The Dark Botanical Garden’ by Pug 22 Aug 2023 - 15 Sep 2023",
                "o ‘Off the Well-Worn Path’ by Ryley Clarke 22 Aug 2023 - 15 Sep 2023",
                "o Future Play Lab: TRON (1982) 19 Sep 2023"
        );

        assertLinesMatch(expectedLines, actualLines);
    }

    @Test
    void searchStudentDetails() {
    }

    @Test
    void removeStudent() {
    }

    @Test
    void countStudent() {
        assertEquals(9, this.event.countStudent());
    }

    @Test
    void rewriteStudentFile() {
    }

    @Test
    void addStudent() {
        String simulatedInput = "1234567\nJohn Doe\nvalidPassword123!\n";
        ByteArrayInputStream simulatedInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(simulatedInputStream);

        String result = this.event.AddStudent();
        assertSame("Password length should be 9", result);

        assertDoesNotThrow(() -> {this.event.AddStudent();});
    }
}