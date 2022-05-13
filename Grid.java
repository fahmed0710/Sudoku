package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]						values;

	/*
	 * Constructs a Grid instance from a string[] as provided by TestGridSupplier
	 * 
	 * @param rows a String array
	 */
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}	
	
	/*
	 * Converts the grid to a String
	 * 
	 * @return a String representation of the grid
	 */
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}

	/*
	 * Copies the constructor and duplicates its source
	 * 
	 * @param src a Grid instance
	 */
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	/*
	 * Finds an empty member of values[][] & returns an array list of 
	 * 9 grids that look like the current grid, except the empty member 
	 * contains 1, 2, 3 .... 9. Returns null if the current grid is full. 
	 * 
	 * @return an array list of 9 new grids
	 */
	public ArrayList<Grid> next9Grids()
	{		
		int xOfNextEmptyCell = 0;
		int yOfNextEmptyCell = 0;

		if(isFull())
			return null;
		
		// Find x,y of an empty cell.
		for(int x = 0; x < values.length; x++)
		{
			for(int y = 0; y < values[0].length; y++)
			{
				if(values[x][y] == 0)
				{
					xOfNextEmptyCell = x;
					yOfNextEmptyCell = y;
				}	
			}
		}

		// Construct array list to contain 9 new grids.
		ArrayList<Grid> grids = new ArrayList<Grid>();

		// Create 9 new grids as described in the comments above. Add them to grids.
		for(int i = 1; i <= 9; i++)
		{
			Grid newGrid = new Grid(this);
			newGrid.values[xOfNextEmptyCell][yOfNextEmptyCell] = i;
			grids.add(newGrid);
		}
		
		return grids;
	}

	/*
	 * Returns true if the grid is legal, or if no row/column/3x3 block
	 * contains a repeated integer from 1-9.
	 * 
	 * @return whether the grid is legal
	 */
	public boolean isLegal()
	{
		// Check every row. If you find an illegal row, return false.
		for(int i = 0; i < values.length; i++)
		{
			ArrayList<Integer> checkRows = new ArrayList<Integer>();
			for(int j = 0; j < values[0].length; j++)
			{	
				if(values[i][j] != 0)
				{
					if(checkRows.contains(values[i][j]))
						return false;
					checkRows.add(values[i][j]);
				}
			}
		}

		// Check every column. If you find an illegal column, return false.
		for(int i = 0; i < values.length; i++)
		{
			ArrayList<Integer> checkCols = new ArrayList<Integer>();
			for(int j = 0; j < values[0].length; j++)
			{	
				if(values[j][i] !=0)
				{
					if(checkCols.contains(values[j][i]))
						return false;
					checkCols.add(values[j][i]);
				}
			}
		}

		// Check every block. If you find an illegal block, return false.
		for(int i = 0; i < values.length; i += 3)
		{
			for(int j = 0; j < values[0].length; j += 3)
			{
				ArrayList<Integer> checkBlocks = new ArrayList<Integer>();
				for(int k = 0; k < 3; k++)
				{
					for(int m = 0; m < 3; m++)
					{
						int value = values[i + k][j + m];
						if(value != 0)
						{
							if(checkBlocks.contains(value))
								return false;
							checkBlocks.add(value);
						}
								
					}
				}
			}
		}

		// All rows/cols/blocks are legal.
		return true;
	}

	/*
	 * Returns true if every cell member of values[][] is a digit from 1-9
	 * 
	 * @return whether the cell is full or not
	 */
	public boolean isFull()
	{
		for(int i = 0; i < values.length; i++)
		{
			for(int j = 0; j < values[0].length; j++)
			{
				if(values[i][j] == 0)
					return false;
			}
		}
		return true;
	}
	
	/*
	 * Returns true if x is a Grid and for (i,j), 
	 * x.values[i][j] == this.values[i][j];
	 * 
	 * @return whether 2 grids are equal or not
	 */
	public boolean equals(Object x)
	{
		Grid g = (Grid)x;
		for(int i = 0; i < values.length; i++)
		{
			for(int j = 0; j < values[0].length; j++)
			{
				if(values[i][j] != g.values[i][j])
					return false;
			}
		}
		return true;
	}
}