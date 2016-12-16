package com.automation.multithreading.test;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class MultiThreadTest {
	
	public static void main(String[] args) {
		MultiThreadTest multiThreadTestObject = new MultiThreadTest();
		multiThreadTestObject.GetInitialSetOfDataAsArrayAndProcess();
	}
	
	public void GetInitialSetOfDataAsArrayAndProcess() {
		
		//Bla bla Code
		ArrayList<String> testArray = new ArrayList<String>();
		PerformMultiThreadOperation(testArray);
		
	}
	
	private void PerformMultiThreadOperation(ArrayList<String> dataArray) {

		System.out.println("Update Operation");
		try {
			System.out.println("Total records to be processed : " + dataArray.size());
			System.out.println("Total records to be processed : " + dataArray.size());
			ForkJoinPool forkJoinPool = new ForkJoinPool(8);
			forkJoinPool.submit(() -> dataArray.parallelStream().forEach(t -> {
				try {
					ProcessRecordsOnebyOne(t);
				} catch (Exception e) {
					// e.printStackTrace();
				}
			})).get();

		} catch (Exception e) {
			System.out.println("Exception occured while updating Security holdings details information :" + e.getMessage());
			System.out.println("Exception stacktrace :" + e.getStackTrace());
		}
	}
	
	private void ProcessRecordsOnebyOne(String record) {
		// region Declarations
		// endregion
		System.out.println("****************Starting process for updating data for record :" + record + "******************");
		
		//Do your regular operation.
		
	}
	

	
}
