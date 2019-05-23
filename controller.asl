// Agent controller in project ier_hf.mas2j

/* Initial beliefs and rules */
free(ns).
free(we).

priority(ns).
//priority(we).

lastPriority(ns). // Last dir given priority to

/* Initial goals */

!run.
!pulse.
/* Plans */

+want_to_cross(CarName)[source(Dir)] : true
	<- 
	.print("+ -> ", Dir);
	if ( Dir = ns) { -free(we);}
	else { -free(ns); }
		
.

-want_to_cross(CarName)[source(Dir)] : true
	<-
	.print("- -> ", Dir);
	if ( Dir = ns) { +free(we);}
	else { +free(ns); }
.

/* -want_to_cross(CarName)[source(Dir)] : true
	<-
	.print("- -> ", Dir);
	.findall(_, want_to_cross(_)[source(ns)], L);
	if( .empty(L)) {
		.print("ns ures");
		+free(we);
	} else {
		.print("ns nem ures, L: ", L);
	}
	.findall(_, want_to_cross(_)[source(we)], L);
	if( .empty(L)) {
	.print("we ures");
		+free(ns);
	} else {
		.print("we nem ures, L: ", L);
	}
. */



+!run : accident
	<-
	.print("Accident happened, traffic shut down!");
	.wait(500);
	!run
.

+!pulse : true
	<-
	send_pulse;
	.print("pulse");
	.wait(1000);
	!pulse;
.
	
+!run : true
	<-
	.findall(A, free(A), L);
	for ( .member(I,L) ) {
		!let_trough(I);
		.wait(200);
	}

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
	//.print("letting trough ", X);
	lettingThrough(X)
.

+!solve_gridlock : true
	<-
	?priority(A);
	.my_name(MyName);
	if ( A = ns) {
		.print("here");
		.abolish(free(we));
		//free(ns);
		.send(MyName, tell, free(ns));
	} else {
		.abolish(free(ns));
		//free(we);
		.send(MyName, tell, free(we));
	}
	//?lastPriority(LastDir);
	// if ( LastDir = ns) { 
		// .suspend(let_trough(ns));
		// -lastPriority(ns); +lastPriority(we);
	// }
	// else {
		// .suspend(let_trough(we));
		// -lastPriority(we); +lastPriority(ns);
	// }
.
	
+!resume_gridlock
	<-
	?lastPriority(LastDir);
	if ( LastDir = ns) { .resume(let_trough(ns)); }
	else { .resume(let_trough(we)); }
.
