// Agent car in project ier_hf.mas2j

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

+my_dir(Dir) : true 
	<-
	.send(Dir, tell, want_to_cross)
.

-my_dir(Dir) : true
	<-
	.send(Dir, untell, want_to_cross)
.	
