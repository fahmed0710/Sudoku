package sudoku;

import java.util.*;


public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	
	
	/*
	 * Constructs a solver object
	 * 
	 * @param problem a grid
	 */
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	/*
	 * Calls the solveRecurse method to solve the grid
	 */
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
	/*
	 * Recursively solves the puzzles
	 * 
	 * @param grid a grid
	 */
	private void solveRecurse(Grid grid)
	{		
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
		{
			// Abandon evaluation of this illegal board.
			return;
		}
		else if (eval == Evaluation.ACCEPT)
		{
			// A complete and legal solution. Add it to solutions.
			solutions.add(grid);
		}
		else
		{
			// Here if eval == Evaluation.CONTINUE. Generate all 9 possible next grids. 
			// Recursively call solveRecurse() on those grids.
			ArrayList<Grid> nextGrids = grid.next9Grids();
			for(Grid g : nextGrids)
			{
				solveRecurse(g);
			}
		}
	}
	
	/*
	 * Returns Evaluation.ABANDON if the grid is illegal,
	 * CONTINUE if the grid is legal & incomplete, &
	 * ACCEPT if the grid is legal and complete
	 * 
	 * @param grid a grid
	 * @return an evaluation status
	 */
	public Evaluation evaluate(Grid grid)
	{
		boolean full = grid.isFull();
		boolean legal = grid.isLegal();
		
		if(!legal)
			return Evaluation.ABANDON;
		if(!full)
			return Evaluation.CONTINUE;
		return Evaluation.ACCEPT;

	}

	
	/*
	 * Returns the solutions
	 * 
	 * @return the array list of solutions
	 */
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	public static void main(String[] args)
	{
		Grid g = TestGridSupplier.getPuzzle1();		// or any other puzzle
		Solver solver = new Solver(g);
		System.out.println("Will solve\n" + g);
		solver.solve();
		
		// Print out your solution, or test if it equals() the solution in TestGridSupplier.
		System.out.println(solver.getSolutions().get(0));
	}
}
