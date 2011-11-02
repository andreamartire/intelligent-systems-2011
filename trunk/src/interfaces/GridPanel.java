package interfaces;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import vacuumCleaner.Environment;
import vacuumCleaner.Square;
import vacuumCleaner.Square.Type;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel[][] labelMatrix;
	public static ImageIcon dirtIcon, obstacleIcon, tileIcon, vacuumIcon;
	public int labelSize;
	public static int iconSize = 60;
	static Environment env;
	
	static Square.Type currType;
	static ImageIcon currIcon;
	
	public GridPanel(Environment env){
		GridPanel.env = env;
		init();
		update();
	}
	
	private void init() {
		setLayout(new GridLayout(env.lenght, env.width));
		
		dirtIcon = new ImageIcon(new ImageIcon("img/dirt.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		obstacleIcon = new ImageIcon(new ImageIcon("img/wall.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		tileIcon = new ImageIcon(new ImageIcon("img/tile.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		vacuumIcon = new ImageIcon(new ImageIcon("img/vacuum.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		
		labelMatrix = new JLabel[env.lenght][env.width];
		
		currType = Type.DIRTY;
		currIcon = dirtIcon;
		
		for(int i=0; i<env.lenght; i++)
			for(int j=0; j<env.width; j++){
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.fill = GridBagConstraints.BOTH;
				constraints.gridx = i;
				constraints.gridy = j;
				final JLabel label = new JLabel();
				label.setPreferredSize(new Dimension(iconSize,iconSize));
				label.addMouseListener(new ClickHandler(label,i,j));
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
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
