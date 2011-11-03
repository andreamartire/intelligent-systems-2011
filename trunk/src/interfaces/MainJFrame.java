package interfaces;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import vacuumCleaner.AbstractAgent;
import vacuumCleaner.Agent;
import vacuumCleaner.Environment;
import vacuumCleaner.Floor;
import vacuumCleaner.Environment.DynamicType;

public class MainJFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar jMenuBar;
	private JMenu menuFile;
	private JMenuItem closeMenuItem;
	private JMenuItem	openMenuItem;
	
	public GridPanel gridPanel;
	private SettingsPanel settingsPanel;
	
	int rows = 10, cols = 10;
	
	public Environment env;
	AbstractAgent agent;
	protected boolean stopped;
	private JMenuItem	saveMenuItem;
	
	public static void main(String[] args) {
		new MainJFrame();
	}
	
	public MainJFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
//		setResizable(false);
		{
			jMenuBar = new JMenuBar();
			setJMenuBar(jMenuBar);
			{
				menuFile = new JMenu();
				jMenuBar.add(menuFile);
				menuFile.setText("File");
				{
					openMenuItem = new JMenuItem();
					openMenuItem.setText("Open");
					openMenuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Serializzatore<Floor> serializzatore = new Serializzatore<Floor>();
							Floor floor = serializzatore.carica();
							if (floor != null){
								env.floor.load(floor);
								gridPanel.update();
							}
							serializzatore.dispose();
						}
					});
					saveMenuItem = new JMenuItem();
					saveMenuItem.setText("Save");
					saveMenuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Serializzatore<Floor> serializzatore = new Serializzatore<Floor>();
							serializzatore.salva(env.floor);
							serializzatore.dispose();
						}
					});
					closeMenuItem = new JMenuItem();
					closeMenuItem.setText("Close");
					closeMenuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							System.exit(0);
						}
					});
					menuFile.add(openMenuItem);
					menuFile.add(saveMenuItem);
					menuFile.addSeparator();
					menuFile.add(closeMenuItem);
				}
			}
		}
		
		agent = new Agent(0,0,rows,cols,Agent.VisibilityType.MY_NEIGHBOURS,10);
		env = new Environment(rows,cols,agent,DynamicType.STATIC);
		
		settingsPanel = new SettingsPanel(this);
		getContentPane().add(settingsPanel, BorderLayout.WEST);
		
		gridPanel = new GridPanel(env);
		getContentPane().add(gridPanel, BorderLayout.EAST);
		
		pack();
		this.setVisible(true);
	}

	public void mainLoop(){
		env.show();
		while(!agent.goalReached() && !stopped){
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
		agent = new Agent(0,0,parseInt,parseInt2,agent.visType, 100);
		env = new Environment(parseInt,parseInt2,agent,env.dynType);
		getContentPane().remove(gridPanel);
		gridPanel = new GridPanel(env);
		getContentPane().add(gridPanel);
		pack();
	}
}
