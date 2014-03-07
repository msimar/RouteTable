Problem Statement 
==============

Check out URL : http://www.cs.helsinki.fi/webfm_send/1332

Requirements 
==============

JDK 6 should be install on the linux/windows machine.

Java API Documentation 
==============

```
  $ PROJECT_ROOT_FOLDER/doc/index.html
```

Configuration
==============

The file **nodelist_global.txt** or **nodelist_local.txt**  can be consider as example template to follow. The global node list can be organized as per the following structure:

```
  120 	ukko020.hpc.cs.helsinki.fi	4420 
  121 	ukko021.hpc.cs.helsinki.fi	4421
  122 	ukko022.hpc.cs.helsinki.fi	4422
  123 	ukko023.hpc.cs.helsinki.fi	4423
  124 	ukko024.hpc.cs.helsinki.fi	4424
  125 	ukko025.hpc.cs.helsinki.fi	4425
  126 	ukko026.hpc.cs.helsinki.fi	4426
  127 	ukko027.hpc.cs.helsinki.fi	4427
```
The local node list can be organized as per the following structure:

```
  0 localhost 40000
  1 localhost 40001
  2 localhost 40002
  3 localhost 40003
  4 localhost 40004
  5 localhost 40005
  6 localhost 40006
  7 localhost 40007
```

The command file can be organized as:

```
<Route>  <Index of Source Node > <Index of Destination Node > <String message >
```

```
Route 1 15 "Hello"
```


Usage
==============

*Compiling the source code :*

find all *.java files and organize them into source.text file

```
  $ find -name "*.java" > source.txt
```

*Compile the java files to generate .class files*

```
  $ javac  @source.txt
```

*Run the Application as:*

```
  $ java App nodelist_local.txt command.txt
```
The first argument should be the node list file and the second argument should be the file containing routing command.


License
==============

RouteTable is released under the **MIT license**. Checkout MIT license for more information. 

Contact me
==============

**Maninder Pal Singh**

  
 


