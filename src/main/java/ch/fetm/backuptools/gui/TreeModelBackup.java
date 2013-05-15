package ch.fetm.backuptools.gui;

import java.util.ArrayList;
import java.util.List;

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
	public void valueForPathChanged(TreePath path, Object newValue) {}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if(((TreeInfo)parent).type == TreeInfo.TYPE_TREE){
			return agent.getTreeInfosOf(((TreeInfo)parent).SHA).indexOf(child);	
		}else{
			return 0;
		}
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {}

}
