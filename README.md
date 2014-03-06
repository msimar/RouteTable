Learning


Day 20140306

Compiling the source code :

find all *.java files and organize them into source.text file

$ find -name "*.java" > source.txt

Compile the java files to generate .class files 

$ javac  @source.txt






Day 20140303

DHT bootstrap

A DHT introduces a new lookup necessary for bootstrap purposes. The
bootstrap is necessary for a SN to take its place in its DHT. This document
propose a model to reuse the first lookup type for this purpose.

As DHT will not replace the DNS but will rather provide an overlay network
for the SIP location service, we can reuse the DNS for other functions
present in P2P SIP architecture, like bootstrapping.

Bootstrapping implies that there is at least one resource available in the
DHT overlay, which can provide the boot-up function for a new SN to join.
This can be another SN. We assume the existing SNs available in the DHT have
equal rights for allowing join and leave of other SN as requested. A
dedicated login server should not be necessary for this task. What is
necessary though is that a new SN using the bootstrap procedure finds an
available other SN for booting.

DNS is used anyway during a bootstrap procedure over the Internet to locate
a host-name of the boot-server for instance. For economic reasons we can
reuse this concept so that the DNS provides an available SN at the time of
the query in the DHT domain requested.

This implies that the DNS server itself must becomes DHT aware and must know
in real-time the topology of the Super Nodes network. This does not require
a change in the DNS protocol but rather requires a DNS server implementation
that can generate responses dynamically based on information obtained from
the DHT rather that static information obtained from a zone file or other
storage container.

Source :: http://ag-projects.com/docs/SIPThor_UsingDNSforDHTlookup.txt



Problem : http://www.cs.helsinki.fi/webfm_send/1332

You	are	given	1024	nodes.	Each	node	has	a	unique	ID	(e.g.	0,	1, 2,	 3	 …	 1023)	 and	 maintains	 a	 routing table	 of	 certain	 size	 in	 order	 to	communicate	with	each	other.

You	 are	 required	 to	 organize	 the	 given	 nodes	 in	 a	 certain	 way	 to	 construct	 a	communication	 overlay.	 You	 can	 freely	 choose	 way	 you	 like	 to	 organize	 them. You	 need	 to	 design	 the	 overlay,	 analyze	 its	 communication	 complexity,	 and	experimentally	verify	the	complexity	on	the	Ukko	cluster.	

Requirements	
1. The	overlay	must	be	able	to	deliver	a	message	correctly	between	any	two	nodes	(e.g.	deliver	a	message	from	node	x	to	node	y).	
2. Node	must	only	use	the	routing	table	it	maintains	for	communication.	Broadcast	is	not	allowed.	Hard-coding	everything	in	the	code	is	not	allowed.	
3. Implement	the	overlay	and	run	it	on	the	Ukko	cluster.	
4. Experimentally	determine	values	for	maximum	routing	table	size	and	the	average	number	of	hops	to	deliver	a	message	between	any	two	nodes.

Documentation	
In	the	documentation,	you	are	required	to	report	the	following	things:	
1. Describe	how	you	organize	the	nodes,	and	why	you	choose	that	way.	 If	you	used	any	references,	cite	the	references	in	the	documents.	
2. Calculate	 the	average	 hops	 to	 deliver	a	message	 between	any	 two	 nodes	in	your	overlay,	from	both	experimental	and	theoretical	perspective.	
3. Calculate	the	minimum,	maximum	and	average	size	of	the	routing	table	of	all	the	nodes	in	the	overlay.	
4. Analyze	the	pros	and	cons	of	your	design,	and	what	is	best	context	it	can	be applied	to.	
5. Present	 results	 of	 your	 experimental	 verification	 and	 compare	 them	 with	your	calculations

The	metrics	we	use	to	evaluate	the solution	are	as	follows:	
1. Quality	and	functionality	of	the	code.	
2. Quality	of	the	documentation.	
3. The	product	H	 *	R.	The	product	must	be	below	387	 to	pass	 the	Problem	(for	 your	 1024	 node	 overlay).	Higher	 values	 result	in	a	 failed	grade.	Values	below	387	do	not	affect	your	grade.	


