package test;

import main.Config;
import main.CoveredCell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoveredCellTest {
    @Test
    void CoveredCellType_success() {
        CoveredCell coveredCell = new CoveredCell();
        assertEquals(Config.COVER_FOR_CELL, coveredCell.getType());

        coveredCell.setType(19);
        assertEquals(Config.COVERED_MINE_CELL, coveredCell.getType());
    }
}
