#Percolation

##Structure

`src/` contains all the source code, which are written in java.

`bin/` contains the compiled java `class` files.

##To Build this project

```
mkdir bin
javac -cp src/ src/PercolationStats.java -d bin/
javac -cp src/ src/PercolationVisualizer.java -d bin/
```
##To Run/Test
###To run Percolation client
`java -cp bin/ Percolation < /dir/to/test.txt`
###To run Percolation Visualizer
`java -cp bin/ PercolationVisualizer < /dir/to/test.txt`
###To run Percolation Stats
`java -cp bin/ PercolationStats < /dir/to/test.txt`
