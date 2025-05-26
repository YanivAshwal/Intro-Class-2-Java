package tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.BoardCell;
import model.ClearCellGame;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {
	
    @Test
    public void testProcessCell() {
    	ClearCellGame game = new ClearCellGame(4, 4, new Random(), 1);
    	game.setBoardCell(0, 0, BoardCell.RED);
    	game.processCell(0, 0);
    	assertEquals(game.getBoardCell(0, 0), BoardCell.EMPTY);
    	assertEquals(game.getScore(), 1);
    	
    	game.setBoardWithColor(BoardCell.GREEN);
    	game.processCell(3, 3);
    	assertEquals(game.getBoardCell(0, 3), BoardCell.EMPTY);
    	assertEquals(game.getBoardCell(3, 0), BoardCell.EMPTY);
    	assertEquals(game.getBoardCell(1, 1), BoardCell.EMPTY);
    	assertEquals(game.getBoardCell(1, 2), BoardCell.GREEN);
    	assertEquals(game.getScore(), 11);
    	
    	game.setBoardWithColor(BoardCell.EMPTY);
    	game.setRowWithColor(3, BoardCell.RED);
    	game.nextAnimationStep();
    	assertTrue(game.isGameOver());
    }
}
