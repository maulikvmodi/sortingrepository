package com.sortingservice.sortingservicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;
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
	private SortingValuesRepository repo;
	
	private static Logger logger = Logger.getLogger(SortingServiceApplicationTests.class.getName());
	
	@Test
	public void contextLoads() {
		controller = new SortNumbersController();
	}
	
	@Test
	public void sortRandomNumbers() {
		controller = new SortNumbersController();
		String randomNumberArray = "[52,2,32,1,58,5,42,12,36,98,57,74,78,45,22,3]";
		String sortedArray = controller.sortRandomArray(randomNumberArray);
		assertNotNull(sortedArray);
	}
	
	@Test
	public void checkForSortedArray() {
		controller = new SortNumbersController();
		String randomNumberArray = "[52,2,32,1,58,5,42,12,36,98,57,74,78,45,22,3]";
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
	public void testSortedArrayElementsEquality() {
		controller = new SortNumbersController();
		String randomNumberArray = "[52,2,32,1,58,5,42,12,36,98,57,74,78,45,22,3]";
		String sortedArray = controller.sortRandomArray(randomNumberArray);
		assertEquals(randomNumberArray, sortedArray);
	}
	
	@Test
	public void testArrayElementCount() {
		controller = new SortNumbersController();
		String randomNumberArray = "[52,2,32,1,58,5,42,12,36,98,57,74,78,45,22,3]";
		String sortedArray = controller.sortRandomArray(randomNumberArray);
		assertEquals(randomNumberArray.length(), sortedArray.length());
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
		try {
			SortedValuesBean bean = new SortedValuesBean();
			repo = new SortingValuesRepository();
			bean = repo.getLastSortedValues();
			assertNotNull(bean);
		}catch(Exception e) {
			logger.log(Level.SEVERE, "Exception thrown in testGetLastSortedValues: ", e);
		}
	}
	
	@Test
	public void testSuccessfulInsertion() {
		try {
			SortedValuesBean bean = new SortedValuesBean();
			bean.setOriginalstring("[21,32,45,12,1,56,21,63,87,2,30]");
			bean.setSortedstring("[1,2,12,21,30,32,45,56,63,87]");
			bean.setCreateddate(ZonedDateTime.now());
			repo = new SortingValuesRepository();
			int updatedRows = repo.insertSortedData(bean);
			assertEquals(0, updatedRows);
			
		}catch(Exception e) {
			logger.log(Level.SEVERE, "Exception thrown in testSuccessfulInsertion: ", e);
		}
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
