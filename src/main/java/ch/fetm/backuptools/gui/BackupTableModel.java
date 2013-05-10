
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
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ch.fetm.backuptools.common.Backup;

public class BackupTableModel extends AbstractTableModel {
	private final String[]header  = {"date","signature"};
	private final List<Backup> backups = new ArrayList<Backup>();
	
	public BackupTableModel(List<Backup> backups)
	{
		super();
		this.backups.addAll(backups);
	}

	@Override
	public int getRowCount() {
		return backups.size();
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return backups.get(rowIndex).getDate();	
		case 1:
			return backups.get(rowIndex).getName();
		}
		return null;
	}
	
	@Override
	public String getColumnName(int columnIndex){
		return header[columnIndex];
	}
}
