
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

package ch.fetm.backuptools.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ch.fetm.backuptools.common.BackupAgent;
import ch.fetm.backuptools.common.NodeDatabase;
import ch.fetm.backuptools.gui.SimpleGUI;

public class BackupApplication {
	private BackupAgent agent;
	private List<NodeDatabase> databases = new ArrayList<NodeDatabase>();
	private List<SimpleGUI> guis = new ArrayList<SimpleGUI>();

	private void doUpdateDatabaseList() {
		for(SimpleGUI gui : guis){
			gui.doUpdateDatabaseList(databases);
		}
	}
	
	
	public BackupApplication(){
		agent = new BackupAgent(null);
	
	}
	
	public void addVaultDirectory(File selectedFile) {
		NodeDatabase database = new NodeDatabase(selectedFile.getPath());
		databases.add(database);
		doUpdateDatabaseList();
	}


	public void subscribeBackupApplicationListener(SimpleGUI gui) {
		guis.add(gui);
		gui.doUpdateDatabaseList(databases);
	}
	
	public void unsubscribeApplicationListener(SimpleGUI gui){
		guis.remove(gui);
	}


	public BackupAgent getBackupAgent() {
		return agent;
	}

}
