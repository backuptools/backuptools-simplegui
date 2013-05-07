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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JComboBox;

import ch.fetm.backuptools.common.Backup;
import ch.fetm.backuptools.common.BackupAgentDirectoryVault;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;


public class RestoreAgentGUI extends JDialog {
	private JTable table;

	private String restore_path;
	private List<Backup> backups;
	/**
	 * Create the dialog.
	 * @param agent 
	 */
	public RestoreAgentGUI(BackupAgentDirectoryVault agent) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						if(fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION){
							restore_path = fileChooser.getSelectedFile().toPath().toAbsolutePath().toString();
							Backup backup = RestoreAgentGUI.this.backups.get(getTable().getSelectedColumn());
							agent.restore(backup,restore_path);
						}	
					}
				});
				okButton.setHorizontalAlignment(SwingConstants.RIGHT);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
		                RestoreAgentGUI.this.setVisible(false);
		                RestoreAgentGUI.this.dispatchEvent( 
		                		new WindowEvent( RestoreAgentGUI.this,
		                						 WindowEvent.WINDOW_CLOSING));
					}
				});
				cancelButton.setHorizontalAlignment(SwingConstants.RIGHT);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{

		}
		{
			table = new JTable();
			getContentPane().add(table, BorderLayout.CENTER);
		}
		
		String header[] ={"data backup"};
		backups = agent.getBackups();
		BackupTableModel model = new BackupTableModel(backups);
		getTable().setModel(model);
		
	}

	
	public JTable getTable() {
		return table;
	}
}
