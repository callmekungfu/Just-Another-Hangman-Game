package central;
import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class Drawing extends JPanel implements ActionListener{
	MaterialColors mc = new MaterialColors();
	Timer baseTm = new Timer(15, this);
	AffineTransform transform = new AffineTransform();

	public int key = 0;
	public boolean charAnimationFlag = true;
	public Color drawingColor = mc.AMBER;
	private final int WIDTH = 10;
	private final int xPos = 110;
	private final int LEFT_ARM_X = 190;
	private final int HAND_Y = 190;
	private boolean baseFlag = true, stickFlag = true, barFlag = true, stringFlag = false, iniAnimationFlag = true;
	private boolean leftArmFlag = false, rightArmFlag = false, leftLegFlag = false, rightLegFlag = false;
	private int baseX = 0, stickY = 0, barX = 0, stringY = 0,volX = 2, volY = 2, volYX = 2, volXX = 1, volBody = 4;
	private int headHeight = 0, bodyHeight = 0,leftArmX = 190,
			leftArmY = 190,rightArmX = 223,rightArmY = 220,
			leftLegX = 220, leftLegY = 235,rightLegX = 220, rightLegY = 235;
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		g2.drawString("Hello", 0, 0);
		g2.setColor(drawingColor);
		g2.fillRect(xPos, 300, baseX, WIDTH);
		g2.fillRect(xPos+20, 110, WIDTH, stickY);
		g2.fillRect(xPos, 110, barX, WIDTH);
		g2.fillRect(xPos*2, 110, 3, stringY);
		//head
		g2.fillOval(206, 170, 30, headHeight);
		//male body
		g2.fillRect(218, 190, 7, bodyHeight);
		//leg and arms
		g2.setStroke(new BasicStroke(4));
		if(leftArmFlag)
			g2.draw(new Line2D.Float(LEFT_ARM_X, HAND_Y, leftArmX, leftArmY));
		if(rightArmFlag)
			g2.draw(new Line2D.Float(223, 220, rightArmX, rightArmY));
		g2.setStroke(new BasicStroke(5));
		if(leftLegFlag)
			g2.draw(new Line2D.Float(220, 235, leftLegX, leftLegY));
		if(rightLegFlag)
			g2.draw(new Line2D.Float(220, 235, rightLegX, rightLegY));
	}
	public void start(){
		baseTm.start();
	}
	public void end(){
		baseTm.stop();
	}
	public void actionPerformed(ActionEvent e){
		if(iniAnimationFlag)
			iniAnimation();
		if(charAnimationFlag)
			animatePerson();
	}
	private void iniAnimation(){
		if(baseFlag)
			baseX += volX;
		if(stickFlag)
			stickY += volYX;
		if(barFlag)
			barX += volXX;
		if(stringFlag)
			stringY += volY;
		if(baseX==50){
			baseFlag = false;
		}
		if(stickY == 200)
			stickFlag = false;
		if(barX == 120){
			barFlag = false;
			stringFlag = true;
		}
		if(stringY == 60){
			stringFlag = false;
			iniAnimationFlag = false;
			baseTm.stop();
		}
		repaint();
	}
	private void animatePerson(){
		if(key == 1 && headHeight <= 30){
			headHeight += volY;
			if(headHeight == 30)
				baseTm.stop();
		}
		if(key == 2 && bodyHeight <= 50){
			bodyHeight += volBody;
			if(bodyHeight == 50)
				baseTm.stop();
		}
		if(key == 3 && leftArmX <= 218){
			leftArmFlag = true;
			leftArmX += volX;
			leftArmY += volY;
			if(leftArmX == 218)
				baseTm.stop();
		}
		if(key == 4 && rightArmY >= 190){
			rightArmFlag = true;
			rightArmX += volX;
			rightArmY -= volY;
			if(rightArmY == 190)
				baseTm.stop();
		}
		if(key == 5 && leftLegY <= 265){
			leftLegFlag = true;
			leftLegY += volY;
			if(leftLegY == 265)
				baseTm.stop();
		}
		if(key == 5 && leftLegX <= 235){
			leftLegX += volX;
			if(leftLegX == 235)
				baseTm.stop();
		}
		if(key == 6 && rightLegY <= 265){
			rightLegFlag = true;
			rightLegY += volY;
			if(rightLegY == 265)
				baseTm.stop();
		}
		if(key == 6 && rightLegX >= 211){
			rightLegX -= volX;
			if(rightLegX == 211)
				baseTm.stop();
		}
		repaint();
	}
	public void resetCharacter(){
		headHeight = 0; 
		bodyHeight = 0;
		leftArmX = 190;
		leftArmY = 190;
		rightArmX = 223;
		rightArmY = 220;
		leftLegX = 220; 
		leftLegY = 235;
		rightLegX = 220; 
		rightLegY = 235;
		leftArmFlag = false; 
		rightArmFlag = false;
		leftLegFlag = false; 
		rightLegFlag = false;
		repaint();
	}
}
