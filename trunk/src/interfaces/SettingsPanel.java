package interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import vacuumCleaner.Agent;
import vacuumCleaner.Environment;
import vacuumCleaner.Floor;
import vacuumCleaner.Square;
import vacuumCleaner.Environment.DynamicType;

public class SettingsPanel extends JPanel {

	public JFrame mainFrame;
	
	private JPanel commandPanel;
	private JButton controlButton;
	private JButton generatorButton;
	private JTextField dirtField;
	private JLabel dirtLabel;
	private JTextField obstaclesField;
	private JLabel obstaclesLabel;
	private JPanel generationPanel;
	private JButton refreshButton;
	private JTextField sizeField;
	private JLabel sizeLabel;
	private JPanel dimensionPanel;
	
	public SettingsPanel(final MainJFrame mainFrame) {
		{
			this.mainFrame = mainFrame;
			GridBagLayout jPanel2Layout = new GridBagLayout();
			jPanel2Layout.rowWeights = new double[] {0.1, 0.1, 0.1};
			jPanel2Layout.rowHeights = new int[] {1,1,1};
			jPanel2Layout.columnWeights = new double[] {0.1};
			jPanel2Layout.columnWidths = new int[] {1};
			setLayout(jPanel2Layout);
			{
				dimensionPanel = new JPanel();
				dimensionPanel.setPreferredSize(new Dimension(300,100));
//				dimensionPanel.setBackground(Color.cyan);
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Size Settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        dimensionPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				
				add(dimensionPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				sizeLabel = new JLabel();
				dimensionPanel.add(sizeLabel);
				sizeLabel.setText("Size");
				sizeField = new JTextField();
				dimensionPanel.add(sizeField);
				sizeField.setText("10");
				
				refreshButton = new JButton();
				dimensionPanel.add(refreshButton);
				refreshButton.setText("Refresh");
				refreshButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int size = Integer.parseInt(sizeField.getText());
						if(size < 6 ){
							size = 6;
							sizeField.setText("6");
						}
						if(size > 20 ){
							size = 20;
							sizeField.setText("20");
						}
						mainFrame.newConfig(size,size);
					}
				});
			}
			{
				generationPanel = new JPanel();
				generationPanel.setPreferredSize(new Dimension(350,110));
//				generationPanel.setBackground(Color.blue);
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Build Settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        generationPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
		        
				add(generationPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				{
					obstaclesLabel = new JLabel();
					generationPanel.add(obstaclesLabel);
					obstaclesLabel.setText("Obstacles");
					obstaclesField = new JTextField();
					generationPanel.add(obstaclesField);
					obstaclesField.setText("0");
					obstaclesField.setPreferredSize(new Dimension(30, 30));
					
					dirtLabel = new JLabel();
					generationPanel.add(dirtLabel);
					dirtLabel.setText("Dirt");
					dirtField = new JTextField();
					generationPanel.add(dirtField);
					dirtField.setText("0");
					dirtField.setPreferredSize(new Dimension(30, 30));

					generatorButton = new JButton();
					generationPanel.add(generatorButton);
					generatorButton.setText("Generate");
					generatorButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							int dirt = Integer.parseInt(dirtField.getText());
							int obstacles = Integer.parseInt(obstaclesField.getText());
							obstaclesField.setText("0");
							dirtField.setText("0");
							mainFrame.env.floor.generateObject(dirt,obstacles);
							mainFrame.gridPanel.update();
						}
					});
				}
			}
			{
				commandPanel = new JPanel();
				commandPanel.setPreferredSize(new Dimension(200,110));
//				commandPanel.setBackground(Color.black);
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Commands");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        commandPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				
				add(commandPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				{
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
