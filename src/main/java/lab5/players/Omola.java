package lab5.players;

import lab5.game.Board;
import lab5.game.Position;
import lab5.game.PlayerToken;

import java.util.List;

public class Omola extends Player {

    public Omola(String name) {
        super(name);
    }

    @Override
    public Position pickNextMove(Board currentBoard) {
        PlayerToken myToken = currentBoard.getNextTurnToken();
        PlayerToken opponentToken = myToken.opponent();
        List<Position> availableMoves = currentBoard.getEmptyCells();

        for (Position move : availableMoves) {
            Board copy = new Board(currentBoard);
            copy.placeNextToken(move);
            if (copy.getWinner() == myToken) {
                return move;
            }
        }

        for (Position move : availableMoves) {
            Board copy = new Board(currentBoard);

            Position dummyMove = null;

            if (copy.getNextTurnToken() == myToken) {
                for (Position dummy : copy.getEmptyCells()) {
                    if (!dummy.equals(move)) {  // Avoid conflict
                        copy.placeNextToken(dummy);
                        dummyMove = dummy;
                        break;
                    }
                }
            }

            if (!move.equals(dummyMove)) {
                copy.placeNextToken(move);
                if (copy.getWinner() == opponentToken) {
                    return move;  // Block opponent
                }
            }
        }

        return availableMoves.get(0);
    }
}
