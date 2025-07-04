package lab4.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.basic.BasicSplitPaneUI;

import static lab4.game.Board.Status.InProgress;
import static lab4.game.Board.Status.XWins;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BoardMinimalTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testInitialStatusAndEmptiness() {
        // A new board should be InProgress and not full
        assertEquals(InProgress, board.getStatus());
        assertFalse(board.isFull());
    }
}