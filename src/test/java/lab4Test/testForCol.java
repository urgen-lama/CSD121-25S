package lab4Test;

import jdk.swing.interop.SwingInterOpUtils;
import lab4.game.Col;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class testForCol {
    @Test
        void testForValidLeftStrings() {
            assertEquals(Col.Left, Col.from("1"));
            assertEquals(Col.Left, Col.from("l"));
            assertEquals(Col.Left, Col.from("L"));
        }

        void testForValidMiddleStrings() {
            assertEquals(Col.Middle, Col.from("2"));
            assertEquals(Col.Middle, Col.from("m"));
            assertEquals(Col.Middle, Col.from("M"));
            assertEquals(Col.Middle, Col.from("c"));
            assertEquals(Col.Middle, Col.from("C"));
        }

        void testForValidRightStrings() {
            assertEquals(Col.Right, Col.from("3"));
            assertEquals(Col.Right, Col.from("r"));
            assertEquals(Col.Right, Col.from("R"));
        }

        void testForInvalidStrings() {
            assertThrows(IllegalArgumentException.class, () -> Col.from("0"));
            assertThrows(IllegalArgumentException.class, () -> Col.from("4"));
            assertThrows(IllegalArgumentException.class, () -> Col.from("x"));
            assertThrows(IllegalArgumentException.class, () -> Col.from("left"));
            assertThrows(IllegalArgumentException.class, () -> Col.from("middle"));
            assertThrows(IllegalArgumentException.class, () -> Col.from("right"));
            assertThrows(IllegalArgumentException.class, () -> Col.from(""));
            assertThrows(IllegalArgumentException.class, () -> Col.from(null));
        }
    }
