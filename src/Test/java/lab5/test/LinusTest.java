package lab5.test;

import lab5.game.Board;
import lab5.game.Col;
import lab5.game.Position;
import lab5.game.Row;
import lab5.players.Linus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LinusTest {

    @Test
    public void testPickNextMove_emptyBoard() {
        Linus linus = new Linus("Linus");
        Board board = new Board("---\n---\n---");

        Position expected = new Position(Row.Top, Col.Left);
        Position actual = linus.pickNextMove(board);

        assertEquals(expected, actual);
    }

    @Test
    public void testPickNextMove_someTaken() {
        Linus linus = new Linus("Linus");
        Board board = new Board("XOX\n-OX\n---");

        Position expected = new Position(Row.Middle, Col.Left);
        Position actual = linus.pickNextMove(board);

        assertEquals(expected, actual);
    }

    @Test
    public void testPickNextMove_oneAvailable() {
        Linus linus = new Linus("Linus");
        Board board = new Board("XOX\nXOX\nOX-");

        Position expected = new Position(Row.Bottom, Col.Right);
        Position actual = linus.pickNextMove(board);

        assertEquals(expected, actual);
    }

    @Test
    public void testPickNextMove_fullBoard() {
        Linus linus = new Linus("Linus");
        Board board = new Board("XOX\nOXO\nXOX");

        Position actual = linus.pickNextMove(board);

        assertNull(actual);
    }
}

