package utils;

public class Memory {

	public static void memoryStats() {
	    int mb = 1024 * 1024;
	    // get Runtime instance
	    Runtime instance = Runtime.getRuntime();
	    System.out.println("***** Heap utilization statistics [MB] *****\n");
	    // available memory
	    System.out.println("Total Memory: " + instance.totalMemory() / mb);
	    // free memory
	    System.out.println("Free Memory: " + instance.freeMemory() / mb);
	    // used memory
	    System.out.println("Used Memory: "
	            + (instance.totalMemory() - instance.freeMemory()) / mb);
	    // Maximum available memory
	    System.out.println("Max Memory: " + instance.maxMemory() / mb);
	}

}
