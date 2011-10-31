package interfaces;

import java.awt.Color;
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
	int iconSize;
	
	public GridPanel(Environment myEnv, int iconSize){
		super();
		this.iconSize = iconSize;
		gridPanelInit(myEnv);
	}
	
	private void gridPanelInit(Environment myEnv) {
		setLayout(new GridLayout(myEnv.lenght, myEnv.width));
		setBackground(Color.green);
		
		dirtIcon = new ImageIcon(new ImageIcon("img/dirt.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		obstacleIcon = new ImageIcon(new ImageIcon("img/wall.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		tileIcon = new ImageIcon(new ImageIcon("img/tile.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		vacuumIcon = new ImageIcon(new ImageIcon("img/vacuum.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		
		labelMatrix = new JLabel[myEnv.lenght][myEnv.width];
		
		GridBagConstraints constraints = new GridBagConstraints();
		for(int i=0; i<myEnv.lenght; i++)
			for(int j=0; j<myEnv.width; j++){
				constraints.fill = GridBagConstraints.BOTH;
				constraints.gridx = i;
				constraints.gridy = j;
				JLabel label = new JLabel();
				labelMatrix[i][j] = label;
				add(label, constraints);
			}
	}
	
	public void gridPanelUpdate(Environment myEnv) {
		for(int i=0; i<myEnv.lenght; i++)
			for(int j=0; j<myEnv.width; j++){
				if(myEnv.floor.get(i,j) == Square.Type.DIRTY)
					labelMatrix[i][j].setIcon(dirtIcon);
				if(myEnv.floor.get(i,j) == Square.Type.CLEAN)
					labelMatrix[i][j].setIcon(tileIcon);
				if(myEnv.floor.get(i,j) == Square.Type.OBSTACLE)
					labelMatrix[i][j].setIcon(obstacleIcon);
				if(myEnv.agent.x == i && myEnv.agent.y == j)
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
