package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {
    private List<Cell> field;
    private Boolean inGame;
    private Integer minesLeft;
    private List<Image> images;

    private Integer totalCells;
    private final JLabel statusLabel;

    public Board(JLabel statusLabel) {
        this.statusLabel = statusLabel;
        initBoard();
    }

    private void initBoard() {
        setPreferredSize(new Dimension(Config.BOARD_WIDTH, Config.BOARD_HEIGHT));

        images = loadImages();

        addMouseListener(new MineAdapter());
        newGame();
    }

    private List<Image> loadImages() {
        List<Image> finalList = new ArrayList<>();

        for(Integer x = 0; x < Config.IMAGE_NUMBER; x++) {
            String path = "minesweeper/src/resources/" + x + ".png";
            finalList.add(new ImageIcon(path).getImage());
        }

        return finalList;
    }

    private void newGame() {
        Random random = new Random();
        inGame = true;
        minesLeft = Config.MINES;
        totalCells = Config.ROWS * Config.COLUMNS;
        field = new ArrayList<>();

        for(Integer x = 0; x < totalCells; x++) {
            field.add(new CoveredCell());
        }

        statusLabel.setText(Integer.toString(minesLeft));
        Integer mineCounter = 0;

        while(mineCounter < Config.MINES) {
            double rawPosition = totalCells * random.nextDouble();
            Integer position = new Double(rawPosition).intValue();

            if(position < totalCells && !field.get(position).getType().equals(Config.COVERED_MINE_CELL)) {
                Integer currentColumn = position % Config.COLUMNS;
                field.set(position, new CoveredMineCell());
                mineCounter++;

                BoardUtils.updateLeftSide(field, currentColumn, position);
                BoardUtils.updateTopAndBottom(field, position);
                BoardUtils.updateRightSide(field, currentColumn, position);
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        int uncover = 0;

        for (Integer x = 0; x < totalCells; x++) {
            Integer cellType = field.get(x).getType();
            if (inGame && cellType.equals(Config.MINE_CELL)) {
                inGame = false;
            }

            if (!inGame) {
                if (cellType.equals(Config.COVERED_MINE_CELL)) {
                    cellType = Config.DRAW_MINE;
                }
                else if (cellType.equals(Config.MARKED_MINE_CELL)) {
                    cellType = Config.DRAW_MARK;
                }
                else if (cellType > Config.COVERED_MINE_CELL) {
                    cellType = Config.DRAW_WRONG_MARK;
                }
                else if (cellType > Config.MINE_CELL) {
                    cellType = Config.DRAW_COVER;
                }
            }
            else {
                if (cellType > Config.COVERED_MINE_CELL) {
                    cellType = Config.DRAW_MARK;
                }
                else if (cellType > Config.MINE_CELL) {
                    cellType = Config.DRAW_COVER;
                    uncover++;
                }
            }

            if (cellType < 0) {
                cellType = 0;
            }

            int xCoordinate = x % Config.COLUMNS * Config.CELL_SIZE;
            int yCoordinate = x / Config.ROWS * Config.CELL_SIZE;

            graphics.drawImage(images.get(cellType), xCoordinate, yCoordinate, this);
        }

        if(uncover == 0 && inGame) {
            inGame = false;
            statusLabel.setText("Game won");
        }
        else if(!inGame) {
            statusLabel.setText("Game lost");
        }
    }

    private class MineAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            Integer xCoordinate = event.getX();
            Integer yCoordinate = event.getY();

            Integer eventColumn = xCoordinate / Config.CELL_SIZE;
            Integer eventRow = yCoordinate / Config.CELL_SIZE;

            boolean doRepaint = false;

            if (!inGame) {
                newGame();
                repaint();
            }

            if (
                    xCoordinate < Config.COLUMNS * Config.CELL_SIZE &&
                    yCoordinate < Config.ROWS * Config.CELL_SIZE &&
                    event.getButton() == MouseEvent.BUTTON3
            ) {
                if (field.get(eventRow * Config.COLUMNS + eventColumn).getType() > Config.MINE_CELL) {
                    doRepaint = true;
                    markAttempt(field, eventRow, eventColumn);
                }
            }
            else {
                if (field.get(eventRow * Config.COLUMNS + eventColumn).getType() > Config.COVERED_MINE_CELL) {
                    return;
                }

                if (
                        field.get(eventRow * Config.COLUMNS + eventColumn).getType() > Config.MINE_CELL &&
                        field.get(eventColumn * Config.COLUMNS + eventColumn).getType() < Config.MARKED_MINE_CELL
                ) {
                    Integer cellValue = field.get(eventRow * Config.COLUMNS + eventColumn).getType();

                    field.get(eventRow * Config.COLUMNS + eventColumn).setType(cellValue - Config.COVER_FOR_CELL);
                    doRepaint = true;

                    if (field.get(eventRow * Config.COLUMNS + eventColumn).getType().equals(Config.MINE_CELL)) {
                        inGame = false;
                    }

                    if (field.get(eventRow * Config.COLUMNS + eventColumn).getType().equals(Config.EMPTY_CELL)) {
                        EmptyCellUtils.findEmptyCells(field, eventRow * Config.COLUMNS + eventColumn);
                    }
                }
            }

            if (doRepaint) {
                repaint();
            }
        }
    }

    private void markAttempt(List<Cell> field, Integer eventRow, Integer eventColumn) {
        if (field.get(eventRow * Config.COLUMNS + eventColumn).getType() <= Config.COVERED_MINE_CELL) {
            if (minesLeft > 0) {
                Integer cellValue = field.get(eventRow * Config.COLUMNS + eventColumn).getType();
                field.get(eventRow * Config.COLUMNS + eventColumn).setType(cellValue + Config.MARK_FOR_CELL);
                minesLeft--;
                String message = Integer.toString(minesLeft);
                statusLabel.setText(message);
            }
            else {
                statusLabel.setText("No marks left");
            }
        }
    }

    public List<Cell> getField() {
        return field;
    }

    public Boolean lgetInGame() {
        return inGame;
    }

    public Integer getMinesLeft() {
        return minesLeft;
    }

    public List<Image> getImages() {
        return images;
    }

    public Integer getTotalCells() {
        return totalCells;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }
}
