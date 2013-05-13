
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
 
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ch.fetm.backuptools.common.BackupAgentConfig;
import ch.fetm.backuptools.common.BackupAgentDirectoryVault;
 
public class TrayIconBackup {

    public static void main(String[] args) {	
        setLookAndFeel();        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	BackupAgentConfig config;
            	if(BackupAgenConfigManager.ConfigfileExist()){
            		config = BackupAgenConfigManager.readConfigurationFile();
            	}else{
            		config = new BackupAgentConfig();
            		JDialogBackuptoolsConfiguration dialog = new JDialogBackuptoolsConfiguration(config);
            	}
            	BackupAgentDirectoryVault agent = new BackupAgentDirectoryVault(config);
                new TrayIconBackup(agent);

            }
        });
    }

	private static void setLookAndFeel() {
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
	}


	private BackupAgentDirectoryVault  agent;
	
    private TrayIcon trayIcon;
    private SystemTray tray;
    
	private void onClickConfiguration() {
	 JDialogBackuptoolsConfiguration configuration = new JDialogBackuptoolsConfiguration(agent.getConfiguration());
	 configuration.setVisible(true);
	}

	public TrayIconBackup(BackupAgentDirectoryVault agent) {
    	this.agent = agent;
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(createImage("bulb.gif", "Backuptools agent"));
        tray     = SystemTray.getSystemTray();
         
        MenuItem aboutItem         = new MenuItem("About");
        MenuItem configurationItem = new MenuItem("Configuration");
        MenuItem RunItem           = new MenuItem("Run backup");
        MenuItem restoreItem       = new MenuItem("Restore");
        MenuItem exitItem          = new MenuItem("Exit");
        
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
        
        restoreItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showRestoreList();
			}
		});
        exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitSoftware();
			}
		});
        RunItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TrayIconBackup.this.agent.doBackup();
			}
		});        
        configurationItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickConfiguration();
			}

		});
        aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO do nothing
			}
		});
    }
     
    protected void showRestoreList() {
		RestoreAgentGUI restore = new RestoreAgentGUI(agent);
		restore.setVisible(true);
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