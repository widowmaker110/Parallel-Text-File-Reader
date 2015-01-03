Parallel-Text-File-Reader
=========================

Java program implementing Multithreading on text files to find the top 10 most occuring words in a text file more efficiently

author:  Alexander Miller
version: 9/8/2014
 
The idea behind this parallel code is to split up every non-empty line of the text file into
its own thread. While each line is getting its own thread, its accessing a synchronized
list to see if the word already exists. If it exists, it adds to the 'Word' count. If it 
doesnt, it creates a new 'Word'. 
 
Make sure to go to the blue dots on the right side of this window in Eclipse to change the 
file path of your text file, if need be. Default is Desktop.
 
