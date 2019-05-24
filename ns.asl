
+want_to_cross(CarName)
	<-
	.my_name(N);
	.send(controller, tell, want_to_cross(CarName));
	.print("told want to cross: ", N, " name: ", CarName)
.

-want_to_cross(CarName)
	<-
	.my_name(N);
	.send(controller, untell, want_to_cross(CarName));
	.print("untold want to cross: ", N, " name: ", CarName)
.

+abolish_things 
	<-
	.abolish(want_to_cross(_));
	.
