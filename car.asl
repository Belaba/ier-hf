// Agent car in project ier_hf.mas2j

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true
	<- .wait(2000);
		.send(controller, tell, want_to_cross(ns));
		.print("told:  want_to_cross(ns)");
		.wait(2000);
		.send(controller, tell, want_to_cross(we));
		.print("told:  want_to_cross(we)");
		.wait(2000);
		.send(controller, untell, want_to_cross(we));
		.print("untold:  want_to_cross(we)");
		.wait(2000);
		.send(controller, untell, want_to_cross(ns))
		.print("untold:  want_to_cross(ns)");
		.
