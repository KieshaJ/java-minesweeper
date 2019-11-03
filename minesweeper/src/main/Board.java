package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {
    private List<Integer> field;
    private Boolean inGame;
    private Integer minesLeft;
    private List<Image> images;

    private Integer totalCells;
    private final JLabel statusLabel;

    public Board(JLabel statusLabel) {
        this.statusLabel = statusLabel;
        initBoard();
    }

    public void initBoard() {
        setPreferredSize(new Dimension(Config.BOARD_WIDTH, Config.BOARD_HEIGHT));

        images = loadImages(Config.IMAGE_NUMBER);

        addMouseListener(new MineAdapter());
        newGame();
    }

    private List<Image> loadImages(Integer imageNumber) {
        List<Image> finalList = new ArrayList<>();

        for(Integer x = 0; x < imageNumber; x++) {
            String path = "minesweeper/src/resources/" + x + ".png";
            finalList.add(new ImageIcon(path).getImage());
        }

        return finalList;
    }

    private void newGame() {
        Integer cell;

        Random random = new Random();
        inGame = true;
        minesLeft = Config.MINES;

        totalCells = Config.ROWS * Config.COLUMNS;

        field = new ArrayList<>();

        for(Integer x = 0; x < totalCells; x++) {
            field.add(Config.COVER_FOR_CELL);
        }

        statusLabel.setText(Integer.toString(minesLeft));

        Integer mineCounter = 0;

        while(mineCounter < Config.MINES) {
            Integer position = (int) (totalCells * random.nextDouble());

            if((position < totalCells) && (field.get(position) != Config.COVERED_MINE_CELL)) {
                Integer currentColumn = position % Config.COLUMNS;
                field.set(position, Config.COVERED_MINE_CELL);
                mineCounter++;

                if(currentColumn > 0) {
                    cell = position - 1 - Config.COLUMNS;
                    if(cell >= 0) {
                        if(field.get(cell) != Config.COVERED_MINE_CELL) {
                            Integer cellValue = field.get(cell);
                            field.set(cell, cellValue + 1);
                        }
                    }

                    cell = position - 1;

                    if(cell >= 0) {
                        if(field.get(cell) != Config.COVERED_MINE_CELL) {
                            Integer cellValue = field.get(cell);
                            field.set(cell, cellValue + 1);
                        }
                    }

                    cell = position + Config.COLUMNS - 1;

                    if(cell < totalCells) {
                        if(field.get(cell) != Config.COVERED_MINE_CELL) {
                            Integer cellValue = field.get(cell);
                            field.set(cell, cellValue + 1);
                        }
                    }
                }

                cell = position - Config.COLUMNS;

                if(cell >= 0) {
                    if(field.get(cell)  != Config.COVERED_MINE_CELL) {
                        Integer cellValue = field.get(cell);
                        field.set(cell, cellValue + 1);
                    }
                }

                cell = position + Config.COLUMNS;

                if(cell < totalCells) {
                    if(field.get(cell)  != Config.COVERED_MINE_CELL) {
                        Integer cellValue = field.get(cell);
                        field.set(cell, cellValue + 1);
                    }
                }

                if(currentColumn < (Config.COLUMNS - 1)) {
                    cell = position - Config.COLUMNS + 1;

                    if(cell >= 0) {
                        if(field.get(cell)  != Config.COVERED_MINE_CELL) {
                            Integer cellValue = field.get(cell);
                            field.set(cell, cellValue + 1);
                        }
                    }

                    cell = position + Config.COLUMNS + 1;

                    if(cell < totalCells) {
                        if(field.get(cell)  != Config.COVERED_MINE_CELL) {
                            Integer cellValue = field.get(cell);
                            field.set(cell, cellValue + 1);
                        }
                    }

                    cell = position + 1;

                    if(cell < totalCells) {
                        if(field.get(cell)  != Config.COVERED_MINE_CELL) {
                            Integer cellValue = field.get(cell);
                            field.set(cell, cellValue + 1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Integer uncover = 0;

        for(Integer x = 0; x < Config.ROWS; x++) {
            for(Integer y = 0; y < Config.COLUMNS; y++) {
                Integer cell = field.get((x * Config.COLUMNS) + y);
                if(inGame && cell == Config.MINE_CELL) {
                    inGame = false;
                }

                if(!inGame) {
                    if(cell == Config.COVERED_MINE_CELL) {
                        cell = Config.DRAW_MINE;
                    }
                    else if(cell == Config.MARKED_MINE_CELL) {
                        cell = Config.DRAW_MARK;
                    }
                    else if(cell > Config.COVERED_MINE_CELL) {
                        cell = Config.DRAW_WRONG_MARK;
                    }
                    else if(cell > Config.MINE_CELL) {
                        cell = Config.DRAW_COVER;
                    }
                }
                else {
                    if(cell > Config.COVERED_MINE_CELL) {
                        cell = Config.DRAW_MARK;
                    }
                    else if(cell > Config.MINE_CELL) {
                        cell = Config.DRAW_COVER;
                        uncover++;
                    }
                }

                graphics.drawImage(images.get(cell), (y * Config.CELL_SIZE), (x * Config.CELL_SIZE), this);
            }
        }

        if(uncover == 0 && inGame) {
            inGame = false;
            statusLabel.setText("Game won");
        }
        else if(!inGame) {
            statusLabel.setText("Game lost");
        }
    }

    private void findEmptyCells(Integer index) {
        Integer currentColumn = index % Config.COLUMNS;
        Integer cell;

        if(currentColumn > 0) {
            cell = index - Config.COLUMNS - 1;

            if(cell >= 0) {
                if(field.get(cell) > Config.MINE_CELL) {
                    Integer cellValue = field.get(cell);
                    field.set(cell, cellValue - Config.COVER_FOR_CELL);
                    if(field.get(cell) == Config.EMPTY_CELL) {
                        findEmptyCells(cell);
                    }
                }
            }

            cell = index - 1;

            if(cell >= 0) {
                if(field.get(cell) > Config.MINE_CELL) {
                    Integer cellValue = field.get(cell);
                    field.set(cell, cellValue - Config.COVER_FOR_CELL);
                    if(field.get(cell) == Config.EMPTY_CELL) {
                        findEmptyCells(cell);
                    }
                }
            }

            cell = index + Config.COLUMNS - 1;

            if(cell < totalCells) {
                if(field.get(cell) > Config.MINE_CELL) {
                    Integer cellValue = field.get(cell);
                    field.set(cell, cellValue - Config.COVER_FOR_CELL);
                    if(field.get(cell) == Config.EMPTY_CELL) {
                        findEmptyCells(cell);
                    }
                }
            }
        }

        cell = index - Config.COLUMNS;

        if(cell >= 0) {
            if(field.get(cell) > Config.MINE_CELL) {
                Integer cellValue = field.get(cell);
                field.set(cell, cellValue - Config.COVER_FOR_CELL);
                if(field.get(cell) == Config.EMPTY_CELL) {
                    findEmptyCells(cell);
                }
            }
        }

        cell = index + Config.COLUMNS;

        if(cell < totalCells) {
            if(field.get(cell) > Config.MINE_CELL) {
                Integer cellValue = field.get(cell);
                field.set(cell, cellValue - Config.COVER_FOR_CELL);
                if(field.get(cell) == Config.EMPTY_CELL) {
                    findEmptyCells(cell);
                }
            }
        }

        if(currentColumn < (Config.COLUMNS - 1)) {
            cell = index - Config.COLUMNS + 1;
            if(cell >= 0) {
                if(field.get(cell) > Config.MINE_CELL) {
                    Integer cellValue = field.get(cell);
                    field.set(cell, cellValue - Config.COVER_FOR_CELL);
                    if(field.get(cell) == Config.EMPTY_CELL) {
                        findEmptyCells(cell);
                    }
                }
            }

            cell = index + Config.COLUMNS + 1;

            if(cell < totalCells) {
                if(field.get(cell) > Config.MINE_CELL) {
                    Integer cellValue = field.get(cell);
                    field.set(cell, cellValue - Config.COVER_FOR_CELL);
                    if(field.get(cell) == Config.EMPTY_CELL) {
                        findEmptyCells(cell);
                    }
                }
            }

            cell = index + 1;

            if(cell < totalCells) {
                if(field.get(cell) > Config.MINE_CELL) {
                    Integer cellValue = field.get(cell);
                    field.set(cell, cellValue - Config.COVER_FOR_CELL);
                    if(field.get(cell) == Config.EMPTY_CELL) {
                        findEmptyCells(cell);
                    }
                }
            }
        }
    }

    private class MineAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            Integer xCoordinate = event.getX();
            Integer yCoordinate = event.getY();

            Integer eventColumn = xCoordinate / Config.CELL_SIZE;
            Integer eventRow = yCoordinate / Config.CELL_SIZE;

            Boolean doRepaint = false;

            if(!inGame) {
                newGame();
                repaint();
            }

            if((xCoordinate < Config.COLUMNS * Config.CELL_SIZE) && (yCoordinate < Config.ROWS * Config.CELL_SIZE)) {
                if(event.getButton() == MouseEvent.BUTTON3) {
                    if(field.get(eventRow * Config.COLUMNS + eventColumn) > Config.MINE_CELL) {
                        doRepaint = true;

                        if(field.get(eventRow * Config.COLUMNS + eventColumn) <= Config.COVERED_MINE_CELL) {
                            if(minesLeft > 0) {
                                Integer cellValue = field.get(eventRow * Config.COLUMNS + eventColumn);
                                field.set(eventRow * Config.COLUMNS + eventColumn, cellValue + Config.MARK_FOR_CELL);
                                minesLeft--;
                                String message = Integer.toString(minesLeft);
                                statusLabel.setText(message);
                            }
                            else {
                                statusLabel.setText("No marks left");
                            }
                        }
                        else {
                            Integer cellValue = field.get(eventRow * Config.COLUMNS + eventColumn);
                            field.set(eventRow * Config.COLUMNS + eventColumn, cellValue + Config.MARK_FOR_CELL);
                            minesLeft++;
                            String message = Integer.toString(minesLeft);
                            statusLabel.setText(message);
                        }
                    }
                }
                else {
                    if(field.get(eventRow * Config.COLUMNS + eventColumn) > Config.COVERED_MINE_CELL) {
                        return;
                    }

                    if(field.get(eventRow * Config.COLUMNS+ eventColumn) > Config.MINE_CELL && (field.get(eventColumn * Config.COLUMNS + eventColumn) < Config.MARKED_MINE_CELL)) {
                        Integer cellValue = field.get(eventRow * Config.COLUMNS + eventColumn);

                        field.set(eventRow * Config.COLUMNS + eventColumn, cellValue - Config.COVER_FOR_CELL);
                        doRepaint = true;

                        if(field.get(eventRow * Config.COLUMNS + eventColumn) == Config.MINE_CELL) {
                            inGame = false;
                        }

                        if(field.get(eventRow * Config.COLUMNS + eventColumn) == Config.EMPTY_CELL) {
                            findEmptyCells(eventRow * Config.COLUMNS + eventColumn);
                        }
                    }
                }

                if(doRepaint) {
                    repaint();
                }
            }
        }
    }
}
