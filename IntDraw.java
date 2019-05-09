
import java.awt.*;
import javax.swing.*;

public class IntDraw extends JPanel {
	private int cellWidth, cellHeight, gridSize;
	private Color cCar = Color.blue; // az autok szine
	private Color cPed = Color.yellow; // a gyalogosok szine
	
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
				if ( (row == 5 || row == 6 ) && (col == 4 || col == 7)) {
					g.setColor(Color.white);
					for (int z = 0; z < 2; z++) {
						g.fillRect(col*cellWidth + 5,
								   (row * cellHeight + (2*z+1)*(cellHeight/5)),
								   cellWidth-9,
								   (cellHeight / 5));
					}
				}
				
				// ~ fuggoleges
				if ( (col == 5 || col == 6) && (row == 4 || row == 7) ) {
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
	}
	
	public void update() {
		repaint();
	}
}
