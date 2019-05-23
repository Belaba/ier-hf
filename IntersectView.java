import jason.environment.grid.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.*;

public class IntersectView extends JFrame {
	
	IntersectModel model;
	IntersectEnv env;
	private IntDraw intDraw;
	private Box box;
	private JButton butCarN, butCarE, butCarS, butCarW, butPedN, butPedE, 
					butPedS, butPedW, butPedCross, butCleanup;
	public JPanel controlPanel = new JPanel();
	public JPanel c1 = new JPanel();
	public JPanel c2 = new JPanel();
	public JPanel c3 = new JPanel();
	public BoxLayout bL1 = new BoxLayout(c1, BoxLayout.Y_AXIS);
	public BoxLayout bL2 = new BoxLayout(c2, BoxLayout.Y_AXIS);
	public BoxLayout bL3 = new BoxLayout(c3, BoxLayout.Y_AXIS);
	public JLabel carLabel, pedLabel;
		

	public IntersectView(IntersectEnv ie) {
		super("Okos keresztezodes");
		intDraw = new IntDraw();
		env = ie;
		setResizable(false);
		initialise();
	}
	
	public void initialise() {
		Container c = getContentPane();
		controlPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10000));
		
		butCarN = new JButton("N");
		butCarE = new JButton("E");
		butCarS = new JButton("S");
		butCarW = new JButton("W");
		butPedN = new JButton("N");
		butPedE = new JButton("E");
		butPedS = new JButton("S");
		butPedW = new JButton("W");
		butPedCross = new JButton("Cross");
		butCleanup = new JButton("Clean up");
		carLabel = new JLabel("Car:");
		pedLabel = new JLabel("Pedestrian:");
		
		box = new Box(BoxLayout.Y_AXIS);
		box.add(intDraw);
		box.add(controlPanel);
		box.add(c1);
		box.add(c2);
		box.add(c3);
		
		c1.add(carLabel);
		c1.add(butCarN);
		c1.add(butCarE);
		c1.add(butCarS);
		c1.add(butCarW);
		c2.add(pedLabel);
		c2.add(butPedN);
		c2.add(butPedE);
		c2.add(butPedS);
		c2.add(butPedW);
		c3.add(butPedCross);
		c3.add(butCleanup);
		controlPanel.add(c1);
		controlPanel.add(c2);
		controlPanel.add(c3);
		
		//box.add(but2);
		
		c.add(box);
		
		setSize(380,560);
		setVisible(true);
		
		
		butCarN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntersectEnv.agents.add(env.new Agent(true, "n"));
            }
        });
		
		butCarE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntersectEnv.agents.add(env.new Agent(true, "e"));
            }
        });
		
		butCarS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntersectEnv.agents.add(env.new Agent(true, "s"));
            }
        });
		
		butCarW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntersectEnv.agents.add(env.new Agent(true, "w"));
            }
        });
		
		butPedN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntersectEnv.agents.add(env.new Agent(false, "n"));
            }
        });
		
		butPedE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntersectEnv.agents.add(env.new Agent(false, "e"));
            }
        });
		
		butPedS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntersectEnv.agents.add(env.new Agent(false, "s"));
            }
        });
		
		butPedW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntersectEnv.agents.add(env.new Agent(false, "w"));
            }
        });
	}
	
	
	
	public void update() {
	   intDraw.update();	
	}
	
}
