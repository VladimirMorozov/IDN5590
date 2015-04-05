package me.vmorozov.cluster.data;

public class RowWithSum {
	
	private int summ;
	private int[] row;
	private int rowId;
	
	public RowWithSum(int summ, int[] row, int rowId) {
		super();
		this.summ = summ;
		this.setRow(row);
		this.setRowId(rowId);
	}
	public int getSumm() {
		return summ;
	}
	public void setSumm(int summ) {
		this.summ = summ;
	}
	public int[] getRow() {
		return row;
	}
	public void setRow(int[] row) {
		this.row = row;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	
}
