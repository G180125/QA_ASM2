package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicDataTest {
    BasicData basicData;

    @Before
    public void setup(){
        this.basicData = new BasicData(0, "test", "password");
    }

    @After
    public void tearDown() {
        this.basicData = null;
    }

    @Test
    public void getID() {
        assertEquals(0, this.basicData.getID());
        assertNotEquals(1, this.basicData.getID());
    }

    @Test
    public void getName() {
        assertSame("test", this.basicData.getName());
        assertNotSame("", this.basicData.getName());
    }
}