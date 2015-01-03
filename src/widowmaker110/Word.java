package widowmaker110;

public class Word {
	
	private String name;
	private int count;
	private int order;
	
	/**
	 * A 'Word' is a variable which holds two parameters: (String word, int count) where the name is the actual word and count is the number of times it occurred
	 * @param String n is the actual word being evaluated
	 * @param Int c is the number of times it has occurred. By convention, this should be initialized to 1.
	 * @param Int o is a number which helps the ordering system once the parallel is finished
	 */
	public Word(String n, int c, int o){
		this.name = n;
		this.count = c;
		this.order = o;
	}

	/**
	 * 
	 * @return returns an int with the number of times it has occurred in the text document
	 */
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 
	 * @return returns a string with the actual word being evaluated
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
