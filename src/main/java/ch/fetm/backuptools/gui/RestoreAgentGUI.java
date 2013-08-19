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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;

import ch.fetm.backuptools.common.BackupAgentDirectoryVault;
import ch.fetm.backuptools.common.model.Backup;
import ch.fetm.backuptools.common.model.TreeInfo;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;


public class RestoreAgentGUI extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7824855057171744435L;
	
	private JTable table;
	private String restore_path;
	private List<Backup> backups;
	private BackupAgentDirectoryVault agent;
	private JScrollPane scrollPane;

	private void onClickCancel() {
		closeWindow();
	}

	private void closeWindow() {
		RestoreAgentGUI.this.setVisible(false);
        RestoreAgentGUI.this.dispatchEvent( 
        		new WindowEvent( RestoreAgentGUI.this,
        						 WindowEvent.WINDOW_CLOSING));
	}

	private JTable getTable() {
		return table;
	}

	public RestoreAgentGUI(BackupAgentDirectoryVault agent) {
		setTitle("Select your backup for restoring");
		buildInterfaceAndSubscribeEvent();	
		
		this.agent = agent;
		backups = agent.getBackups();
		BackupTableModel model = new BackupTableModel(backups);
		getTable().setModel(model);
		{
			JPopupMenu popupMenu = new JPopupMenu();
			addPopup(table, popupMenu);
			{
				JMenuItem mntmExplore = new JMenuItem("Explore");
				mntmExplore.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						RestoreAgentGUI.this.onClickExplore();
					}
				});
				popupMenu.add(mntmExplore);
			}
			{
				JMenuItem mntmRestore = new JMenuItem("restore");
				mntmRestore.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						RestoreAgentGUI.this.onClickRestore();
					}
				});
				popupMenu.add(mntmRestore);
			}
		}
	}

	protected void onClickExplore() {
		Backup backup = RestoreAgentGUI.this.backups.get(getTable().getSelectedRow());
		JFrameBackupExplorer explorer = new JFrameBackupExplorer(agent, backup);
		explorer.setVisible(true);
	}

	protected void onClickRestore() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION){
			restore_path = fileChooser.getSelectedFile().toPath().toAbsolutePath().toString();
			Backup backup = RestoreAgentGUI.this.backups.get(getTable().getSelectedRow());
			TreeInfo tree = new TreeInfo();
			tree.name = backup.getName();
			tree.SHA  = backup.getName();
			tree.type = TreeInfo.TYPE_TREE;
			RestoreAgentGUI.this.agent.restore(tree,restore_path);
		}
	}

	private void buildInterfaceAndSubscribeEvent() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
		                onClickCancel();
					}
				});
				cancelButton.setHorizontalAlignment(SwingConstants.RIGHT);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			table = new JTable();
			scrollPane.setViewportView(table);
			table.setSurrendersFocusOnKeystroke(true);
			table.setBorder(null);
			table.setFillsViewportHeight(true);
		}
		{

		}
	}
	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
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
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
