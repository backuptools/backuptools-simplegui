
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
 
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.*;

import ch.fetm.backuptools.common.BackupAgentConfiguration;
import ch.fetm.backuptools.common.BackupAgentDirectoryVault;
 
public class TrayIconBackup {
	
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
        } catch (Exception e){
        	e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	BackupAgentDirectoryVault agent = new BackupAgentDirectoryVault();
                new TrayIconBackup(agent);
            }
        });
    }


	private BackupAgentDirectoryVault  agent;
	
    private BackupAgentConfiguration config = new BackupAgentConfiguration();
    private TrayIcon trayIcon;
    private SystemTray tray;
    
	private void showConfiguration() {
	 JDialogBackuptoolsConfiguration configuration = new JDialogBackuptoolsConfiguration(config);
	 configuration.setVisible(true);
	}

	public TrayIconBackup(BackupAgentDirectoryVault agent) {
    	this.agent = agent;
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(createImage("bulb.gif", "Backuptools agent"));
        tray = SystemTray.getSystemTray();
         
        MenuItem aboutItem         = new MenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO do nothing
			}
		});
        
        MenuItem configurationItem = new MenuItem("Configuration");
        configurationItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				showConfiguration();
			}

		});
        MenuItem RunItem           = new MenuItem("Run backup");
        RunItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TrayIconBackup.this.agent.setVaultDirectory(config.getDatabaseLocation());
				TrayIconBackup.this.agent.backupDirectory(Paths.get(config.getSourcelocation()));
			}
		});
        MenuItem restoreItem       = new MenuItem("Restore");
        restoreItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showRestoreList();
			}
		});
        MenuItem exitItem          = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitSoftware();
			}
		});
         
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(configurationItem);
        popup.add(RunItem);
        popup.add(restoreItem);
        popup.addSeparator();
        popup.add(exitItem);
         
        trayIcon.setPopupMenu(popup);
         
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
    }
     
    protected void showRestoreList() {
		RestoreAgentGUI restore = new RestoreAgentGUI(agent);		
	}

	protected void exitSoftware() {
		tray.remove(trayIcon);
		System.exit(0);
	}

	protected Image createImage(String path, String description) {
        URL imageURL = getClass().getClassLoader().getResource(path);
         
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}