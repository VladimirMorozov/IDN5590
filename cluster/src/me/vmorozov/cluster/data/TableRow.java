package me.vmorozov.cluster.data;

public class TableRow {
	
	private int sum;
	private int[] row;
	private int rowId;
	private int removalOrder;
	
	public TableRow(int sum, int[] row, int rowId, int removalOrder) {
		super();
		this.sum = sum;
		this.setRow(row);
		this.setRowId(rowId);
		this.removalOrder = removalOrder;
	}
	public int getSum() {
		return sum;
	}
	public void setSumm(int summ) {
		this.sum = summ;
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
	public int getRemovalOrder() {
		return removalOrder;
	}
	public void setRemovalOrder(int removalOrder) {
		this.removalOrder = removalOrder;
	}

	
}
