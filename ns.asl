// Agent ns in project ier_hf.mas2j

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true
	<-
	.my_name(N);
	.wait(1000);
	.send(controller, tell, want_to_cross);
	.print("told want to cross: ", N);
	.wait(3000);
	.send(controller, untell, want_to_cross);
	.print("untold want to cross: ", N);
.
