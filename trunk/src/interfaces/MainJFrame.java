package interfaces;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import vacuumCleaner.Agent;
import vacuumCleaner.Environment;
import vacuumCleaner.Square;
import vacuumCleaner.Environment.DynamicType;

public class MainJFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar jMenuBar;
	private JMenu menuFile;
	private JMenuItem closeMenuItem;
	private JPanel commandPanel;
	private JButton StopButton;
	private JButton startButton;
	private JButton generatorButton;
	private JTextField dirtField;
	private JLabel dirtLabel;
	private JTextField obstaclesField;
	private JLabel obstaclesLabel;
	private JPanel eastPanel;
	private JLabel widthLabel;
	private JPanel GenerationPanel;
	private JButton refreshButton;
	private JTextField jTextField1;
	private JTextField lenghtField;
	private JLabel lengthLabel;
	private JPanel dimensionPanel;
	private JPanel westPanel;
	private JLabel[][] labelMatrix;
	private ImageIcon dirtIcon, obstacleIcon, tileIcon, vacuumIcon;
	int iconSize = 60;
	
	public static void main(String[] args) {
		new MainJFrame();
	}
	
	public MainJFrame() {
		super();
		initGUI();
		init();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1,2));
		this.setSize(new java.awt.Dimension(250 + iconSize*9, iconSize*9));
		this.setResizable(false);
		{
			jMenuBar = new JMenuBar();
			setJMenuBar(jMenuBar);
			{
				menuFile = new JMenu();
				jMenuBar.add(menuFile);
				menuFile.setText("File");
				{
					closeMenuItem = new JMenuItem();
					menuFile.add(closeMenuItem);
					closeMenuItem.setText("Close");
					closeMenuItem.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							System.exit(0);
						}
					});
				}
			}
		}
		{
			westPanel = new JPanel();
			getContentPane().add(westPanel);
			GridBagLayout jPanel2Layout = new GridBagLayout();
			westPanel.setBackground(Color.RED);
			westPanel.setSize(250, iconSize*7);
			jPanel2Layout.rowWeights = new double[] {0.1, 0.1, 0.1};
			jPanel2Layout.rowHeights = new int[] {7, 7, 7};
			jPanel2Layout.columnWeights = new double[] {0.1};
			jPanel2Layout.columnWidths = new int[] {7};
			westPanel.setLayout(jPanel2Layout);
			{
				dimensionPanel = new JPanel();
				
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Size Settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        dimensionPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				
				westPanel.add(dimensionPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				lengthLabel = new JLabel();
				dimensionPanel.add(lengthLabel);
				lengthLabel.setText("Lenght");
				lenghtField = new JTextField();
				dimensionPanel.add(lenghtField);
				lenghtField.setText("7");
				widthLabel = new JLabel();
				dimensionPanel.add(widthLabel);
				widthLabel.setText("Width");
				jTextField1 = new JTextField();
				dimensionPanel.add(jTextField1);
				jTextField1.setText("5");
				refreshButton = new JButton();
				dimensionPanel.add(refreshButton);
				refreshButton.setText("Refresh");
			}
			{
				GenerationPanel = new JPanel();
				
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Build Settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        GenerationPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
		        
				westPanel.add(GenerationPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				{
					obstaclesLabel = new JLabel();
					GenerationPanel.add(obstaclesLabel);
					obstaclesLabel.setText("Obstacles");
					obstaclesField = new JTextField();
					GenerationPanel.add(obstaclesField);
					obstaclesField.setText("8");
					dirtLabel = new JLabel();
					GenerationPanel.add(dirtLabel);
					dirtLabel.setText("Dirt");
					dirtField = new JTextField();
					GenerationPanel.add(dirtField);
					dirtField.setText("7");

					generatorButton = new JButton();
					GenerationPanel.add(generatorButton);
					generatorButton.setText("Generate");
				}
			}
			{
				commandPanel = new JPanel();
				
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Commands");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        commandPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				
				westPanel.add(commandPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				{
					startButton = new JButton();
					commandPanel.add(startButton);
					startButton.setText("Start");
					StopButton = new JButton();
					commandPanel.add(StopButton);
					StopButton.setText("Stop");
				}
			}
		}
		{
			eastPanel = new JPanel();
			getContentPane().add(eastPanel);
			eastPanel.setSize(290,290);
		}
		pack();
		this.setVisible(true);
	}
	
	public void init(){
		int l = 7, w = 7;
		Agent agent = new Agent(0,0,l,w,Agent.VisibilityType.MY_NEIGHBOURS);
		Environment myEnv = Environment.create(l,w,agent,DynamicType.STATIC);
		myEnv.show();
		interfaceInit(myEnv);
		interfaceUpdate(myEnv);
		while(!agent.goalReached() && agent.actionList.size()<myEnv.opBound){
			agent.perceives(myEnv.getPerceptions());
			agent.update();
			myEnv.getAction(agent.action());
			System.out.println("Action received: " + myEnv.currAction);
			myEnv.update();
			myEnv.show();
			interfaceUpdate(myEnv);
			System.out.println("-------------------");
		}
		System.out.println("Num actions: " + agent.actionList.size());
		agent.showActions();
		System.out.println("Performance: " + myEnv.performanceMeasure() );
		System.out.println("-- End --");
	}

	private void interfaceInit(Environment myEnv) {
		eastPanel.setLayout(new GridLayout(myEnv.lenght, myEnv.width));
		eastPanel.setBackground(Color.green);
		
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
				eastPanel.add(label, constraints);
			}
	}

	private void interfaceUpdate(Environment myEnv) {
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
