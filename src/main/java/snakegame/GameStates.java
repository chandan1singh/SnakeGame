package snakegame;

public class GameStates {
		int[] snakeX;
		int[] snakeY;
		int enemyX;
		int enemyY;
		int moves;
		int score;
		int lengthOfSnake;
		boolean left;
		boolean right;
		boolean up;
		boolean down;

		public GameStates(int[] snakeX, int[] snakeY, int enemyX, int enemyY, int moves, int score, int lengthOfSnake,
				boolean left, boolean right, boolean up, boolean down) {
			this.snakeX = snakeX;
			this.snakeY = snakeY;
			this.enemyX = enemyX;
			this.enemyY = enemyY;
			this.moves = moves;
			this.score = score;
			this.lengthOfSnake = lengthOfSnake;
			this.left = left;
			this.right = right;
			this.up = up;
			this.down = down;
		}
	}
