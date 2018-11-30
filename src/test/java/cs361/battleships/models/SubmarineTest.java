package cs361.battleships.models;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class SubmarineTest {

    @Test
    public void testDefaultConstructor(){
        Submarine s1 = new Submarine();
        assertEquals(s1.getSunk(), false);
        assertEquals(s1.getKind(), "submarine");
        assertEquals(s1.getLength(), 5);
    }

    @Test
    public void testSetAndGetSubmergedBool(){
        Submarine s1 = new Submarine();
        s1.setSubmerged(true);
        assertEquals(s1.getSubmerged(), true);
    }

}
