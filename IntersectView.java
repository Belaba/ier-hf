import jason.environment.grid.*;

import java.awt.*;
import javax.swing.*;

public class IntersectView extends JFrame {
	
	IntersectModel model;
	
	private IntDraw intDraw;
	private Box box;
	private JButton but1, but2;
		
	public IntersectView() {
		super("Okos keresztezodes");
		intDraw = new IntDraw();
		initialise();
	}
	
	public void initialise() {
		Container c = getContentPane();
		
		but1 = new JButton("Gomb1");
		but2 = new JButton("Gomb2");
		
		box = new Box(BoxLayout.Y_AXIS);
		box.add(intDraw);
		box.add(but1);
		box.add(but2);
		
		c.add(box);
		
		setSize(380,450);
		setVisible(true);
	}
	
	public void update() {
	   intDraw.update();	
	}
	
}
