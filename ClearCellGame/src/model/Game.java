package model;

import java.awt.Color;

/**
 * This class represents the logic of a game where a board is updated on each
 * step of the game animation. The board can also be updated by selecting a
 * board cell.
 * 
 * @author Department of Computer Science, UMCP
 */

public abstract class Game {
	protected BoardCell[][] board;

	/**
	 * Defines a board with BoardCell.EMPTY cells.
	 * 
	 * @param maxRows
	 * @param maxCols
	 */
	public Game(int maxRows, int maxCols) {
		this.board = new BoardCell[maxRows][maxCols];
		setBoardWithColor(BoardCell.EMPTY);
	}

	public int getMaxRows() {
		return this.board.length; //.length returns the num of rows
	}

	public int getMaxCols() {
		return this.board[0].length; //all rows should have the same num of columns here
	}

	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		if (boardCell != null && rowIndex >= 0 && rowIndex < getMaxRows() 
				&& colIndex <getMaxCols() && colIndex >= 0) {
			this.board[rowIndex][colIndex] = boardCell;
		} else {
			throw new ArrayIndexOutOfBoundsException("The row or column is out of bounds"
					+ " or the cell is null");
		}
	}
		

	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		if (rowIndex >= 0 && rowIndex < getMaxRows() && colIndex < getMaxCols()
				&& colIndex >= 0) {
			return board[rowIndex][colIndex];
		} else {
			throw new ArrayIndexOutOfBoundsException("The row or column is out of bounds"
					+ " or the cell is null");
		}
	}

	/**
	 * Initializes row with the specified color.
	 * 
	 * @param rowIndex
	 * @param cell
	 */
	public void setRowWithColor(int rowIndex, BoardCell cell) { //??
		if (cell != null && rowIndex >= 0 && rowIndex < getMaxRows()) { 
			for( int i = 0; i < board[rowIndex].length; i++) {
				board[rowIndex][i]= cell; 
			}
		} else {
			throw new ArrayIndexOutOfBoundsException("The row is out of bounds"
					+ " or the cell is null");
		}
	}

	/*
	 * Initializes column with the specified color.
	 * 
	 * @param colIndex
	 * @param cell
	 */
	public void setColWithColor(int colIndex, BoardCell cell) {
		if (cell != null && colIndex >= 0 && colIndex < getMaxCols()) { 
			for (int i = 0; i < getMaxRows(); i++) { //traverse rows
				board[i][colIndex] = cell; //set col to cell
			}
		} else {
			throw new ArrayIndexOutOfBoundsException("The column is out of bounds"
					+ " or the cell is null");
		}
	}

	/**
	 * Initializes the board with the specified color.
	 * 
	 * @param cell
	 */
	public void setBoardWithColor(BoardCell cell) {
		if (cell != null) {
			for (int i = 0; i < getMaxRows(); i++) { //traverse rows
				for (int j = 0; j < getMaxCols(); j++) {
					board[i][j] = cell;
				}
			}
		} else { 
			throw new IllegalArgumentException("The cell is null");
		}
	}

	public abstract boolean isGameOver();

	public abstract int getScore();

	/**
	 * Advances the animation one step.
	 */
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board state and the selected
	 * cell.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public abstract void processCell(int rowIndex, int colIndex);
	
}
