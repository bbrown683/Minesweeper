// Author: Benjamin Brown
// Filename: Tile.java
// Project 1
// Purpose: Implementation of a basic tile object that has conditionals used by the GameBoard object.

public class Tile 
{
	private boolean isCovered;
	private boolean isMarked;
	private boolean containsMine;
	private int adjacentMines;
	
	public Tile()
	{
		isCovered = true;
		isMarked = false;
		containsMine = false;
		adjacentMines = 0;
	}
	
	public void setIsCovered(boolean value)
	{
		// A tile can be uncovered only if it’s not marked 
		// – trying to uncover a marked tile should do nothing.  
		if (!isMarked)
		{
			isCovered = value;
		}
	}
	
	public boolean getIsCovered()
	{
		return isCovered;
	}
	
	public void setIsMarked(boolean value)
	{
		//  A tile can be marked only if it’s still covered 
		// – trying to mark an already uncovered tile should do nothing.  
		if(isCovered)
		{
			isMarked = value;
		}
	}
	
	public boolean getIsMarked()
	{
		return isMarked;
	}
	
	public void setContainsMine(boolean value)
	{
		containsMine = value;
	}
	
	public boolean getContainsMine()
	{
		return containsMine;
	}
	
	public void setAdjacentMines(int value)
	{
		adjacentMines = value;
	}
	
	public int getAdjacentMines()
	{
		return adjacentMines;
	}
	
	public String toString()
	{
		if(isMarked)
		{
			// Tiles that are marked.
			return "!";
		}
		else if(isCovered)
		{
			// Covered tile.
			return "?";
		}
		else if (adjacentMines > 0)
		{
			// Uncovered with adjacent mines.
			return adjacentMines + "";
		}
		else 
		{
			// Uncovered but no adjacent mines.
			return "_";
		}
	}
}
