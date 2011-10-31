package interfaces;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import vacuumCleaner.Agent;
import vacuumCleaner.Environment;
import vacuumCleaner.Environment.DynamicType;

public class MainJFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar jMenuBar;
	private JMenu menuFile;
	private JMenuItem closeMenuItem;
	
	public GridPanel gridPanel;
	private SettingsPanel settingsPanel;
	
	int rows = 7, cols = 7;
	
	public Environment env;
	Agent agent;
	protected boolean stopped;
	
	public static void main(String[] args) {
		new MainJFrame();
	}
	
	public MainJFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1,2));
		setResizable(false);
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
		
		agent = new Agent(0,0,rows,cols,Agent.VisibilityType.MY_NEIGHBOURS);
		env = new Environment(rows,cols,agent,DynamicType.STATIC);
		
		settingsPanel = new SettingsPanel(this);
		getContentPane().add(settingsPanel);
		
		gridPanel = new GridPanel(env);
		getContentPane().add(gridPanel);
		
		pack();
		this.setVisible(true);
	}

	public void mainLoop(){
		env.show();
		while(!agent.goalReached() && agent.actionList.size()<env.opBound && !stopped){
			agent.perceives(env.getPerceptions());
			agent.update();
			env.getAction(agent.action());
			System.out.println("Action received: " + env.currAction);
			env.update();
			env.show();
			gridPanel.update();
			System.out.println("-------------------");
		}
		System.out.println("Num actions: " + agent.actionList.size());
		agent.showActions();
		System.out.println("Performance: " + env.performanceMeasure() );
		System.out.println("-- End --");
	}

	public void newConfig(int parseInt, int parseInt2) {
		agent = new Agent(0,0,parseInt,parseInt2,agent.visType);
		env = new Environment(parseInt,parseInt2,agent,env.dynType);
		getContentPane().remove(gridPanel);
		gridPanel = new GridPanel(env);
		getContentPane().add(gridPanel);
		pack();
	}
}
