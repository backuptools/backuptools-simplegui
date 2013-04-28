
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import ch.fetm.backuptools.common.NodeDatabase;

public class RootTreeNodeDatabases implements TreeNode {
	List<TreeNodeNodeDatabase> nodedatabases;
	
	public RootTreeNodeDatabases(){
		nodedatabases = new ArrayList<TreeNodeNodeDatabase>();
	}
	
	public TreeNode getChildAt(int childIndex) {
		return nodedatabases.get(childIndex);
	}

	public int getChildCount() {
		return nodedatabases.size();
	}

	public TreeNode getParent() {
		return null;
	}

	public int getIndex(TreeNode node) {
		return nodedatabases.indexOf(node);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return false;
	}
	
	public Enumeration children() {
		return Collections.enumeration(nodedatabases);
	}

	public void addDatabase(NodeDatabase database) {
	  	nodedatabases.add(new TreeNodeNodeDatabase(this, database));
	}
	
	@Override
	public String toString() {
		return "Database";
	}

	public void clear() {
		nodedatabases.clear();
	}

}
