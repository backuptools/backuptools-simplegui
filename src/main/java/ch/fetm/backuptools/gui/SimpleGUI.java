
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

import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import ch.fetm.backuptools.common.BackupApplication;
import ch.fetm.backuptools.common.NodeDatabase;
import ch.fetm.backuptools.common.Tree;
import javax.swing.JScrollPane;
import javax.swing.JDesktopPane;
import java.awt.Component;
import javax.swing.Box;

public class SimpleGUI {

	private JFrame frmBackuptoolsSimple;
	private BackupApplication app;
	private JTree treedatabase;
	private RootTreeNodeDatabases rootnode;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleGUI window = new SimpleGUI(new BackupApplication());
					window.frmBackuptoolsSimple.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param application 
	 */
	public SimpleGUI(BackupApplication application) {
		initialize();
		app = application;
		app.subscribeBackupApplicationListener(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBackuptoolsSimple = new JFrame();
		frmBackuptoolsSimple.setTitle("backuptools - simple gui");
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frmBackuptoolsSimple);
		frmBackuptoolsSimple.setBounds(100, 100, 450, 300);
		frmBackuptoolsSimple.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmBackuptoolsSimple.setJMenuBar(menuBar);
		
		JMenu mnBackup = new JMenu("Action");
		menuBar.add(mnBackup);
		
		JMenuItem mntmVaultList = new JMenuItem("show vault");
		mntmVaultList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(fileChooser.showOpenDialog(frmBackuptoolsSimple) == JFileChooser.APPROVE_OPTION){
					app.addVaultDirectory(fileChooser.getSelectedFile());
				}
			}
		});
		mnBackup.add(mntmVaultList);
		
		JMenuItem mntmMakeBackup = new JMenuItem("Make backup");
		mntmMakeBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BackupAgentGUI backupgui = new BackupAgentGUI(app.getBackupAgent());
				backupgui.setVisible(true);
			}
		});
		mnBackup.add(mntmMakeBackup);
		frmBackuptoolsSimple.getContentPane().setLayout(new BorderLayout(0, 0));

		rootnode = new RootTreeNodeDatabases();
		
		JToolBar toolBar = new JToolBar();
		frmBackuptoolsSimple.getContentPane().add(toolBar, BorderLayout.NORTH);
		treedatabase = new JTree(rootnode);
		frmBackuptoolsSimple.getContentPane().add(treedatabase, BorderLayout.WEST);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doUpdateDatabaseList(List<NodeDatabase> databases) {
		rootnode.clear();
		for( NodeDatabase database : databases){
			rootnode.addDatabase(database);
		}
		treedatabase.updateUI();
	}

	protected JTree getTreedatabase() {
		return treedatabase;
	}
}
