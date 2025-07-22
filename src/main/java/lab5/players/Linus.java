package lab5.players;

import lab5.game.Board;
import lab5.game.Position;

import java.util.Comparator;

public class Linus extends Player {

    public Linus(String name) {
        super(name);
    }

    @Override
    public Position pickNextMove(Board currentBoard) {
        return currentBoard.getEmptyCells().stream()
                .sorted(Comparator
                        .comparingInt((Position pos) -> pos.row().ordinal())
                        .thenComparingInt(pos -> pos.col().ordinal()))
                .findFirst()
                .orElse(null);
    }
}
