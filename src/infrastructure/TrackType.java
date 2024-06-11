package infrastructure;

public enum TrackType {
	
	TRACK_1100(1,1,0,0),
	TRACK_0011(0,0,1,1),

	TRACK_1000(1,0,0,0),
	TRACK_0100(0,1,0,0),
	TRACK_0010(0,0,1,0),
	TRACK_0001(0,0,0,1),
	
	TRACK_1010(1,0,1,0),
	TRACK_1001(1,0,0,1),
	TRACK_0110(0,1,1,0),
	TRACK_0101(0,1,0,1),
	
	TRACK_1110(1,1,1,0),
	TRACK_0111(0,1,1,1),
	TRACK_1011(1,0,1,1),
	TRACK_1101(1,1,0,1),
	
	TRACK_1111(1,1,1,1);
	

	TrackType(int north, int south, int west, int east) {
		this.north = north;
		this.south = south;
		this.west = west;
		this.east = east;
	}
	
	private int north;
	private int south;
	private int west;
	private int east;
	
	
	public String getFilenamePart() {
		return String.format("%d%d%d%d", this.north, this.south, this.west, this.east);
	}
	
	public static TrackType getForAdjacency(int north, int south, int west, int east) {
		for (TrackType type : TrackType.values()) {
			if(type.north == north && type.south == south && type.west == west && type.east == east ) {
				return type;
			}
		}
		return null;
	}
	
	public static TrackType getFromVector(int deltaX, int deltaY) {
		int north = deltaY == -1 ? 1 : 0;
		int south = deltaY ==  1 ? 1 : 0;
		int west  = deltaX == -1 ? 1 : 0;
		int east  = deltaY ==  1 ? 1 : 0;
		
		return TrackType.getForAdjacency(north, south, west, east);
	}
}
