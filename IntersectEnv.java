// Environment code for project ier_hf.mas2j



import jason.asSyntax.*;

import jason.environment.*;

import java.util.logging.*;
import java.util.*;



public class IntersectEnv extends Environment {
	
	public class Agent {
		public boolean car; //auto-e. ha nem, akkor gyalogos. :)
		public int x,y; // koordinatak, 0<= ... < 12*30=360, egyebkent kiert a palyarol, torolni.
		public String dirFrom; //mozgas iranya -> MERROL megy, nem az, hogy merre
		
		public Agent(boolean _car, String _dirFrom) {
			this.car = _car;
			this.dirFrom = _dirFrom;
			
			// kezdo koordinatak meghatarozasa az indulasi irany alapjan
			if (car) {
				switch (this.dirFrom) {
					case "n": x=5*30; y=0; break;
					case "s": x=6*30; y=11*30; break;
					case "w": x=0; y=6*30; break;
					case "e": x=11*30; y=5*30; break;
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
		
		public void move() {
			switch(this.dirFrom) {
				case "s": y-=30; break;
				case "n": y+=30; break;
				case "w": x+=30; break;
				case "e": x-=30; break;
				default: break;
			}
		}
	}

    private Logger logger = Logger.getLogger("ier_hf.mas2j."+IntersectEnv.class.getName());

	public static boolean ltWE = false;
	public static boolean ltNS = false;
	public static List<Agent> agents = new ArrayList<>();

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
		
		agents.add(new Agent(false, "n"));
		agents.add(new Agent(false, "s"));
		
        //addPercept(ASSyntax.parseLiteral("percept(demo)"));
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
		} else {
			logger.info("executing: "+action+", but not implemented!");
		}
        if (true) { // you may improve this condition
			informAgsEnvironmentChanged();
        }
		
		moveAll(); // agensek mozgatasa
		
		gui.update();
		
        return true; // the action was executed with success
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
	
	public static void moveAll() {
		agents.forEach((a) -> a.move());	
	}

}


