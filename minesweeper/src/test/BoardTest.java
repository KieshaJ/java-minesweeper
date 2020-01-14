package test;

import main.Board;
import main.Config;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {
    @Test
    void Board_initSuccess() {
        JLabel label = new JLabel("testBoard");
        Board board = new Board(label);

        assertEquals(label.getText(), board.getStatusLabel().getText());
        assertEquals(Config.IMAGE_NUMBER, board.getImages().size());
        assertEquals(Config.MINES, board.getMinesLeft());
        assertEquals(Config.ROWS * Config.COLUMNS, board.getTotalCells());
        assertEquals(Config.ROWS * Config.COLUMNS, board.getField().size());
        assertEquals(true, board.getInGame());
    }
}
