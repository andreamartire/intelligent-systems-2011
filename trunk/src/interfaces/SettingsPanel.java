package interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 * Implement a JPanel with setting elements to interact with the environment 
 *
 */
import vacuumCleaner.Agent;
import vacuumCleaner.Floor;
import vacuumCleaner.Square;
import vacuumCleaner.AbstractAgent.VisibilityType;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public JFrame mainFrame;
	
	private JPanel dimensionPanel;
	private JTextField sizeField;
	private JLabel sizeLabel;
	
	private JPanel generationPanel;
	private JTextField dirtField;
	private JLabel dirtLabel;
	private JTextField obstaclesField;
	private JLabel obstaclesLabel;
	
	private JPanel agentPanel;
	private JTextField agentEnergyField;
	private JLabel agentEnergylabel;
	private JLabel agentVisibilityLabel;
	private JComboBox agentVisibilityCombobox;
	
	private JPanel commandPanel;
	private JButton refreshButton;
	private JButton controlButton;
	
	private int max_dim = 20;
	private int min_dim = 6;
	/**
	 * 
	 * @param mainFrame
	 */
	public SettingsPanel(final MainJFrame mainFrame) {
		{
			this.mainFrame = mainFrame;
			GridBagLayout jPanel2Layout = new GridBagLayout();
			jPanel2Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			jPanel2Layout.rowHeights = new int[] {1,1,1,1};
			jPanel2Layout.columnWeights = new double[] {0.1};
			jPanel2Layout.columnWidths = new int[] {1};
			
			DocumentListener refreshListener = (new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					refreshButton.setText("Refresh*");
				}
				
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					refreshButton.setText("Refresh*");
				}
				
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					refreshButton.setText("Refresh*");
				}
			});
			
			setLayout(jPanel2Layout);
			{
				dimensionPanel = new JPanel();
				dimensionPanel.setPreferredSize(new Dimension(300,100));
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Size Settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        dimensionPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				
		        /*input field to set the size of the floor*/
				add(dimensionPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				sizeLabel = new JLabel();
				dimensionPanel.add(sizeLabel);
				sizeLabel.setText("Size");
				sizeField = new JTextField();
				dimensionPanel.add(sizeField);
				sizeField.setText("" + mainFrame.env.floor.length);
				sizeField.setPreferredSize(new Dimension(30, 30));
				sizeField.getDocument().addDocumentListener(refreshListener);
			}
			{
				/*setting input fields*/
				generationPanel = new JPanel();
				generationPanel.setPreferredSize(new Dimension(350,110));
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Build Settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        generationPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
		        
				add(generationPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				{
					/*number of obstacles*/
					obstaclesLabel = new JLabel();
					obstaclesLabel.setText("Obstacles");
					obstaclesField = new JTextField();
					obstaclesField.setText("0");
					obstaclesField.setPreferredSize(new Dimension(30, 30));
					obstaclesField.getDocument().addDocumentListener(refreshListener);
					
					/*number of dirty tiles*/
					dirtLabel = new JLabel();
					dirtLabel.setText("Dirt");
					dirtField = new JTextField();
					dirtField.setText("0");
					dirtField.setPreferredSize(new Dimension(30, 30));
					dirtField.getDocument().addDocumentListener(refreshListener);

					generationPanel.add(obstaclesLabel);
					generationPanel.add(obstaclesField);
					generationPanel.add(dirtLabel);
					generationPanel.add(dirtField);
				}
			}
			{
				agentPanel = new JPanel();
				add(agentPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Agent's settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        agentPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
		        
		        agentPanel.setLayout(new GridLayout(3,1));
		        
		        JPanel agentEnergyPanel = new JPanel();
		        agentEnergyPanel.setLayout(new FlowLayout());
		        agentPanel.add(agentEnergyPanel);
		        
		        agentEnergylabel = new JLabel("Energy");
		        agentEnergyPanel.add(agentEnergylabel);
		        agentEnergyField = new JTextField("" + mainFrame.agent.opBound);
		        agentEnergyField.setPreferredSize(new Dimension(30, 30));
		        agentEnergyField.getDocument().addDocumentListener(refreshListener);
		        agentEnergyPanel.add(agentEnergyField);
		        
		        JPanel agentVisibilityPanel = new JPanel();
		        
		        agentPanel.add(agentVisibilityPanel);
		        
		        agentVisibilityLabel = new JLabel("Visibility");

		        Vector<VisibilityType> visTypeVector = new Vector<VisibilityType>();
		        visTypeVector.add(VisibilityType.MY_CELL);
		        visTypeVector.add(VisibilityType.MY_NEIGHBOURS);
		        visTypeVector.add(VisibilityType.ALL);
		        agentVisibilityCombobox = new JComboBox(visTypeVector);
		        agentVisibilityCombobox.setSelectedItem(mainFrame.agent.visType);
		        agentVisibilityCombobox.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						refreshButton.setText("Refresh*");
					}
				});
		        
		        agentVisibilityPanel.setLayout(new FlowLayout());
		        agentVisibilityPanel.add(agentVisibilityLabel);
		        agentVisibilityPanel.add(agentVisibilityCombobox);
			}
			{
				commandPanel = new JPanel();
				commandPanel.setPreferredSize(new Dimension(300,110));
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Commands");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        commandPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				
				add(commandPanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				{   
					/*Refresh current configuration*/
					refreshButton = new JButton();
					commandPanel.add(refreshButton);
					refreshButton.setText("Refresh");
					refreshButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							int size = Integer.parseInt(sizeField.getText());
							int dirt = Integer.parseInt(dirtField.getText());
							int obstacles = Integer.parseInt(obstaclesField.getText());
							int energy = Integer.parseInt(agentEnergyField.getText());
							VisibilityType visType = (VisibilityType) agentVisibilityCombobox.getSelectedItem();
							if(size < min_dim ){
								size = min_dim;
								sizeField.setText("" + min_dim);
								JOptionPane.showMessageDialog(null,"Minimun allowed size is " + min_dim, "Warning", JOptionPane.WARNING_MESSAGE);	
							}
							if(size > max_dim ){
								size = max_dim;
								sizeField.setText("" + max_dim);
								JOptionPane.showMessageDialog(null,"Maximun allowed size is " + max_dim, "Warning", JOptionPane.WARNING_MESSAGE);
							}
							mainFrame.newConfig(size, dirt, obstacles, visType, energy);
							refreshButton.setText("Refresh");
						}
					});
					/*Start simulation of agent*/
					controlButton = new JButton();
					commandPanel.add(controlButton);
					controlButton.setText("Start");
					controlButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(controlButton.getText().equals("Start")){
								controlButton.setText("Stop");
								mainFrame.stopped = false;
								class myThread implements Runnable{
									public void run() {
										mainFrame.mainLoop();
								    }
								}
								new Thread(new myThread()).start();
								
							}
							else{
								mainFrame.stopped = true;
								controlButton.setText("Start");
							}								
						}
					});
				}
			}
		}
	}

}
