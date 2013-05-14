
/*	Copyright 2013 Florian Mahon <florian@faivre-et-mahon.ch>
 * 
 *    This file is part of backuptools.
 *    
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.fetm.backuptools.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import ch.fetm.backuptools.common.BackupAgenConfigManager;
import ch.fetm.backuptools.common.BackupAgentConfig;
import ch.fetm.backuptools.common.BackupAgentDirectoryVault;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import java.awt.Toolkit;


public class JDialogBackuptoolsConfiguration extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3636875839442016984L;
	
	private final JPanel contentPanel = new JPanel();
	private BackupAgentConfig config;

	private BackupAgentDirectoryVault agent;
	private JTextField txtSource;
	private JTextField txtVault;

	private void onClickSelectVault() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION){
			String vaultPath = fileChooser.getSelectedFile().toPath().toAbsolutePath().toString();
			getTxtVault().setText(vaultPath);						
		}
	}

	private void onClickSelectSource() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION){
			String sourcePath = fileChooser.getSelectedFile().toPath().toAbsolutePath().toString();
			getTxtSource().setText(sourcePath);
		}
	}
	
	private void onClickCancel() {
		JDialogBackuptoolsConfiguration.this.setVisible(false);
		JDialogBackuptoolsConfiguration.this.dispatchEvent( 
    		new WindowEvent( JDialogBackuptoolsConfiguration.this,
    						 WindowEvent.WINDOW_CLOSING));
	}
	
	private void onClickOk() {
		JDialogBackuptoolsConfiguration.this.config.setSource_path(getTxtSource().getText());
		JDialogBackuptoolsConfiguration.this.config.setVault_path(getTxtVault().getText());
		BackupAgenConfigManager.writeConfigurationInFile(JDialogBackuptoolsConfiguration.this.config);
        agent.setConfiguration(config);
		JDialogBackuptoolsConfiguration.this.setVisible(false);
        JDialogBackuptoolsConfiguration.this.dispatchEvent( 
        		new WindowEvent( JDialogBackuptoolsConfiguration.this,
        						 WindowEvent.WINDOW_CLOSING));
	}

	private void buildInterfaceAndSubscribeEvent() {
		setBounds(100, 100, 565, 183);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("OK");
				
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onClickOk();
					}
				});
				
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {		                
						onClickCancel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Select vault destination", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{271, 100, 0};
			gbl_panel.rowHeights = new int[]{15, 0};
			gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				txtVault = new JTextField();
				txtVault.setEditable(false);
				GridBagConstraints gbc_txtVault = new GridBagConstraints();
				gbc_txtVault.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtVault.insets = new Insets(0, 0, 0, 5);
				gbc_txtVault.gridx = 0;
				gbc_txtVault.gridy = 0;
				panel.add(txtVault, gbc_txtVault);
				txtVault.setColumns(10);
			}
			{
				JButton btnSelectVault = new JButton("Select");
				GridBagConstraints gbc_btnSelectVault = new GridBagConstraints();
				gbc_btnSelectVault.anchor = GridBagConstraints.EAST;
				gbc_btnSelectVault.gridx = 1;
				gbc_btnSelectVault.gridy = 0;
				panel.add(btnSelectVault, gbc_btnSelectVault);
				btnSelectVault.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onClickSelectVault();
					}
				});
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Selection source directory", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{271, 100, 0};
			gbl_panel.rowHeights = new int[]{15, 0};
			gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				txtSource = new JTextField();
				txtSource.setEditable(false);
				GridBagConstraints gbc_txtSource = new GridBagConstraints();
				gbc_txtSource.insets = new Insets(0, 0, 0, 5);
				gbc_txtSource.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtSource.gridx = 0;
				gbc_txtSource.gridy = 0;
				panel.add(txtSource, gbc_txtSource);
				txtSource.setColumns(10);
			}
			{
				JButton btnSelectSource = new JButton("Select");
				btnSelectSource.setToolTipText("Select source directory");
				btnSelectSource.setSelectedIcon(null);
				GridBagConstraints gbc_btnSelectSource = new GridBagConstraints();
				gbc_btnSelectSource.fill = GridBagConstraints.VERTICAL;
				gbc_btnSelectSource.anchor = GridBagConstraints.EAST;
				gbc_btnSelectSource.gridx = 1;
				gbc_btnSelectSource.gridy = 0;
				panel.add(btnSelectSource, gbc_btnSelectSource);
				btnSelectSource.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {				
						onClickSelectSource();	
					}
				});
			}
		}
	}
	
	public JDialogBackuptoolsConfiguration(BackupAgentDirectoryVault agent) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogBackuptoolsConfiguration.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		setTitle("Configuration");

		buildInterfaceAndSubscribeEvent();
		

		

		this.agent = agent;
		config = agent.getConfiguration();
		getTxtVault().setText(config.getVault_path());
		getTxtSource().setText(config.getSource_path());
	}
	protected JTextField getTxtVault() {
		return txtVault;
	}
	protected JTextField getTxtSource() {
		return txtSource;
	}
}
