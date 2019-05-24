
import java.awt.*;
import javax.swing.*;
import java.util.List;

public class IntDraw extends JPanel {
	private int cellWidth, cellHeight, gridSize;
	private Color cCar = Color.blue; // az autok szine
	private Color cPed = Color.orange; // a gyalogosok szine
	
	public IntDraw() {
		update();
		cellWidth = 30;
		cellHeight = 30;
		gridSize = 12;
		setSize(cellWidth*gridSize, cellHeight*gridSize);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for (int row = 0; row<gridSize; row++) {
			for (int col = 0; col<gridSize; col++) {
				if (row == 5 || row == 6 || col == 5 || col == 6) {
					g.setColor(Color.lightGray);
					g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
					g.setColor(Color.gray);
					g.drawRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
				}
				else { // ahol nincs ut, ott feher, es nincs cellakeret.
					g.setColor(Color.white);
					g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
				}
                
				// Zebrak kirajzolasa
				// ~ vizszintes
				if ( (row == 5 || row == 6 ) && col == 4) {
					g.setColor(Color.white);
					for (int z = 0; z < 2; z++) {
						g.fillRect(col*cellWidth + 5,
								   (row * cellHeight + (2*z+1)*(cellHeight/5)),
								   cellWidth-9,
								   (cellHeight / 5));
					}
				}
				
				// ~ fuggoleges
				if ( (col == 5 || col == 6) && row == 4 ) {
					g.setColor(Color.white);
					for (int z = 0; z < 2; z++) {
						g.fillRect((col*cellWidth + (2*z+1)*(cellWidth/5)),
								   row*cellHeight+5,
								   cellWidth/5,
								   cellHeight-9);	
					}
				}			
			}
		}
		
		// Agensek kirajzolasa
		
		List agents = IntersectEnv.agents;
		for (int i=0; i<agents.size(); i++) {
			IntersectEnv.Agent currAgent = (IntersectEnv.Agent)agents.get(i);
			if (currAgent.car) { //ha autot rajzolunk
				if (currAgent.dirFrom == "n" || currAgent.dirFrom == "s")
					g.setColor(new Color(102,102,255));
				else
					g.setColor(cCar);
				g.fillRect(currAgent.x+6, currAgent.y+6, 18, 18);
			} else { // ha gyalogost
				g.setColor(cPed);
				g.fillRect(currAgent.x+12, currAgent.y+12, 7, 7);
			}
			
				
		}
		
		
		// Lampak kirajzolasa
		// ~ NS
		if (IntersectEnv.ltNS) g.setColor(Color.green);
		else g.setColor(Color.red);
		g.fillOval(5*cellWidth, 2*cellHeight+(2*cellHeight/3), 10, 10);
		g.fillOval(7*cellWidth-cellWidth/3, 8*cellHeight, 10, 10);
		g.fillRect(4*cellWidth+9, 5*cellHeight-2, 12, 4);
		g.fillRect(4*cellWidth+9, 7*cellHeight-2, 12, 4);
		
		// ~ WE
		if (IntersectEnv.ltWE) g.setColor(Color.green);
		else g.setColor(Color.red);
		g.fillOval(2*cellWidth+(2*cellWidth/3), 6*cellHeight+(2*cellHeight/3), 10, 10);
		g.fillOval(8*cellWidth, 5*cellHeight, 10, 10);
		g.fillRect(5*cellWidth-2, 4*cellHeight+9, 4, 12);
		g.fillRect(7*cellWidth-2, 4*cellHeight+9, 4, 12);
	}
	
	public void update() {
		repaint();
	}
}
