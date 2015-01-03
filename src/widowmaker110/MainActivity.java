package widowmaker110;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * @author Alexander Miller
 * @version 9/8/2014
 * 
 *  The idea behind this parallel code is to split up every non-empty line of the text file into
 *  its own thread. While each line is getting its own thread, its accessing a synchronized
 *  list to see if the word already exists. If it exists, it adds to the 'Word' count. If it 
 *  doesnt, it creates a new 'Word'. 
 *  
 *  Make sure to go to the blue dots on the right side of this window in Eclipse to change the 
 *  file path of your text file, if need be. Default is Desktop.
 */
public class MainActivity {

	private static List<Word> listOfWords = new ArrayList<Word>();
	private static int numberOfWords;
	private static BufferedReader br;
	private static Scanner scan;
	private static long event1, event2, event3, event4, eventCluster1, eventCluster2;
	
	/**
	 * 
	 * @param String test is the word which is known to the programmer 
	 * thats already in the array. ALl this does is increment the 'Word' Counter
	 */
	private static void countArray(String test){
		for(int i = 0; i < listOfWords.size(); i++){		
			if(listOfWords.get(i).getName().equals(test)){
				// add to how many times it shows up
				listOfWords.get(i).setCount(listOfWords.get(i).getCount()+1);				
			}
		}
	}
	
	/**
	 * 
	 * @param String test is checking to know if the word is in the array
	 * @return Boolean of whether or not the word is in the synchronized list.
	 */
	private static boolean checkArray(String test){
		boolean isFound = false;
		for(int i = 0; i < listOfWords.size(); i++){			
			if(listOfWords.get(i).getName().equals(test)){
				isFound = true;				
			}
		}
		return isFound;
	}
	
	public static void main(String[] args){
		
		numberOfWords = 0;
		
		// parallel thread
		class MyThread extends Thread {
			
			// A simple way to pass the variables from the main and save them 
			// to this thread momentarily. 
			private String info;
			private int order;
			public MyThread(String str, int or) {
		        this.setInfo(str);
		        this.setOrder(or);
		    }
			
			/*
			 * 1. Synchronize the activity to prevent thread crashing
			 * 2. Split string by white spaces
			 * 3. While not exceeding the size of the list, check to see if its white space. If
			 *    it isn't, check the array to see if it exists or if it needs to be added.  	 
			 */
		    public void run() {
		    	
		    	synchronized(listOfWords){
		    		
		    		String[] wordsWithinString = getInfo().split("\\s+");
		    		
		    		
		    		for(int i = 0; i < wordsWithinString.length; i++){
		    			
		    			// without this check, it considers single spaces as characters for later 
		    			// calculations
		    			if(!wordsWithinString[i].isEmpty()){
		    				
		    				numberOfWords++;
		    				
		    				if(!checkArray(wordsWithinString[i])){	
		    					listOfWords.add(new Word(wordsWithinString[i], 1, order));
		    				}
		    				else{
		    					countArray(wordsWithinString[i].trim());
		    				}
		    			}
		    		}
		    	}
		    }

			public String getInfo() {
				return info;
			}

			public void setInfo(String info) {
				this.info = info;
			}

			@SuppressWarnings("unused")
			public int getOrder() {
				return order;
			}

			public void setOrder(int order) {
				this.order = order;
			}
		};		
		
		System.out.println("Enter in the name of your text file (e.g. if your file name is test.txt, enter 'test' without the quotation marks)");
		scan = new Scanner(System.in);
		String file = scan.next();
		
		//TODO make sure to change the file directory appropriately
		File myFile = new File("C:/Users/Alexander/Desktop/" + file + ".txt");
		
		// helps prevents a file not found exception/crash
		while(myFile.exists() == false){
			
			System.out.println("Sorry File not found. Please enter in the name of your text file (e.g. if your file name is test.txt, enter 'test' without the quotation marks)");
			scan = new Scanner(System.in);
			file = scan.next();
			
			//TODO make sure to change the file directory appropriately
			myFile = new File("C:/Users/Alexander/Desktop/" + file + ".txt");
			
		}
		
		event1 = System.currentTimeMillis();
		
		// I found this on the internet (http://www.tutorialspoint.com/java/util/collections_synchronizedlist.htm).
		// I thought it was cool that it automatically synchronized the array so that way I could create neat 
		// little threads inside an array without issues.
		List<MyThread> list = Collections.synchronizedList(new ArrayList<MyThread>());
		scan.close();

		try {
			
			FileReader fr = new FileReader(myFile);
			br = new BufferedReader(fr);			          
			int countOrder = 0;
			String comp = "";
			
			while(true){
				// Read the file for one line at a time
				String temp = br.readLine();	
				
				// need a way out if its the 
				// end of the document
				if(temp == null){
					break;
				}
				//without this, the characters were counting the spaces as words
				if(!temp.isEmpty()){
					temp.trim();
					comp += temp;
				
					// add it to the array of threads with the data
					MyThread m = new MyThread(comp, countOrder);
					list.add(m);
					m.start();
					
					// update the counters and strings
					countOrder++;
					comp = "";
				}
			}
			
			event2 = System.currentTimeMillis();
		
		} catch (IOException e1){
			e1.printStackTrace();
		} finally {
			
			
			// wait for each of the threads to finally finish
			for(int i = 0; i < list.size(); i++){
				try {			
					// max wait, 10 seconds
					list.get(i).join(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			event3 = System.currentTimeMillis();
			
			// sort everything at the last second
			// Only need this for when you're trying to print everything in order
			// of how it looks in the document
//			Comparator<Word> comparator = new Comparator<Word>() {
//			    public int compare(Word c1, Word c2) {
//			    	if (c1.getOrder() > c2.getOrder()) {
//			    		return 1;
//			    	  } else if (c1.getOrder() < c2.getOrder()) {
//			    	    return -1;
//			    	  }  
//			    		return 0;
//			    }
//			};

//    		Collections.sort(listOfWords, comparator);
			
			//sort of the top occurring words
	        Collections.sort(listOfWords, new Comparator<Word>() {
	    		@Override
	    		public int compare(Word arg0, Word arg1) {
	    			if(arg0.getCount() < arg1.getCount()){
	    				return 1;
	    			}else if(arg0.getCount() > arg1.getCount()){
	    				return -1;
	    			}else{
	    				return 0;
	    			}
	    		}
	    	});
	        
	        System.out.println("The top 10 most occuring distinct words in this file: ");
	        int size = 0;
	        if(listOfWords.size()< 10){
	        	size = listOfWords.size();
	        }
	        else{
	        	size = 10;
	        }
	        for(int y = 0; y < size ; y++){
	        	System.out.println(y+1 + ". " + listOfWords.get(y).getName() + ", times: " + listOfWords.get(y).getCount());
	        }
	      
	        event4 = System.currentTimeMillis();
	        eventCluster1 = (event2-event1);     
	        eventCluster2 = (event4-event3);	
	        
	        // finally print out the total time
	        System.out.println("-------------------------------");
	        System.out.println("Number of words: "+ numberOfWords);
			System.out.println("Number of distinct words: " + listOfWords.size());
	        System.out.println("Reading the doc: " + eventCluster1);
	        System.out.println("Sorting the doc: " + eventCluster2);
	        System.out.println("Total time: " + (eventCluster2 + eventCluster1));
		}
		
	}
}
