// Agent controller in project ier_hf.mas2j

/* Initial beliefs and rules */
free(ns).
free(we).

lastPriority(ns). // Last dir given priority to

/* Initial goals */

!run.
/* Plans */

+want_to_cross(Dir)[source(car)]
	<- if ( Dir = ns) { -free(we);}
		else { -free(ns); }
.

-want_to_cross(Dir)[source(car)]
	<- if ( Dir = ns) { +free(we);}
		else { +free(ns); }
.

		
+!run : true
	<-
	.findall(A, free(A), L);
	for ( .member(I,L) ) {
		!let_trough(I);
	}
	.wait(500);
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
		
+!let_trough(X) <- .print("letting trough ", X).

+!solve_gridlock
	<- ?lastPriority(LastDir);
	if ( LastDir = ns) { 
		.suspend(lest_trough(ns));
		-lastPriority(ns); +lastPriority(we);
	}
	else {
		.suspend(lest_trough(we));
		-lastPriority(we); +lastPriority(ns);
	}
.
	
+!resume_gridlock
	<- ?lastPriority(LastDir);
	if ( LastDir = ns) { .resume(lest_trough(ns)); }
	else { .resume(lest_trough(we)); }
.