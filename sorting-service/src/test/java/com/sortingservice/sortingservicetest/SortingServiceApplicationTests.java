package com.sortingservice.sortingservicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sortingservice.sortingservice.beans.SortedValuesBean;
import com.sortingservice.sortingservice.beans.SortingValuesRepository;
import com.sortingservice.sortingservice.controller.SortNumbersController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SortingServiceApplicationTests {

	private SortNumbersController controller;
	
	private static Logger logger = Logger.getLogger(SortingServiceApplicationTests.class.getName());
	
	@Test
	public void contextLoads() {
		controller = new SortNumbersController();
	}
	
	@Test
	public void sortRandomNumbers() {
		controller = new SortNumbersController();
		String randomNumberArray = controller.getRandomNumbersArray();
		String sortedArray = controller.sortRandomArray(randomNumberArray);
		assertNotNull(randomNumberArray);
		assertNotNull(sortedArray);
		assertEquals(randomNumberArray, sortedArray);
	}
	
	@Test
	public void checkForSortedArray() {
		controller = new SortNumbersController();
		String randomNumberArray = controller.getRandomNumbersArray();
		JSONArray arr;
		try {
			arr = new JSONArray(randomNumberArray);
			int [] convertedArray = new int[arr.length()];
			convertJSONArrayToArray(arr, convertedArray);
			boolean isSorted = controller.isSorted(convertedArray);
			assertTrue(isSorted);
		} catch (JSONException e) {
			logger.log(Level.SEVERE, "Exception thrown in checkForSortedArray: ", e);
		}
		
	}

	@Test
	public void testPreviouslyGeneratedValues() {
		try {
			controller = new SortNumbersController();
			String prevGeneratedValue =controller.getPreviouslyGeneratedValues();
			assertNotNull(prevGeneratedValue);
			assertTrue(prevGeneratedValue.contains("original"));
			assertTrue(prevGeneratedValue.contains("sorted"));
		}catch(Exception e) {
			logger.log(Level.SEVERE, "Exception thrown in testPreviouslyGeneratedValues: ", e);
		}
	}
	
	@Test
	public void testGetLastSortedValues() {
		SortedValuesBean bean = new SortedValuesBean();
		SortingValuesRepository repo = new SortingValuesRepository();
		bean = repo.getLastSortedValues();
		assertNotNull(bean);
	}
	
	private void convertJSONArrayToArray(JSONArray arrayToConvert, int[] convertedArray) {
		try {
			for(int lengthCount =0; lengthCount<arrayToConvert.length();lengthCount++) {
				convertedArray[lengthCount] = arrayToConvert.getInt(lengthCount);
			}
		}catch(JSONException e) {
			logger.log(Level.SEVERE, "Exception thrown in convertJSONArrayToArray: ", e);
		}
	}
	
}
