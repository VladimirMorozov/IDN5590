package me.vmorozov.cluster.data;

/**
 * holds table cell value and row removed information
 * @author Vova
 *
 */
public class ValueAndIsRemoved {
	
	public Integer value;
	public boolean isRemoved;
	public ValueAndIsRemoved(Integer value, boolean isRemoved) {
		super();
		this.value = value;
		this.isRemoved = isRemoved;
	}

	
	
}
