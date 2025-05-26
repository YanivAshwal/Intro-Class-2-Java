package model;

import java.util.Random;
import java.util.Spliterator.OfPrimitive;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

import javax.management.loading.PrivateClassLoader;
import javax.naming.directory.SearchControls;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.standard.JobPriority;
import javax.swing.CellEditor;
import javax.swing.RowFilter;
import javax.xml.namespace.QName;

import org.hamcrest.core.IsInstanceOf;
import org.junit.validator.PublicClassValidator;
import org.w3c.dom.html.HTMLTableColElement;

/**
 * This class extends GameModel and implements the logic of the clear cell game.
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one
 * where every cell corresponds to BoardCell.EMPTY.
 * 
 * @author Department of Computer Science, UMCP
 */

public class ClearCellGame extends Game {
	

	private int strategy, score;
	private Random random;

	/**
	 * Defines a board with empty cells. It relies on the super class constructor to
	 * define the board. The random parameter is used for the generation of random
	 * cells. The strategy parameter defines which clearing cell strategy to use
	 * (for this project it will be 1). For fun, you can add your own strategy by
	 * using a value different that one.
	 * 
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 * @param strategy
	 */
	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		this.random = random;
		this.strategy = strategy;
	}

	/**
	 * The game is over when the last board row (row with index board.length -1) is
	 * different from empty row.
	 */
	public boolean isGameOver() {
		for (BoardCell cell : board[board.length-1]) { //iterates over the last row
			if (cell.getColor() != BoardCell.EMPTY.getColor()) { //if its a cell is filled...
				return true; 
			}
		}
		return false;
	}
	
	
	private boolean isRowEmpty(int row) {
		if (row >= 0 && row < getMaxRows()) { //iterates over desired row
			for (int col = 0; col < getMaxCols(); col++) {
				if (getBoardCell(row, col) != BoardCell.EMPTY) {  //if any cells are filled...
					return false;
				}
			}
			return true;
		}
		throw new ArrayIndexOutOfBoundsException("the row is out of bounds");
	}
	
	private int findNextNonEmptyRow(int row) { 
		int searchRow = row + 1;
		for (int i = searchRow; i < getMaxRows(); i++) {
			if (!isRowEmpty(i)) {
				return i;
			}
		}
		return -1;
	}

	public int getScore() {
		return score;
	}

	/**
	 * This method will attempt to insert a row of random BoardCell objects if the
	 * last board row (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {
		if (isGameOver()) {
			return;
		}
		for (int j =0; j < getMaxCols(); j++) { 
			for (int i = getMaxRows()-1; i > 0; i--) { //iterates  through
				if(board[i][j] == BoardCell.EMPTY) { //
					board[i][j] = board[i-1][j]; //sets cell to the cell above it 
					board[i-1][j] = BoardCell.EMPTY;
				}
			}
			//fills top row with random cells
			setBoardCell(0,j , BoardCell.getNonEmptyRandomBoardCell(random));
		}
		
	}

	/**
	 * This method will turn to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Notice that the
	 * clearing process does not clear every single cell that surrounds a cell
	 * selected (only those found in the vertical, horizontal or diagonal
	 * directions).
	 * 
	 * IMPORTANT: Clearing a cell adds one point to the game's score.<br />
	 * <br />
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. For example, if we have the following
	 * board (an * represents an empty cell):<br />
	 * <br />
	 * RRR<br />
	 * GGG<br />
	 * YYY<br />
	 * * * *<br/>
	 * <br />
	 * then processing each cell of the second row will generate the following
	 * board<br />
	 * <br />
	 * RRR<br />
	 * YYY<br />
	 * * * *<br/>
	 * * * *<br/>
	 * <br />
	 * IMPORTANT: If the game has ended no action will take place.
	 * 
	 * 
	 */
	public void processCell(int rowIndex, int colIndex) {
		if (rowIndex >= 0  && rowIndex < getMaxRows() && colIndex >= 0 
				&& colIndex  < getMaxCols()) {
			BoardCell original = getBoardCell(rowIndex, colIndex); //var for change methods
			setBoardCell(rowIndex, colIndex, BoardCell.EMPTY); //Clears selected cell
			score++; //increments score
			changeUp(rowIndex, colIndex, original); // recursively clears same color cells up
			changeDown(rowIndex, colIndex, original); //clears down
			changeRight(rowIndex, colIndex, original); //clears right
			changeLeft(rowIndex, colIndex, original); //clears left
			changeUpRight(rowIndex, colIndex, original); //clears up and right
			changeUpLeft(rowIndex, colIndex, original); //clears up and left
			changeDownRight(rowIndex,colIndex, original); //clears down and right
			changeDownLeft(rowIndex, colIndex, original); //clears down and left
			collapseRow(); 
		}
	}
	
	private void changeUp(int row, int col, BoardCell originalColor) {
		int prevRow = row - 1; //looks at the cell above
		
		//base case, if we hit the ceiling  or a cell is a different color
		if (prevRow < 0 || getBoardCell(prevRow, col) != originalColor) { 
			return;
		}
		
		setBoardCell(prevRow, col, BoardCell.EMPTY); //clears cell to the left
		score++; //increments score
		
		changeUp(prevRow, col, originalColor); //recursive call
	}
	
	private void changeDown(int row, int col, BoardCell originalColor) { 
		//same functionality as ChangeUp but down a row
		int nextRow = row + 1;
		
		if (nextRow >= getMaxRows() || getBoardCell(nextRow, col) != originalColor) {
			return;
		}
		
		setBoardCell(nextRow, col, BoardCell.EMPTY);
		score++;
		
		changeDown(nextRow, col, originalColor);
	}
	
	private void changeRight(int row, int col, BoardCell originalColor) {
	//same functionality, just to the right
		int rightCol = col + 1; 
		
		if (rightCol >= getMaxCols() || getBoardCell(row, rightCol) != originalColor) {
			return;
		}
		setBoardCell(row, rightCol, BoardCell.EMPTY);
		score++;
		
		changeRight(row, rightCol, originalColor);
	}
	
	private void changeLeft(int row, int col, BoardCell originalColor) {
		//same functionality just to the left
		int leftCol = col -1;
		
		if (leftCol < 0 || getBoardCell(row, leftCol) != originalColor) {
			return;
		}
		setBoardCell(row, leftCol, BoardCell.EMPTY);
		score++;
		
		changeLeft(row, leftCol, originalColor);
	}
	
	private void changeUpRight(int row, int col, BoardCell originalColor) {
		//same functionality just Diagonally up and right 
		int prevRow = row -1;
		int nextCol = col +1;
		
		if (prevRow < 0 || nextCol >= getMaxCols() 
				|| getBoardCell(prevRow, nextCol) != originalColor) {
			return;
		}
		setBoardCell(prevRow, nextCol, BoardCell.EMPTY);
		score++;
		
		changeUpRight(prevRow, nextCol, originalColor);
	}
	
	private void changeUpLeft(int row, int col, BoardCell originalColor) { 
		//same functionality just diagonally up and left
		int prevRow = row-1;
		int prevCol = col -1;
		
		if (prevRow < 0|| prevCol < 0 
				|| getBoardCell(prevRow, prevCol) != originalColor) {
			return;
		}
		setBoardCell(prevRow, prevCol, BoardCell.EMPTY);
		score++;
		
		changeUpLeft(prevRow, prevCol, originalColor);
	}
	
	private void changeDownRight(int row, int col, BoardCell originalColor) {
		//same functionality just Diagonally down and right
		int nextRow = row + 1; 
		int nextCol = col+1;
		
		if (nextRow > getMaxRows() || nextCol >= getMaxCols()
			|| getBoardCell(nextRow, nextCol) != originalColor) {
				return;
			}
		setBoardCell(nextRow, nextCol, BoardCell.EMPTY); 
		score++;
		
		changeDownRight(nextRow, nextCol, originalColor);
	}
	
	private void changeDownLeft(int row, int col, BoardCell originalColor) {
		//same functionality just Diagonally down and left
		int nextRow = row + 1;
		int prevCol = col-1;
		
		if(nextRow >= getMaxRows() || prevCol < 0
				|| getBoardCell(nextRow, prevCol) != originalColor) {
			return;
		}
		setBoardCell(nextRow, prevCol, BoardCell.EMPTY);
		score++;
		
		changeDownLeft(nextRow, prevCol, originalColor);
	}
	
	private void collapseRow() { 
		for (int i = 0; i < getMaxRows(); i++) {
			if (isRowEmpty(i)) { //if the cur row is empty
				int nextNonEmpty = findNextNonEmptyRow(i); //helper method call
				if (nextNonEmpty != -1) { //if there is an empty row below current row
					copyRow(nextNonEmpty, i);  //copy the non empty row to the current row
					setRowWithColor(nextNonEmpty, BoardCell.EMPTY); //change nonEmpty to EMPTY
				}
			}
		}
	}
	
	private void copyRow(int from, int to) { 
		//helper method for collapseRow that copies a row and moves it to another
		for (int j = 0; j < getMaxCols(); j++) {
			board[to][j] = board[from][j];
		}
	}

	public int getStrategy() { 
		return strategy;
	}

}
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	