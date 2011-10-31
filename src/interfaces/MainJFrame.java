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
	
	int iconSize = 60;
	
	private GridPanel gridPanel;
	private SettingsPanel settingsPanel;
	
	Environment myEnv;
	Agent agent;
	
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
		
		settingsPanel = new SettingsPanel();
		getContentPane().add(settingsPanel);
		settingsPanel.setSize(290,290);
		
		int l = 7, w = 7;
		agent = new Agent(0,0,l,w,Agent.VisibilityType.MY_NEIGHBOURS);
		myEnv = new Environment(l,w,agent,DynamicType.STATIC);
		
		gridPanel = new GridPanel(myEnv, iconSize);
		getContentPane().add(gridPanel);
		gridPanel.setSize(290,290);
		
		pack();
		this.setVisible(true);
	}

	public void init(){
		
		
		myEnv.show();
		gridPanel.gridPanelUpdate(myEnv);
		while(!agent.goalReached() && agent.actionList.size()<myEnv.opBound){
			agent.perceives(myEnv.getPerceptions());
			agent.update();
			myEnv.getAction(agent.action());
			System.out.println("Action received: " + myEnv.currAction);
			myEnv.update();
			myEnv.show();
			gridPanel.gridPanelUpdate(myEnv);
			System.out.println("-------------------");
		}
		System.out.println("Num actions: " + agent.actionList.size());
		agent.showActions();
		System.out.println("Performance: " + myEnv.performanceMeasure() );
		System.out.println("-- End --");
	}

	
}
