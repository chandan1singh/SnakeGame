package snakegame;

import java.awt.Color;

import java.awt.Container;

import java.awt.Font;

import java.awt.Graphics;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import java.security.SecureRandom;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import javax.swing.JFrame;

import javax.swing.JOptionPane;

import javax.swing.JPanel;

import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

	public ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));

	public ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));

	public ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));

	public ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));

	public ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));

	public ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));

	public ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));

	public int[] snakexlength;

	public int[] snakeylength;

	public int lengthOfSnake = 3;

	public SecureRandom random = new SecureRandom();

	public int enemyY;

	public int enemyX;

	public int[] xPos;

	public int[] yPos;

	public boolean left = false;

	public boolean right = true;

	public boolean up = false;

	public boolean down = false;

	public boolean gameOver = false;

	public int width, height;

	public int numXPositions, numYPositions;

	public int moves = 0;

	public int score = 0;

	public Timer timer;

	public int delay = 200;

	boolean flag = true;

	public ArrayList<GameState> gameStates; // To store game states for replay

	public int currentStateIndex;

	public boolean replay = true;

	// GameDatabase gameDatabase = new GameDatabase();

	Connection connection = null;

	public GamePanel(int windowWidth, int windowHeight) {

		addKeyListener(this);

		setFocusable(true);

		setFocusTraversalKeysEnabled(true);

		gameStates = new ArrayList<GameState>();

		width = windowWidth;

		height = windowHeight;

		numXPositions = width / 25;

		numYPositions = (height - 75) / 25;

		// Initialize the snakexlength and snakeylength arrays

		snakexlength = new int[numXPositions * numYPositions];

		snakeylength = new int[numXPositions * numYPositions];

		// Initialize xPos and yPos based on screen dimensions

		xPos = new int[numXPositions];

		yPos = new int[numYPositions];

		for (int i = 0; i < numXPositions; i++) {

			xPos[i] = 25 + i * 25;

		}

		for (int i = 0; i < numYPositions; i++) {

			yPos[i] = 75 + i * 25;

		}

		timer = new Timer(delay, this);

		timer.start();

		newEnemy();

		ConneCtion();

	}

	public void ConneCtion() {

		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";

		String username = "postgres";

		String password = "chandan1singh";

		try {

			connection = DriverManager.getConnection(jdbcURL, username, password);

		} catch (SQLException e) {

		}

	}

	public void newEnemy() {

		// TODO Auto-generated method stub

		enemyX = xPos[random.nextInt(34 - 2)];

		enemyY = yPos[random.nextInt(25 - 2)];

		for (int i = lengthOfSnake - 1; i >= 0; i--) {

			if (snakexlength[i] == enemyX && snakeylength[i] == enemyY) {

				newEnemy();

			}

		}

	}

	@Override

	public void paint(Graphics g) {

		// TODO Auto-generated method stub

		super.paint(g);

		g.setColor(Color.WHITE);

		g.drawRect(24, 10, width - 50 + 1, 55);

		g.drawRect(24, 74, width - 50 + 1, height - 125);

		g.setColor(Color.BLACK);

		g.fillRect(25, 75, width - 50 + 1, height - 125);

		if (moves == 0) {

			snakexlength[0] = 100;

			snakexlength[1] = 75;

			snakexlength[2] = 50;

			snakeylength[0] = 100;

			snakeylength[1] = 100;

			snakeylength[2] = 100;

			int textX = width / 3;

			int fontSize = width / 32;

			int textY1 = height / 4;

			int textY2 = height / 4 + 40;

			int textY3 = height / 4 + 70;

			int textY4 = height / 4 + 100;

			int textY5 = height / 4 + 130;

			g.setColor(Color.WHITE);

			g.setFont(new Font("Arial", Font.BOLD, fontSize));

			g.drawString("Welcome to Snake Game!", textX, textY1);

			g.setFont(new Font("Arial", Font.PLAIN, (int) (fontSize / (2))));

			g.drawString("Use arrow keys to move the snake.", textX, textY2);

			g.drawString("Coincide with Enemy to increase snake's length and score.", textX, textY3);

			g.drawString("Press 'R' to replay the game.", textX, textY4);

			g.drawString("Press 'Q' to quit the game.", textX, textY5);

			// repaint();

		}

		if (left) {

			leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);

		} else if (right) {

			rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);

		} else if (up) {

			upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);

		} else if (down) {

			downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);

		}

		for (int i = 1; i < lengthOfSnake; i++) {

			snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);

		}

		enemy.paintIcon(this, g, enemyX, enemyY);

		if (gameOver) {

			g.setColor(Color.WHITE);

			g.setFont(new Font("Arial", Font.BOLD, 50));

			g.drawString("Game Over", 300, 300);

			g.setFont(new Font("Arial", Font.PLAIN, 18));

			g.drawString("Space: Delete Replay & Restart | R: View Replay | Q: Close Game", 200, width / 2);

		}

		g.setColor(Color.WHITE);

		g.setFont(new Font("Arial", Font.PLAIN, 14));

		g.drawString("Score : " + score, 750, 30);

		g.drawString("length : " + lengthOfSnake, 750, 50);

		g.drawString("press Q to quit game", 550, 40);

		g.dispose();

	}

	public void saveData(int[] snakeX, int[] snakeY, int enemyX, int enemyY, int moves, int score, int lengthOfSnake,

			boolean left, boolean right, boolean up, boolean down) {

		Object[] intArrayX = new Object[snakeX.length];

		Object[] intArrayY = new Object[snakeY.length];

		for (int i = 0; i < snakeX.length; i++) {

			intArrayX[i] = snakeX[i];

			intArrayY[i] = snakeY[i];

		}

		// Set the SQL array as a parameter

		String insertQuery = "INSERT INTO game_states (snakeX, snakeY, enemyX, enemyY, moves, score, lengthOfSnake, isleft, isright, isup, isdown) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);) {

			preparedStatement.setArray(1, connection.createArrayOf("integer", intArrayX));

			preparedStatement.setArray(2, connection.createArrayOf("integer", intArrayY));

			preparedStatement.setInt(3, enemyX);

			preparedStatement.setInt(4, enemyY);

			preparedStatement.setInt(5, moves);

			preparedStatement.setInt(6, score);

			preparedStatement.setInt(7, lengthOfSnake);

			preparedStatement.setBoolean(8, left);

			preparedStatement.setBoolean(9, right);

			preparedStatement.setBoolean(10, up);

			preparedStatement.setBoolean(11, down);

			int ra = preparedStatement.executeUpdate();

			if (ra > 0) {

				// System.out.println("Data inserted successfully");

			}

		} catch (SQLException e) {

			// e.printStackTrace();

		}

	}

	public void deleteData() {

		String deleteQuery = "DELETE FROM game_states";

		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

		}

	}

	public void getData() {

		gameStates.clear();

		String selectQuery = "SELECT * FROM game_states";

		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				// Retrieve the data from the result set

				Integer[] snakeXArray = (Integer[]) resultSet.getArray("snakeX").getArray();

				Integer[] snakeYArray = (Integer[]) resultSet.getArray("snakeY").getArray();

				snakexlength = new int[snakeXArray.length];

				snakeylength = new int[snakeYArray.length];

				for (int i = 0; i < snakeXArray.length; i++) {

					snakexlength[i] = snakeXArray[i];

					snakeylength[i] = snakeYArray[i];

				}

				enemyX = resultSet.getInt("enemyX");

				enemyY = resultSet.getInt("enemyY");

				moves = resultSet.getInt("moves");

				score = resultSet.getInt("score");

				lengthOfSnake = resultSet.getInt("lengthOfSnake");

				left = resultSet.getBoolean("isleft");

				right = resultSet.getBoolean("isright");

				up = resultSet.getBoolean("isup");

				down = resultSet.getBoolean("isdown");

				gameStates.add(new GameState(snakexlength.clone(), snakeylength.clone(), enemyX, enemyY, moves, score,

						lengthOfSnake, left, right, up, down));

			}

		} catch (SQLException e) {

		}

	}

	@Override

	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub

		for (int i = lengthOfSnake - 1; i > 0; i--) {

			snakexlength[i] = snakexlength[i - 1];

			snakeylength[i] = snakeylength[i - 1];

		}

		if (left) {

			snakexlength[0] = snakexlength[0] - 25;

		} else if (right) {

			snakexlength[0] = snakexlength[0] + 25;

		} else if (up) {

			snakeylength[0] = snakeylength[0] - 25;

		} else if (down) {

			snakeylength[0] = snakeylength[0] + 25;

		}

		if (snakexlength[0] > width - 50)

			snakexlength[0] = 25;

		if (snakexlength[0] < 25)

			snakexlength[0] = width - 50;

		//

		if (snakeylength[0] > height + 50 - 125)

			snakeylength[0] = 75;

		if (snakeylength[0] < 75)

			snakeylength[0] = height + 50 - 125;

		collidesWithEnemy();

		collidesWithBody();

		if (!gameOver && flag) {

			gameStates.add(new GameState(snakexlength.clone(), snakeylength.clone(), enemyX, enemyY, moves, score,

					lengthOfSnake, left, right, up, down));

		}

		repaint();

	}

	public void collidesWithBody() {

		// TODO Auto-generated method stub

		for (int i = lengthOfSnake - 1; i > 0; i--) {

			if (snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {

				timer.stop();

				gameOver = true;

			}

		}

	}

	public void collidesWithEnemy() {

		// TODO Auto-generated method stub

		if (snakexlength[0] == enemyX && snakeylength[0] == enemyY) {

			newEnemy();

			lengthOfSnake++;

			score++;

		}

	}

	@Override

	public void keyPressed(KeyEvent e) {

		// TODO Auto-generated method stub

		int keyCode = e.getKeyCode();

		switch (keyCode) {

		case KeyEvent.VK_SPACE:

			deleteData();

			restart();

			resetGame();

			flag = true;

			replay = true;

			break;

		case KeyEvent.VK_LEFT:

			if (!right) {

				left = true;

				right = false;

				up = false;

				down = false;

				moves++;

			}

			break;

		case KeyEvent.VK_RIGHT:

			if (!left) {

				left = false;

				right = true;

				up = false;

				down = false;

				moves++;

			}

			break;

		case KeyEvent.VK_UP:

			if (!down) {

				left = false;

				right = false;

				up = true;

				down = false;

				moves++;

			}

			break;

		case KeyEvent.VK_DOWN:

			if (!up) {

				left = false;

				right = false;

				up = false;

				down = true;

				moves++;

			}

			break;

		case KeyEvent.VK_R:

			DataSaveToDB();

			moves++;

			resetGame();

			getData();

			restart();

			replayGame();

			break;

		case KeyEvent.VK_S:

			restart();

			resetGame();

			flag = true;

			break;

		case KeyEvent.VK_Q:

			int choice = JOptionPane.showConfirmDialog(this, "Do you want to quit the game?", "Quit Game",

					JOptionPane.YES_NO_OPTION);

			if (choice == JOptionPane.YES_OPTION) {

				closeGameWindow();

			}

			break;

		}

	}

	// Method to close the game window

	public void closeGameWindow() {

		// Get the top-level parent container (JFrame) of this panel

		Container container = this.getTopLevelAncestor();

		if (container instanceof JFrame) {

			JFrame frame = (JFrame) container;

			frame.dispose(); // Close the JFrame

		}

	}

	public void DataSaveToDB() {

		if (replay) {

			for (int i = 0; i < gameStates.size(); i++) {

				GameState state = gameStates.get(i);

				// Set the game state to the recorded state

				saveData(state.snakeX.clone(), state.snakeY.clone(), state.enemyX, state.enemyY, state.moves,

						state.score, state.lengthOfSnake, state.left, state.right, state.up, state.down);

			}

			replay = false;

		}

		flag = false;

	}

	// int a=0;

	Timer replayTimer;

	public void replayGame() {

		if (!gameStates.isEmpty() && !gameOver) {

			// Reset the game to its initial state

			// Start a timer to replay the game

			replayTimer = new Timer(delay, new ActionListener() {

				@Override

				public void actionPerformed(ActionEvent e) {

					if (currentStateIndex < gameStates.size() - 1) {

						currentStateIndex++;

						GameState state = gameStates.get(currentStateIndex);

						// Set the game state to the recorded state

						snakexlength = state.snakeX.clone();

						snakeylength = state.snakeY.clone();

						enemyX = state.enemyX;

						enemyY = state.enemyY;

						moves = state.moves;

						score = state.score;

						lengthOfSnake = state.lengthOfSnake;

						left = state.left;

						right = state.right;

						up = state.up;

						down = state.down;

						// System.out.println(snakexlength+" "+snakeylength+" "+enemyX+" "+enemyY+"

						// "+moves+" "+score+" "+lengthOfSnake+" "+left+" "+right+" "+up+" "+down);

						collidesWithBody();

						repaint();

					} else {

						// End replay when all states have been replayed

						((Timer) e.getSource()).stop();

					}

					if (gameOver) {

						((Timer) e.getSource()).stop();

					}

				}

			});

			replayTimer.start();

		}

	}

	public void resetGame() {

		gameStates.clear();

		currentStateIndex = -1;

		gameOver = false;

		flag = true;

		replay = true;

		moves = 0;

		score = 0;

		lengthOfSnake = 3;

		left = false;

		right = true;

		up = false;

		down = false;

		timer.start();

		repaint();

		newEnemy();

	}

	public void restart() {

		// TODO Auto-generated method stub

		gameOver = false;

		moves = 0;

		score = 0;

		lengthOfSnake = 3;

		left = false;

		right = true;

		up = false;

		down = false;

		timer.start();

		repaint();

		newEnemy();

	}

	@Override

	public void keyReleased(KeyEvent e) {

		// TODO Auto-generated method stub

	}

	@Override

	public void keyTyped(KeyEvent e) {

		// TODO Auto-generated method stub

	}

	public class GameState {

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

		GameState(int[] snakeX, int[] snakeY, int enemyX, int enemyY, int moves, int score, int lengthOfSnake,

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

	public static void main(String[] args) {

		// Ask the user to choose a screen size

		String[] options = { "Small (900x700)", "Medium (1100x800)", "Custom" };

		int choice = JOptionPane.showOptionDialog(null, "Choose a screen size", "Snake Game",

				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		int windowWidth, windowHeight;

		// Set the window dimensions based on the user's choice

		switch (choice) {

		case 0:

			windowWidth = 900;

			windowHeight = 700;

			break;

		case 1:

			windowWidth = 1100;

			windowHeight = 800;

			break;

		default:

			// Custom size

			String widthInput = JOptionPane.showInputDialog("Enter the width of the game window (greater than 1100):");

			String heightInput = JOptionPane.showInputDialog("Enter the height of the game window (greater than 800):");

			windowWidth = Integer.parseInt(widthInput);

			windowHeight = Integer.parseInt(heightInput);

			if (windowWidth <= 1100 || windowHeight <= 800) {

				JOptionPane.showMessageDialog(null, "Custom size must be greater than 1100x800.");

				System.exit(0);

			}

		}

		// Create the game window with the specified dimensions

		JFrame frame = new JFrame("Snake Game");

		frame.setBounds(10, 10, windowWidth, windowHeight);

		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the GamePanel and add it to the frame

		GamePanel panel = new GamePanel(windowWidth, windowHeight);

		panel.setBackground(Color.DARK_GRAY);

		frame.add(panel);

		// Make the frame visible

		frame.setVisible(true);

	}

}