package lab5.test;

import lab5.game.Board;
import lab5.game.Position;
import lab5.game.PlayerToken;
import lab5.game.Row;
import lab5.game.Col;
import lab5.players.Omola;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OmolaTest {

    private Omola omolaPlayer;

    @BeforeEach
    void setUp() {
        omolaPlayer = new Omola("TestOmola");
    }

    @Test
    void testOmolaWinsImmediately() {
        Board board = new Board("XOX\n-XO\n--O");
        assertEquals(PlayerToken.X, board.getNextTurnToken());

        Position expectedWinMove = new Position(Row.Bottom, Col.Left);
        Position actualMove = omolaPlayer.pickNextMove(board);

        assertNotNull(actualMove);
        assertEquals(expectedWinMove, actualMove);

        Board boardAfterMove = new Board(board);
        boardAfterMove.placeNextToken(actualMove);
        assertEquals(PlayerToken.X, boardAfterMove.getWinner());
    }


    @Test
    void testOmolaBlocksOpponentWin() {
        Board board = new Board("OXX\n-O-\n---");
        assertEquals(PlayerToken.X, board.getNextTurnToken());

        Position expectedBlockMove = new Position(Row.Bottom, Col.Right);
        Position actualMove = omolaPlayer.pickNextMove(board);

        assertNotNull(actualMove);
        assertEquals(expectedBlockMove, actualMove);

        Board hypotheticalBoard = new Board(board);
        Position badMove = new Position(Row.Bottom, Col.Middle);
        hypotheticalBoard.placeNextToken(badMove);

        assertEquals(PlayerToken.O, hypotheticalBoard.getNextTurnToken());

        // O takes winning move
        hypotheticalBoard.placeNextToken(new Position(Row.Bottom, Col.Right));
        assertEquals(PlayerToken.O, hypotheticalBoard.getWinner());
    }


    @Test
    void testOmolaPicksAnyAvailablePositionWhenNoWinOrBlock() {
        Board board = new Board("XO-\n---\n---");
        assertEquals(PlayerToken.X, board.getNextTurnToken());

        Position actualMove = omolaPlayer.pickNextMove(board);

        assertNotNull(actualMove);
        assertTrue(board.getEmptyCells().contains(actualMove));

        Board boardAfterMove = new Board(board);
        boardAfterMove.placeNextToken(actualMove);
        assertNull(boardAfterMove.getWinner());

        Position expectedMove = new Position(Row.Top, Col.Right);
        assertEquals(expectedMove, actualMove);
    }

    @Test
    void testOmolaHandlesFullBoard() {
        Board board = new Board("XOX\nOXO\nXOX");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            omolaPlayer.pickNextMove(board);
        });
    }

}
