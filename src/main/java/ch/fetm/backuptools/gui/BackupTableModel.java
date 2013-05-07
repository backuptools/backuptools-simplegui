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
