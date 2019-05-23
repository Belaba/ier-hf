// Environment code for project ier_hf.mas2j



import jason.asSyntax.*;

import jason.environment.*;

import java.util.logging.*;
import java.util.*;
import java.awt.Point;



public class IntersectEnv extends Environment {
	public static boolean ltWE = false;
	public static boolean ltNS = false;
	public static boolean pause = false;
	
	public class Agent {
		public boolean car; //auto-e. ha nem, akkor gyalogos. :)
		public int x,y; // koordinatak, 0<= ... < 12*30=360, egyebkent kiert a palyarol, torolni.
		public String dirFrom; //mozgas iranya -> MERROL megy, nem az, hogy merre
		public String name; // Agens neve
		
		public Agent(boolean _car, String _dirFrom, String _name ) {
			this.car = _car;
			this.dirFrom = _dirFrom;
			this.name = _name;
			
			// kezdo koordinatak meghatarozasa az indulasi irany alapjan
			if (car) {
				switch (this.dirFrom) {
					case "n": x=5*30; y=-30; break;
					case "s": x=6*30; y=12*30; break;
					case "w": x=-30; y=6*30; break;
					case "e": x=12*30; y=5*30; break;
					default: break;
				}
			} else {
				switch (this.dirFrom) {
					case "n": x=4*30-5; y=0; break;
					case "s": x=4*30+5; y=11*30; break;
					case "w": x=0; y=4*30+5; break;
					case "e": x=11*30; y=4*30-5; break;
					default: break;
				}
			}
		}
		
		public void kill()
		{
			removePercept(name, Literal.parseLiteral("my_dir(ns,"+ name +")"));
			removePercept(name, Literal.parseLiteral("my_dir(we,"+ name +")"));
			getEnvironmentInfraTier().getRuntimeServices().killAgent(
			name,
			"controller");
			//IntersectEnv.agents.remove(this);
		}
		
		public void move() {
			boolean megallni = false;
			if (car) { //megallas a pirosnal, auto
				if ( (x == 5*30 && y == 3*30) || (x == 6*30 && y == 7*30) || (x == 3*30 && y == 6*30) || (x == 7*30 && y == 5*30)) {
					if ( (dirFrom == "n" || dirFrom == "s") && !IntersectEnv.ltNS) megallni = true;
					if ( (dirFrom == "e" || dirFrom == "w") && !IntersectEnv.ltWE) megallni = true;
				}
			} else { // ~ gyalogos
				if ( dirFrom == "s" && y == 7*30 && !IntersectEnv.ltNS) megallni = true; 
				if ( dirFrom == "n" && y == 4*30 && !IntersectEnv.ltNS) megallni = true;
				if ( dirFrom == "e" && x == 7*30 && !IntersectEnv.ltWE) megallni = true;
				if ( dirFrom == "w" && x == 4*30 && !IntersectEnv.ltWE) megallni = true;
			}
			
			
			
			int newX = x;
			int newY = y;
			// ha nincs piros, hova mozognank
			switch(this.dirFrom) {
				case "s": newY-=30; break;
				case "n": newY+=30; break;
				case "w": newX+=30; break;
				case "e": newX-=30; break;
				default: break;
			}
			//ellenorizzuk, hogy vannak-e elottunk
			if (IntersectEnv.usedPoints.contains(new Point(newX, newY)))  megallni = true;
			
			if (megallni) { // ha meg kell allni, megallunk
				IntersectEnv.usedPoints.add(new Point(x, y));
				return;
			}
			
			//egyebkent mozgunk
			x = newX;
			y = newY;
			IntersectEnv.usedPoints.add(new Point(newX, newY));
			// NS lock
			if ( (dirFrom == "n" || dirFrom == "s") && ( (x == 5*30 && y == 2*30) || (x == 6*30 && y == 8*30) ) ) {
				addPercept(name, Literal.parseLiteral("my_dir(ns,"+ name +")"));
			}
			// NS unlock
			if ( (dirFrom == "n" || dirFrom == "s") && ( (x == 5*30 && y == 7*30) || (x == 6*30 && y == 3*30) ) ) {
				removePercept(name, Literal.parseLiteral("my_dir(ns,"+ name +")"));
			}
			// WE lock
			if ( (dirFrom == "e" || dirFrom == "w") && ( (x == 2*30 && y == 6*30) || (x == 8*30 && y == 5*30) ) ) {
				addPercept(name, Literal.parseLiteral("my_dir(we,"+ name +")"));
			}
			// WE unlock
			if ( (dirFrom == "e" || dirFrom == "w") && ( (x == 7*30 && y == 6*30) || (x == 3*30 && y == 5*30) ) ) {
				removePercept(name, Literal.parseLiteral("my_dir(we,"+ name +")"));
			}
		}
	}

    public Logger logger = Logger.getLogger("ier_hf.mas2j."+IntersectEnv.class.getName());

	
	public static List<Agent> agents = new ArrayList<>();
	public static List<Point> usedPoints = new ArrayList<>();

	private IntersectModel model;
	private IntersectView gui;

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
		
		/*model = new IntersectModel();
		IntersectView view = new IntersectView(model);
		model.setView(view);*/
		
		gui = new IntersectView(this);
        //addPercept(ASSyntax.parseLiteral("percept(demo)"));
    }
	
	public String addAgent(boolean car)
	{
		try
		{
			if(car)
			{
				String name = getEnvironmentInfraTier().getRuntimeServices().createAgent(
				"car",
				"car.asl",
				null,
				null,
				null,
				null,
				null);
				getEnvironmentInfraTier().getRuntimeServices().startAgent(name);
				return name;
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

    @Override
    public boolean executeAction(String agName, Structure action){

		if (action.getFunctor().equals("lettingThrough")) {
			String dir = action.getTerm(0).toString();
			if (dir.equals("ns")) {
				ltNS = true;
				ltWE = false;
			} else {
				ltNS = false;
				ltWE = true;
			}
		} else if (action.getFunctor().equals("send_pulse")) {
			moveAll(); // agensek mozgatasa
		
			gui.update();
		
		} else {
			logger.info("executing: "+action+", but not implemented!");
		}
        if (true) { // you may improve this condition
			informAgsEnvironmentChanged();
        }
		
		
		
        return true; // the action was executed with success
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
	
	public static void moveAll() {
		if(pause)
		{
			return;
		}
		usedPoints.clear();
		agents.forEach((a) -> a.move());	
	}
	
	public static void killAll() {
		pause = true;
		usedPoints.clear();
		agents.forEach((a) -> a.kill());
		agents.clear();
		pause = false;
	}

}


