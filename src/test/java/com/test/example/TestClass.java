
package com.test.example;

import org.junit.Test;

import snakegame.GamePanel;

public class TestClass{
	GamePanel gp=new GamePanel(1000,900);
	
	@Test
	public void GameTest() {
		gp.resetGame();
	}
}

//package com.test.example;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.powermock.api.mockito.PowerMockito.whenNew;
//
//import java.awt.event.KeyEvent;
//
//import javax.swing.ImageIcon;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import snakegame.GamePanel;
//
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(GamePanel.class)
//public class TestClass {
//
//
//    @Mock
//    private ImageIcon mockImageIcon;
//
//
//    private GamePanel gamePanel;
//
//
//    @Before
//    public void setUp() throws Exception {
//
//        whenNew(ImageIcon.class).withNoArguments().thenReturn(mockImageIcon);
//
//        gamePanel = new GamePanel(1200, 1000);
//
//    }
//
//
//
//    @Test
//    public void testGameInitialization() {
//
//        assertNotNull(gamePanel);
//
//    }
//
//
//    @Test
//    public void testSnakeMove() {
//
//        int id = KeyEvent.KEY_PRESSED;
//
//        long when = System.currentTimeMillis();
//
//        int modifiers = 0;
//
//        int keyCode = KeyEvent.VK_RIGHT;
//
//        char keyChar = (char) keyCode;
//
//
//        KeyEvent keyEvent = new KeyEvent(gamePanel, id, when, modifiers, keyCode, keyChar);
//
//
//        gamePanel.keyPressed(keyEvent);
//
//
//        assertTrue(gamePanel.right);
//
//        assertFalse(gamePanel.left);
//
//        assertFalse(gamePanel.up);
//
//        assertFalse(gamePanel.down);
//
//    }
//
//
//
//    @Test
//    public void testCollidesWithEnemy() {
//
//        gamePanel.snakexlength[0] = gamePanel.enemyX;
//
//        gamePanel.snakeylength[0] = gamePanel.enemyY;
//
//        int initialScore = gamePanel.score;
//
//        gamePanel.collidesWithEnemy();
//
//        assertEquals(initialScore + 1, gamePanel.score);
//
//    }
//
//
//    @Test
//    public void testCollidesWithBody() {
//
//        gamePanel.snakexlength = new int[]{100, 125, 150};
//
//        gamePanel.snakeylength = new int[]{100, 100, 100};
//
//        gamePanel.left = true;
//
//        gamePanel.actionPerformed(null);
//
//        assertTrue(gamePanel.gameOver);
//
//    }
//
//
//    @Test
//    public void testGameOverAndRestart() {
//
//        gamePanel.gameOver = true;
//
//        gamePanel.restart();
//
//        assertFalse(gamePanel.gameOver);
//
//        assertEquals(0, gamePanel.score);
//
//        assertEquals(3, gamePanel.lengthOfSnake);
//
//    }
//
//
//    @Test
//    public void testReplayGame() {
//
//        gamePanel.replayGame();
//
//        // Add assertions for replay functionality if needed
//
//    }
//
//}


