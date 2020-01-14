package main;

public class Config {
    public static final Integer IMAGE_NUMBER = 13;
    static final Integer CELL_SIZE = 15;

    public static final Integer COVER_FOR_CELL = 10;
    static final Integer MARK_FOR_CELL = 10;

    static final Integer EMPTY_CELL = 0;
    static final Integer MINE_CELL = 9;
    public static final Integer COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    static final Integer MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    static final Integer DRAW_MINE = 9;
    static final Integer DRAW_COVER = 10;
    static final Integer DRAW_MARK = 11;
    static final Integer DRAW_WRONG_MARK = 12;

    public static final Integer MINES = 40;
    public static final Integer ROWS = 16;
    public static final Integer COLUMNS = 16;

    static final Integer BOARD_WIDTH = COLUMNS * CELL_SIZE + 1;
    static final Integer BOARD_HEIGHT = ROWS * CELL_SIZE + 1;
}
