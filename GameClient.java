// Author: Benjamin Brown
// GameClient.java
// Project 1
// Purpose: Manages input and calls GameBoard methods acting as the client.

import java.util.Scanner;

public class GameClient 
{
	public static void main(String args[])
	{
		// Variable and Objects.
		int width, height;
		int numberOfMines;
		int x, y;
		int oneFourth;

		char userInput;
		GameBoard gameBoard;
		Scanner scanner = new Scanner(System.in);
		
		// Header
		
		System.out.println("===================================");
		System.out.println("Console Minesweeper by Ben Brown");
		System.out.println("Project 1: COMP2150");
		System.out.println("\n? - Covered tile\n"
				+ "_ - Uncovered tile with no neightboring mines\n"
				+ "# - Any number of nearby neighboring mines\n"
				+ "! - Marked tile by player.");
		System.out.println("===================================");
		
		// Initialize gameBoard with user input.
		
		do
		{
			System.out.print("Board width: ");
			width = scanner.nextInt();
			System.out.print("Board height: ");
			height = scanner.nextInt();
			System.out.print("Number of Mines: ");
			numberOfMines = scanner.nextInt();
			
			oneFourth = (int)(0.25f * width * height);
			
		} while((width > 20 || height > 20) || // Size cannot exceed 20x20
				(width < 4 || height < 4) || // Size cannot be below 4x4
				(numberOfMines > oneFourth)); // Number of mines cannot exceed 1/4 of size
		
		// Game initialization
		gameBoard = new GameBoard(width, height);
		gameBoard.distributeMines(numberOfMines);
		gameBoard.adjacentMinesPerTile();
		
		// Uncomment to see answer before you input.
		// System.out.print(gameBoard.cheatSheet());
		
		do
		{
			// Update board.
			System.out.println("===================================");
			System.out.print(gameBoard);
			System.out.println("===================================");
		
			
			System.out.println("Input 'm' to mark or 'u' to uncover a tile.\n"
					+ "Enter a value of 0 to width - 1(X-Position) or height - 1(Y-Position).");
			
			// Input Handling
			userInput = scanner.next().trim().toLowerCase().charAt(0);
			
			System.out.print("X-Position: ");
			x = scanner.nextInt();
			System.out.print("Y-Position: ");
			y = scanner.nextInt();
			
			// Game logic.
			switch(userInput)
			{
				case 'm':
					gameBoard.markTile(x, y);
					break;
				case 'u':
					gameBoard.uncoverTile(x, y);
					break;
				default:
					System.err.println("Invalid character input.");
					break;
			}
		} while(!gameBoard.gameOver(x, y));
		
		System.out.println("===================================");
		System.out.print(gameBoard.cheatSheet());
		System.out.println("===================================");
		
		// Prevent memory leaks by closing.
		scanner.close();
	}
}
