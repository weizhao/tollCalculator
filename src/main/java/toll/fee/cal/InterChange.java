package toll.fee.cal;

import java.util.Set;

import lombok.Data;

@Data public class InterChange implements Comparable<InterChange>{
	
	private int id;
	private String name;
	private double lat;
	private double lng;
	private Set<Adjacent> routes;
	
	@Override
	public int compareTo(InterChange o) {
		return Integer.compare(this.getId(), o.getId());
	}
}


@Data class Adjacent implements Comparable<Adjacent>{
	private int id;
	private double distance;
	
	@Override
	public int compareTo(Adjacent o) {
		return Integer.compare(this.getId(), o.getId());
	}
}