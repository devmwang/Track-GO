// Code adapted from CPSC 210 AlarmSystem application at
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/blob/main/src/test/ca/ubc/cpsc210/alarm/test/EventTest.java

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e1;
    private Event e2;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e1 = new Event("Sensor open at door");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
        e2 = new Event("Sensor open at door");   // (1)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e1.getDescription());
        assertEquals(d.toString(), e1.getDate().toString()); // Convert dates to string to allow for millisecond deltas
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e1.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(e1.equals(e2) && e2.equals(e1));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(e1.equals(null));
    }

    @Test
    public void testWrongClass() {
        assertFalse(e1.equals(new String("Test")));
    }

    @Test
    public void verifyCorrectHashcode() {
        assertTrue(e1.hashCode() == e2.hashCode());
    }
}