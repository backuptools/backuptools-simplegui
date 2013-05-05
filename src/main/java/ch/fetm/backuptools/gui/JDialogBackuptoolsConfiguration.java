
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
import javax.swing.JLabel;

import ch.fetm.backuptools.common.BackupAgentConfiguration;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;


public class JDialogBackuptoolsConfiguration extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3636875839442016984L;
	
	private final JPanel contentPanel = new JPanel();
	private String sourcePath   = null;
	private String vaultPath    = null;
	private JLabel lblVaultPath;
	private JLabel lblSourcePath;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JDialogBackuptoolsConfiguration dialog = new JDialogBackuptoolsConfiguration(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param b 
	 * @param trayIconBackup 
	 */
	public JDialogBackuptoolsConfiguration(final BackupAgentConfiguration configuration) {
		setBounds(100, 100, 450, 130);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JButton btnSelectVault = new JButton("Select vault");
			btnSelectVault.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if(fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION){
						vaultPath = fileChooser.getSelectedFile().toPath().toAbsolutePath().toString();
						getLblVaultPath().setText(vaultPath);						
					}
				}
			});
			
			GridBagConstraints gbc_btnSelectVault = new GridBagConstraints();
			gbc_btnSelectVault.anchor = GridBagConstraints.WEST;
			gbc_btnSelectVault.insets = new Insets(0, 0, 5, 5);
			gbc_btnSelectVault.gridx = 0;
			gbc_btnSelectVault.gridy = 0;
			contentPanel.add(btnSelectVault, gbc_btnSelectVault);
		}
		{
			lblVaultPath = new JLabel("");
			GridBagConstraints gbc_lblVaultPath = new GridBagConstraints();
			gbc_lblVaultPath.insets = new Insets(0, 0, 5, 0);
			gbc_lblVaultPath.gridx = 1;
			gbc_lblVaultPath.gridy = 0;
			contentPanel.add(lblVaultPath, gbc_lblVaultPath);
		}
		{
			JButton btnSelectSource = new JButton("Select source");
			btnSelectSource.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {				
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if(fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION){
						sourcePath = fileChooser.getSelectedFile().toPath().toAbsolutePath().toString();
						getlblSourcePath().setText(sourcePath);
					}	
				}
			});

			GridBagConstraints gbc_btnSelectSource = new GridBagConstraints();
			gbc_btnSelectSource.anchor = GridBagConstraints.WEST;
			gbc_btnSelectSource.insets = new Insets(0, 0, 0, 5);
			gbc_btnSelectSource.gridx = 0;
			gbc_btnSelectSource.gridy = 1;
			contentPanel.add(btnSelectSource, gbc_btnSelectSource);
		}
		{
			lblSourcePath = new JLabel("");
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.gridx = 1;
			gbc_label.gridy = 1;
			contentPanel.add(lblSourcePath, gbc_label);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						configuration.setSourceLocation(sourcePath);
						configuration.setDatabaseLocation(vaultPath);
						
		                JDialogBackuptoolsConfiguration.this.setVisible(false);
		                JDialogBackuptoolsConfiguration.this.dispatchEvent( 
		                		new WindowEvent( JDialogBackuptoolsConfiguration.this,
		                						 WindowEvent.WINDOW_CLOSING));
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
						JDialogBackuptoolsConfiguration.this.setVisible(false);
						JDialogBackuptoolsConfiguration.this.dispatchEvent( 
	                		new WindowEvent( JDialogBackuptoolsConfiguration.this,
	                						 WindowEvent.WINDOW_CLOSING));
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		vaultPath = configuration.getDatabaseLocation();
		sourcePath = configuration.getSourcelocation();
		getlblSourcePath().setText(configuration.getSourcelocation());
		getLblVaultPath().setText(configuration.getDatabaseLocation());
	}

	public JLabel getLblVaultPath() {
		return lblVaultPath;
	}
	public JLabel getlblSourcePath() {
		return lblSourcePath;
	}


}