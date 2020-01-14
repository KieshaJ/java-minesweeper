package main;

import java.util.List;

class EmptyCellUtils {
    private static final Integer totalCells = Config.ROWS * Config.COLUMNS;

    static void findEmptyCells(List<Cell> field, Integer index) {
        Integer currentColumn = index % Config.COLUMNS;
        checkLeftSide(field, currentColumn, index);
        checkTopAndBottom(field, index);
        checkRightSide(field, currentColumn, index);
    }

    private static void checkLeftSide(List<Cell> field, Integer currentColumn, Integer position) {
        if(currentColumn > 0) {
            Integer cell = position - Config.COLUMNS - 1;
            checkNeighboorCell(field, cell);

            cell = position - 1;
            checkNeighboorCell(field, cell);

            cell = position + Config.COLUMNS - 1;
            checkNeighboorCell(field, cell);
        }
    }

    private static void checkTopAndBottom(List<Cell> field, Integer position) {
        Integer cell = position - Config.COLUMNS;
        checkNeighboorCell(field, cell);

        cell = position + Config.COLUMNS;
        checkNeighboorCell(field, cell);
    }

    private static void checkRightSide(List<Cell> field, Integer currentColumn, Integer position) {
        if(currentColumn < (Config.COLUMNS - 1)) {
            Integer cell = position - Config.COLUMNS + 1;
            checkNeighboorCell(field, cell);

            cell = position + Config.COLUMNS + 1;
            checkNeighboorCell(field, cell);

            cell = position + 1;
            checkNeighboorCell(field, cell);
        }
    }

    private static void checkNeighboorCell(List<Cell> field, Integer position) {
        if(position >= 0 && position < totalCells) {
            if(field.get(position).getType() > Config.MINE_CELL) {
                Integer cellValue = field.get(position).getType();
                field.get(position).setType(cellValue - Config.COVER_FOR_CELL);
                if(field.get(position).getType().equals(Config.EMPTY_CELL)) {
                    findEmptyCells(field, position);
                }
            }
        }
    }
}
