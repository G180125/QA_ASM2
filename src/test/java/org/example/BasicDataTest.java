package org.example;

import org.junit.jupiter.api.*;

import javax.swing.text.Utilities;

import static org.junit.jupiter.api.Assertions.*;

class BasicDataTest {
    BasicData basicData;

    @BeforeEach
    void setup(){
        this.basicData = new BasicData(0, "test", "password");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getID() {
        assertEquals(0, this.basicData.getID());
    }

    @Test
    void getName() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void setID() {
    }

    @Test
    void setName() {
    }

    @Test
    void setPassword() {
    }

    @Test
    void print() {
    }
}