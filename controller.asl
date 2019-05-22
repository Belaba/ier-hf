// Agent controller in project ier_hf.mas2j

/* Initial beliefs and rules */
free(ns).
free(we).

lastPriority(ns). // Last dir given priority to

/* Initial goals */

!run.
/* Plans */

+want_to_cross[source(Dir)] : true
	<- 
	.print("+ -> ", Dir);
	if ( Dir = ns) { -free(we);}
	else { -free(ns); }
		
.

-want_to_cross[source(Dir)] : true
	<-
	.print("- -> ", Dir);
	if ( Dir = ns) { +free(we);}
	else { +free(ns); }
.


/*
+accident : true
	<-
	.print("Accident happened, shutting down traffic");
	.drop_intention(run);
	.wait(3000);
	.print("Accident solved, continuing traffic");
	//.add_plan(run);
	!run;
	.drop_event(accident);
.

	*/
+!run : accident
	<-
	.print("Accident happened, traffic shut down!");
	.wait(500);
	/* .wait(3000);
	.print("Accident resolved, continuing traffic");
	-accident; */
	!run
	.
	
+!run : true
	<-
	.findall(A, free(A), L);
	for ( .member(I,L) ) {
		!let_trough(I);
	}
	.wait(500);
	/*if (accident) {
		.print("Accident happened, shutting down traffic");
		.wait(3000);
		-accident;
	}*/
	if ( .empty(L) ) {
		.print("Gridlock");
		!solve_gridlock;
		?lastPriority(Dir);
		.print("Given priority to: ", Dir);
		!let_trough(Dir);
		!resume_gridlock;
	}
	!run
.
		
+!let_trough(X) 
	<- 
	.print("letting trough ", X);
	lettingThrough(X)
.

+!solve_gridlock
	<-
	?lastPriority(LastDir);
	if ( LastDir = ns) { 
		.suspend(let_trough(ns));
		-lastPriority(ns); +lastPriority(we);
	}
	else {
		.suspend(let_trough(we));
		-lastPriority(we); +lastPriority(ns);
	}
.
	
+!resume_gridlock
	<-
	?lastPriority(LastDir);
	if ( LastDir = ns) { .resume(let_trough(ns)); }
	else { .resume(let_trough(we)); }
.
