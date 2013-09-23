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
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import ch.fetm.backuptools.common.BackupAgentDirectoryVault;
import ch.fetm.backuptools.common.model.Backup;
import ch.fetm.backuptools.common.model.TreeInfo;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class JFrameBackupExplorer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3269076354741757127L;
	private BackupAgentDirectoryVault agent;
	private JTree tree;
	

	public JFrameBackupExplorer(BackupAgentDirectoryVault agent, Backup backup) {
		this.agent  = agent;
		getContentPane().setLayout(new BorderLayout(0, 0));
		setBounds(100, 100, 532, 448);
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tree = new JTree();
		scrollPane.setViewportView(tree);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tree, popupMenu);
		
		JMenuItem mntmRestore = new JMenuItem("Restore");
		mntmRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION){
					String restore_path = fileChooser.getSelectedFile().toPath().toAbsolutePath().toString();
					
					List<TreeInfo> trees = new ArrayList<TreeInfo>();
					trees.add((TreeInfo)getTree().getLastSelectedPathComponent());
					JFrameBackupExplorer.this.agent.restore(trees,restore_path);
				}
			}
		});
		popupMenu.add(mntmRestore);
		
		
		getTree().updateUI();
		setBackup(agent, backup);
	}
	
	public void setBackup(BackupAgentDirectoryVault agent,Backup backup){
		getTree().setModel(new TreeModelBackup(agent, backup));
	}

	protected JTree getTree() {
		return tree;
	}
	
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			
			private void showMenu(MouseEvent e) {
                int selectedRow = tree.getRowForLocation(e.getX(), e.getY());  
                if (selectedRow != -1) {  
                    if (SwingUtilities.isRightMouseButton(e) == true) {  
                        tree.setSelectionRow(selectedRow);  
                        popup.show(tree, e.getX(), e.getY());  
                    }  
                } 
			}
		});
	}
}
