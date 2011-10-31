package interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vacuumCleaner.Environment;
import vacuumCleaner.Square;

public class GridPanel extends JPanel {

	private JLabel[][] labelMatrix;
	private ImageIcon dirtIcon, obstacleIcon, tileIcon, vacuumIcon;
	public int labelSize;
	public static int iconSize = 60;
	private Environment env;
	
	public GridPanel(Environment env){
		super();
		this.env = env;
		init();
		update();
	}
	
	private void init() {
		setLayout(new GridLayout(env.lenght, env.width));
		setBackground(Color.green);
		
		dirtIcon = new ImageIcon(new ImageIcon("img/dirt.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		obstacleIcon = new ImageIcon(new ImageIcon("img/wall.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		tileIcon = new ImageIcon(new ImageIcon("img/tile.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		vacuumIcon = new ImageIcon(new ImageIcon("img/vacuum.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		
		labelMatrix = new JLabel[env.lenght][env.width];
		
		
		for(int i=0; i<env.lenght; i++)
			for(int j=0; j<env.width; j++){
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.fill = GridBagConstraints.BOTH;
				constraints.gridx = i;
				constraints.gridy = j;
				JLabel label = new JLabel();
				label.setPreferredSize(new Dimension(iconSize,iconSize));
				labelMatrix[i][j] = label;
				add(label, constraints);
			}
	}
	
	public void update() {
		for(int i=0; i<env.lenght; i++)
			for(int j=0; j<env.width; j++){
				if(env.floor.get(i,j) == Square.Type.DIRTY)
					labelMatrix[i][j].setIcon(dirtIcon);
				if(env.floor.get(i,j) == Square.Type.CLEAN)
					labelMatrix[i][j].setIcon(tileIcon);
				if(env.floor.get(i,j) == Square.Type.OBSTACLE)
					labelMatrix[i][j].setIcon(obstacleIcon);
				if(env.agent.x == i && env.agent.y == j)
					labelMatrix[i][j].setIcon(vacuumIcon);
			}
		repaint();
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
