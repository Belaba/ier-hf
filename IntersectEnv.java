// Environment code for project ier_hf.mas2j



import jason.asSyntax.*;

import jason.environment.*;

import java.util.logging.*;
import java.util.*;



public class IntersectEnv extends Environment {
	
	public class Agent {
		public boolean car; //auto-e. ha nem, akkor gyalogos. :)
		public int x,y; // koordinatak, 0<= ... < 30
		public String dir; //mozgas iranya -> MERRE megy, nem az, hogy merrol
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
		
		gui = new IntersectView();
		
		
        //addPercept(ASSyntax.parseLiteral("percept(demo)"));

    }



    @Override

    public boolean executeAction(String agName, Structure action) {

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

		gui.update();
		
        return true; // the action was executed with success

    }



    /** Called before the end of MAS execution */

    @Override

    public void stop() {

        super.stop();

    }

}


