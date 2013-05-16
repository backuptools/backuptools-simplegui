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

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import ch.fetm.backuptools.common.Backup;
import ch.fetm.backuptools.common.BackupAgentDirectoryVault;
import ch.fetm.backuptools.common.TreeInfo;

public class TreeModelBackup implements TreeModel{
	private BackupAgentDirectoryVault agent;
	private TreeInfo treeinfo;
	
	public TreeModelBackup(BackupAgentDirectoryVault agent, Backup backup){
		this.agent  = agent;
		treeinfo = new TreeInfo();
		treeinfo.name = backup.getDate();
		treeinfo.SHA  = backup.getName();
		treeinfo.type = TreeInfo.TYPE_TREE;
	}
	
	@Override
	public Object getRoot() {
		return treeinfo;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return agent.getTreeInfosOf(((TreeInfo)parent).SHA).get(index);
	}

	@Override
	public int getChildCount(Object parent) {
		if(((TreeInfo)parent).type.equals(TreeInfo.TYPE_TREE)){
			return agent.getTreeInfosOf(((TreeInfo)parent).SHA).size();
		}else{
			return 0;
		}
	}

	@Override
	public boolean isLeaf(Object node) {
		return (((TreeInfo)node).type.equals(TreeInfo.TYPE_BLOB));
	}


	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if(((TreeInfo)parent).type == TreeInfo.TYPE_TREE){
			return agent.getTreeInfosOf(((TreeInfo)parent).SHA).indexOf(child);	
		}else{
			return 0;
		}
	}
	
	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {}

	@Override
	public void addTreeModelListener(TreeModelListener l) {}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {}

}
