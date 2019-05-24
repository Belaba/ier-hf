// Agent car in project ier_hf.mas2j

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

+my_dir(Dir, MyName) : true 
	<-
	.send(Dir, tell, want_to_cross(MyName));
	.print("told", " ", Dir, " ", MyName)
.

-my_dir(Dir, MyName) : true
	<-
	.send(Dir, untell, want_to_cross(MyName));
	.print("untold",  " ", Dir, " ", MyName)
.	
