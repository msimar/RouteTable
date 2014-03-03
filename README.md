
Problem : http://www.cs.helsinki.fi/webfm_send/1332

You	are	given	1024	nodes.	Each	node	has	a	unique	ID	(e.g.	0,	1, 2,	 3	 …	 1023)	 and	 maintains	 a	 routing table	 of	 certain	 size	 in	 order	 to	communicate	with	each	other.

You	 are	 required	 to	 organize	 the	 given	 nodes	 in	 a	 certain	 way	 to	 construct	 a	communication	 overlay.	 You	 can	 freely	 choose	 way	 you	 like	 to	 organize	 them. You	 need	 to	 design	 the	 overlay,	 analyze	 its	 communication	 complexity,	 and	experimentally	verify	the	complexity	on	the	Ukko	cluster.	

Requirements	
1. The	overlay	must	be	able	to	deliver	a	message	correctly	between	any	two	nodes	(e.g.	deliver	a	message	from	node	x	to	node	y).	
2. Node	must	only	use	the	routing	table	it	maintains	for	communication.	Broadcast	is	not	allowed.	Hard-coding	everything	in	the	code	is	not	allowed.	
3. Implement	the	overlay	and	run	it	on	the	Ukko	cluster.	
4. Experimentally	determine	values	for	maximum	routing	table	size	and	the	average	number	of	hops	to	deliver	a	message	between	any	two	nodes.


The	metrics	we	use	to	evaluate	the solution	are	as	follows:	
1. Quality	and	functionality	of	the	code.	
2. Quality	of	the	documentation.	
3. The	product	H	 *	R.	The	product	must	be	below	387	 to	pass	 the	Problem	(for	 your	 1024	 node	 overlay).	Higher	 values	 result	in	a	 failed	grade.	Values	below	387	do	not	affect	your	grade.	


