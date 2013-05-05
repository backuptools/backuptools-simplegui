
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

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ch.fetm.backuptools.common.BackupAgent;
import ch.fetm.backuptools.common.NodeDatabase;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

public class BackupAgentGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4431378979629507218L;
	private BackupAgent agent;
	private Path source;

	public BackupAgentGUI(BackupAgent backupAgent) {
		agent = backupAgent;
		setBounds(100, 100, 300, 60);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnSelectSource = new JButton("Select source");
		btnSelectSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION){
					source = fileChooser.getSelectedFile().toPath();
				}
			}
		});
		getContentPane().add(btnSelectSource);
		
		JButton btnSelectVault = new JButton("Select vault");
		btnSelectVault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION){
					NodeDatabase data = new NodeDatabase(fileChooser.getSelectedFile().toString());
					agent.setDatabase(data);
				}
			}
		});
		getContentPane().add(btnSelectVault);
		
		JButton btnBackup = new JButton("backup");
		btnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agent.backupDirectory(source);
			}
		});
		getContentPane().add(btnBackup);
	}

}
