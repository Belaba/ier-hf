import jason.environment.grid.*;

import java.awt.*;
import javax.swing.*;

public class IntersectView extends JFrame {
	
	IntersectModel model;
		
	public IntersectView() {
		super("Okos keresztezõdés");
		
		initialise();
	}
	
	public void initialise() {
		Container c = getContentPane();
		
		JLabel label = new JLabel("asdf");
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(label);
		c.add(box);
		setSize(600,600);
		setVisible(true);
	}
	
	public void update() {
	 //TODO.	
	}
	
}
