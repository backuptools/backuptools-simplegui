package ch.fetm.backuptools.gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;

import ch.fetm.backuptools.common.Backup;
import ch.fetm.backuptools.common.BackupAgentDirectoryVault;
import ch.fetm.backuptools.common.TreeInfo;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JFrameBackupExplorer extends JFrame {
	private BackupAgentDirectoryVault agent;
	private Backup backup;
	private JTree tree;
	

	public JFrameBackupExplorer(BackupAgentDirectoryVault agent, Backup backup) {
		this.agent  = agent;
		this.backup = backup;
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
					JFrameBackupExplorer.this.agent.restore(((TreeInfo)getTree().getLastSelectedPathComponent()),restore_path);
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
