// Author: Benjamin Brown
// Filename: GameBoard.java
// Project 1
// Purpose: Contains logic for performing game actions such as marking a tile.

import java.util.Random;

public class GameBoard 
{
	private Tile[][] tiles;
	
	private int width, height;
	private int mineCount;
	
	public GameBoard(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		tiles = initTiles();
	}
	
	private Tile[][] initTiles()
	{
		tiles = new Tile[width][height];
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				tiles[i][j] = new Tile();
			}
		}
		
		return tiles;
	}
	
	public void distributeMines(int numberOfMines)
	{	
		mineCount = numberOfMines;
		
		Random random = new Random();
		int randomPositionX, randomPositionY;
		
		int i = 0;
		while(i < mineCount)
		{
			randomPositionX = ((int)random.nextInt(tiles.length - 1));
			randomPositionY = ((int)random.nextInt(tiles[0].length - 1));
			
			//System.out.println(randomPositionX + ", " + randomPositionY);
			
			if(!tiles[randomPositionX][randomPositionY].getContainsMine())
			{		
				//System.out.println("set value to true.");
				
				tiles[randomPositionX][randomPositionY].setContainsMine(true);
				i++;
			}
		}
	}
	
	public void adjacentMinesPerTile()
	{	
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				int adjacentMines = 0;
				
				if(!tiles[i][j].getContainsMine())
				{
					/*
					 * Tests to ensure if the i and j are within boundaries in the array.
					 */
					
					// 4 Sides
					if(i + 1 < width && tiles[i + 1][j].getContainsMine())
						adjacentMines++;
					if(i - 1 > -1 && tiles[i - 1][j].getContainsMine())
						adjacentMines++;
					if(j - 1 > -1 && tiles[i][j - 1].getContainsMine())
						adjacentMines++;
					if(j + 1 < height && tiles[i][j + 1].getContainsMine())
						adjacentMines++;
					
					// 4 Corners
					if((i - 1 > -1 && j + 1 < height) && tiles[i - 1][j + 1].getContainsMine())
						adjacentMines++;
					if((i - 1 > -1 && j - 1 > -1) && tiles[i - 1][j - 1].getContainsMine())
						adjacentMines++;
					if((i + 1 < width && j + 1 < height) && tiles[i + 1][j + 1].getContainsMine())
						adjacentMines++;
					if((i + 1 < width && j - 1 > -1)&& tiles[i + 1][j - 1].getContainsMine())
						adjacentMines++;
				}
				
				tiles[i][j].setAdjacentMines(adjacentMines);
			}
		}
	}
	
	// Verifies if the tile is within the given array boundaries.
	private boolean verifyTile(int x, int y)
	{
		if((x >= tiles.length || y >= tiles[0].length) || (x < 0 && y < 0))
		{
			System.err.println(x + ", " + y + " is out of range.");
			return false;
		}
		
		return true;
	}
	
	// Marks or unmarks a tile at the specified position 
	// depending on its current state.
	public void markTile(int x, int y)
	{
		if(verifyTile(x, y))
		{
			if(!tiles[x][y].getIsMarked())
			{
				System.out.println("Marking " + x + ", " + y);
				
				// Marks tile.
				tiles[x][y].setIsMarked(true); 
			}
			else
			{
				System.out.println("Unmarking " + x + ", " + y);
				
				// Unmarks tile.
				tiles[x][y].setIsMarked(false);
			}
		}
	}
	
	// Uncovers a tile on the GameBoard at the specified position.
	public void uncoverTile(int x, int y)
	{
		// if this returns false, then tile verification failed
		// as input was out of boundaries.
		if(verifyTile(x, y))
		{
			// If covered, but not marked, we can uncover the tile.
			if(tiles[x][y].getIsCovered() && !tiles[x][y].getIsMarked())
			{
				//System.out.println("Uncovering " + x + " : " + y);
				
				tiles[x][y].setIsCovered(false);
			}
		}
	}
	
	// Tests if all tiles with mines are  marked,
	// and all tiles without mines are uncovered.
	public boolean win()
	{
		int markedCount = 0;
		int uncoveredCount = 0;
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				if((tiles[i][j].getContainsMine() && tiles[i][j].getIsMarked()))
				{
					markedCount++;
				}
				if(!tiles[i][j].getContainsMine() && !tiles[i][j].getIsCovered())
				{
					uncoveredCount++;
				}
			}
		}
		
		if(markedCount == mineCount && uncoveredCount == (width * height - mineCount))
			return true;
		
		return false;
	}
	
	// Any tile with a mine is uncovered  
	public boolean lose(int x, int y)
	{
		if(tiles[x][y].getContainsMine() && !tiles[x][y].getIsCovered())
			return true;

		return false;
	}
	
	public boolean gameOver(int x, int y)
	{
		if(win() || lose(x, y))
		{
			if(win())
				System.out.println("You win!");
			else
				System.out.println("You lose!");
		
			
			System.out.println("Game Over.");
			return true;
		}
		return false;
	}
	
	public String cheatSheet()
	{
		String cheatSheet = "";
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				if(j == tiles.length - 1)
				{
					if(tiles[i][j].getContainsMine())
					{
						// Show mines with an asterisk.
						cheatSheet += "*\n";
					}
					else
					{
						cheatSheet += tiles[i][j].toString() + "\n";
					}
				}
				else
				{
					if(tiles[i][j].getContainsMine())
					{
						// Show mines with an asterisk.
						cheatSheet += "*  ";
					}
					else
					{
						cheatSheet += tiles[i][j].toString() + "  ";
					}
				}
			}
		}
		
		return cheatSheet;
	}
	
	public String toString()
	{
		String gameBoard = "";
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				if(j == tiles.length - 1)
				{
					gameBoard += tiles[i][j].toString() + "\n";
				}
				else
				{
					gameBoard += tiles[i][j].toString() + "  ";
				}
			}
		}
		
		return gameBoard;
	}
}
