package main;

import java.util.List;

class BoardUtils {
    private static final Integer totalCells = Config.ROWS * Config.ROWS;

    static void updateLeftSide(List<Cell> field, Integer currentColumn, Integer position) {
        if(currentColumn > 0) {
            Integer cell = position - 1 - Config.COLUMNS;

            if(cell >= 0) {
                updateField(field, cell);
            }

            cell = position - 1;

            if(cell >= 0) {
                updateField(field, cell);
            }

            cell = position + Config.COLUMNS - 1;

            if(cell < totalCells) {
                updateField(field, cell);
            }
        }
    }

    static void updateTopAndBottom(List<Cell> field, Integer position) {
        Integer cell = position - Config.COLUMNS;

        if(cell >= 0) {
            updateField(field, cell);
        }

        cell = position + Config.COLUMNS;

        if(cell < totalCells) {
            updateField(field, cell);
        }
    }

    static void updateRightSide(List<Cell> field, Integer currentColumn, Integer position) {
        if(currentColumn < (Config.COLUMNS - 1)) {
            Integer cell = position - Config.COLUMNS + 1;

            if(cell >= 0) {
                updateField(field, cell);
            }

            cell = position + Config.COLUMNS + 1;

            if(cell < totalCells) {
                updateField(field, cell);
            }

            cell = position + 1;

            if(cell < totalCells) {
                updateField(field, cell);
            }
        }
    }

    private static void updateField(List<Cell> field, Integer cellPosition) {
        if(!field.get(cellPosition).getType().equals(Config.COVERED_MINE_CELL)) {
            Integer cellValue = field.get(cellPosition).getType();
            field.get(cellPosition).setType(cellValue + 1);
        }
    }
}
