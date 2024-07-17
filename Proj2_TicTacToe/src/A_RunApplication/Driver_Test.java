// yousef sharbi 1202057
// anas karakra 1200467
package A_RunApplication;

import java.util.Random;
import java.util.Scanner;

public class Driver_Test {
	
	static int res_i=-1;
	static int res_j=-1;
	static String chooseRandom[]= {"Human","AI"};
	static Random random = new Random();
	static int randomIndex = random.nextInt(chooseRandom.length);
	public static void main(String[] args) {
		
		char board[][]= { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };
		
		Scanner in=new Scanner(System.in);
		
		
		
		System.out.println(chooseRandom[randomIndex]+" will Start the game (maximize)");

		
		int depth=9;
		while(checkStateOfGame(board)==2) {
			
			
			if(chooseRandom[randomIndex].equals("Human")) {
				if(depth==9) {
					printBoard(board);
				}
				
				int x;
				int y;
				System.out.println("Please Enter row and column (0-2) separated by space: ");
				x = in.nextInt();
				y = in.nextInt();
				
				
				while(board[x][y]!=' ') {
					System.out.println("Please Re-enter row and column (0-2) separated by space: ");
					x = in.nextInt();
					y = in.nextInt();
				}
				if(board[x][y]==' ') {
					board[x][y]='X';
				}
				
				if(depth>0) {
					depth--;
				}
				
				
				if(checkStateOfGame(board)==2) {
					calcMinimax(board, depth, false, true);
					if(depth>0) {
						depth--;
					}
				}
				
				printBoard(board);
			}
			else {
				calcMinimax(board, depth, true, true);
				if(depth>0) {
					depth--;
				}
				
				printBoard(board);
				
				if(checkStateOfGame(board)==2) {
					int x;
					int y;
					System.out.println("Please Enter row and column (0-2) separated by space: ");
					x = in.nextInt();
					y = in.nextInt();
					
					
					while(board[x][y]!=' ') {
						System.out.println("Please Re-enter row and column (0-2) separated by space: ");
						x = in.nextInt();
						y = in.nextInt();
					}
					if(board[x][y]==' ') {
						board[x][y]='O';
					}
					
					if(depth>0) {
						depth--;
					}
				}
				
			}
			
			
		}
		
		if(checkStateOfGame(board)==1 && chooseRandom[randomIndex].equals("Human")) {
			System.out.println("The Human win");
		}
		else if(checkStateOfGame(board)==1 && chooseRandom[randomIndex].equals("AI")) {
			System.out.println("The AI win");
		}
		else if(checkStateOfGame(board)==-1 && !chooseRandom[randomIndex].equals("Human")) {
			System.out.println("The Human win");
		}
		else if(checkStateOfGame(board)==-1 && !chooseRandom[randomIndex].equals("AI")) {
			System.out.println("The AI win");
		}
		else if(checkStateOfGame(board)==0) {
			System.out.println("Tie");
		}
	
		//printBoard(board);
		
		

	}
	
	public static int calcMinimax(char board[][], int depth, boolean isMaxmizing, boolean isParent) {

		if (depth == 0 || checkStateOfGame(board) != 2) {
			return checkStateOfGame(board);
		}

		if (isMaxmizing) { // x time
			int finalScore = Integer.MIN_VALUE; // to save the max value for x
			int finalI = 0, finalJ = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == ' ') {
						board[i][j] = 'X';
						int score = calcMinimax(board, depth - 1, false, false);
						board[i][j] = ' ';
						if (score > finalScore) {
							finalScore = score;
							finalI = i;
							finalJ = j;
						}
						if (isParent) {
							System.out.println("score," + i + "," + j + ": " + score);
						}

					}

				}
			}
			if (isParent) {
				if(chooseRandom[randomIndex].equals("Human")) {
					board[finalI][finalJ] = 'O';
				}
				else {
					board[finalI][finalJ] = 'X';
				}
				
			}
			return finalScore;
		} else {
			int finalScore = Integer.MAX_VALUE;// to save the max value for o
			int finalI = 0, finalJ = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == ' ') {
						board[i][j] = 'O';
						int score = calcMinimax(board, depth - 1, true, false);
						board[i][j] = ' ';
						if (score < finalScore) {
							finalScore = score;
							finalI = i;
							finalJ = j;

						}
						if (isParent) {
							System.out.println("score," + i + "," + j + ": " + score);
						}
					}

				}
			}
			if (isParent) {
				if(chooseRandom[randomIndex].equals("Human")) {
					board[finalI][finalJ] = 'O';
				}
				else {
					board[finalI][finalJ] = 'X';
				}
			}
			
			return finalScore;
		}

	}
	
	
	public static int checkStateOfGame(char board[][]) {
		// 1: X win
		//-1: O win
		// 0: tie
		// 2: continue game
		
		//-------------------------------------------------------------------------------------------
		// Win:
		// in case row:
		
			// Case X:
			if((board[0][0]==board[0][1] && board[0][1]==board[0][2] && board[0][2]=='X') ||
			   (board[1][0]==board[1][1] && board[1][1]==board[1][2] && board[1][2]=='X') ||
			   (board[2][0]==board[2][1] && board[2][1]==board[2][2] && board[2][2]=='X')) {
				return 1;
			} 
			// Case O:
			else if((board[0][0]==board[0][1] && board[0][1]==board[0][2] && board[0][2]=='O') ||
					(board[1][0]==board[1][1] && board[1][1]==board[1][2] && board[1][2]=='O') ||
					(board[2][0]==board[2][1] && board[2][1]==board[2][2] && board[2][2]=='O')) {
				return -1;
			}
			
			
		// in case column:
			
			// Case X:
			else if((board[0][0]==board[1][0] && board[1][0]==board[2][0] && board[2][0]=='X') ||
			   (board[0][1]==board[1][1] && board[1][1]==board[2][1] && board[2][1]=='X') ||
			   (board[0][2]==board[1][2] && board[1][2]==board[2][2] && board[2][2]=='X')) {
				return 1;
			}
			// Case O:
			else if((board[0][0]==board[1][0] && board[1][0]==board[2][0] && board[2][0]=='O') ||
			        (board[0][1]==board[1][1] && board[1][1]==board[2][1] && board[2][1]=='O') ||
			        (board[0][2]==board[1][2] && board[1][2]==board[2][2] && board[2][2]=='O')) {
				return -1;
			}
			
			
		// in case diagonal:
			
			// Case X:
			else if((board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[2][2]=='X') ||
					(board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[2][0]=='X')) {
				return 1;
			}
			// Case O:
			else if((board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[2][2]=='O') ||
					(board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[2][0]=='O')) {
				return -1;
			}
			
		//-------------------------------------------------------------------------------------------	
		// Tie:
			
			else if(checkTie(board)) {
				return 0;
			}
			
		//-------------------------------------------------------------------------------------------	
		// Continue game:	
			else {
				return 2;
			}
		
		
	    
	    
	}
	
	
	public static boolean checkTie(char board[][]) {
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(board[i][j]==' ') {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public static void printBoard(char board[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|  " + board[i][j]);
                if(j==2) {
                	System.out.print(" |");
                }
            }
            System.out.println("\n -------------- ");
        }
    }

		

}
