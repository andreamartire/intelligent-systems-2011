package interfaces;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class SettingsPanel extends JPanel {

	private JPanel commandPanel;
	private JButton StopButton;
	private JButton startButton;
	private JButton generatorButton;
	private JTextField dirtField;
	private JLabel dirtLabel;
	private JTextField obstaclesField;
	private JLabel obstaclesLabel;
	private JLabel widthLabel;
	private JPanel GenerationPanel;
	private JButton refreshButton;
	private JTextField jTextField1;
	private JTextField lenghtField;
	private JLabel lengthLabel;
	private JPanel dimensionPanel;
	
	public SettingsPanel() {
		{
			GridBagLayout jPanel2Layout = new GridBagLayout();
			setBackground(Color.RED);
			setSize(270, 300);
			jPanel2Layout.rowWeights = new double[] {0.1, 0.1, 0.1};
			jPanel2Layout.rowHeights = new int[] {7, 7, 7};
			jPanel2Layout.columnWeights = new double[] {0.1};
			jPanel2Layout.columnWidths = new int[] {7};
			setLayout(jPanel2Layout);
			{
				dimensionPanel = new JPanel();
				
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Size Settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        dimensionPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				
				add(dimensionPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
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
		        
				add(GenerationPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
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
				
				add(commandPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
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
	}

}
