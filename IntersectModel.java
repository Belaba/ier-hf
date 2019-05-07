import jason.environment.grid.GridWorldModel;

public class IntersectModel extends GridWorldModel {
	
	public static final int CAR = 16;
	public static final int PEDESTRIAN = 32;
	
	public static final int GSize = 12;
	
	public IntersectModel() {
		super(GSize, GSize, 20);
		
	}
}
