package com.sortingservice.sortingservice.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sortingservice.constants.SortingServiceConstants;
import com.sortingservice.sortingservice.beans.SortedValuesBean;
import com.sortingservice.sortingservice.beans.SortingValuesRepository;

/**
 * @author maulik 
 * Class declared with @RestController annotation for Generating
 *         and Sorting Random Number Array
 */
@RestController
public class SortNumbersController {

	private static Logger logger = Logger.getLogger(SortNumbersController.class.getName());
	
	@Autowired
	SortingValuesRepository sortingRepository;

	@RequestMapping(value = "/previousValues", method = RequestMethod.GET)
	public @ResponseBody String getPreviouslyGeneratedValues() {
		try {
			SortedValuesBean bean = sortingRepository.getLastSortedValues();
			JSONObject lastSortedObj = new JSONObject();
			lastSortedObj.put("original", bean.getOriginalstring());
			lastSortedObj.put("sorted", bean.getSortedstring());
			return lastSortedObj.toString();
		} catch (JSONException e) {
			logger.log(Level.SEVERE, "Exception occured in getPreviouslyGeneratedValues: ", e);
			return null;
		}
	}

	/**
	 * Request Method for generating Array of Random Numbers.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getRandomNumbers", method = RequestMethod.GET)
	public @ResponseBody String getRandomNumbersArray() {
		try {
			JSONArray randomNumArray = new JSONArray();

			for (int randomCount = 0; randomCount < 10; randomCount++) {
				Random r = new Random();
				randomNumArray.put(r.ints(1, (100 + 1)).limit(1).findFirst().getAsInt());
			}
			return randomNumArray.toString();
		}catch(Exception e) {
			logger.log(Level.SEVERE, "Exception occured in getRandomNumbersArray: ", e);
			return null;
		}
	}

	/**
	 * Request Method for Sorting the Array of Random Numbers.
	 * 
	 * @param randomArrayString
	 * @return
	 */
	@RequestMapping(value = "/sortRandomNumbers", method = RequestMethod.POST)
	public @ResponseBody String sortRandomArray(@RequestParam("randomObj") String randomArrayString) {
		try {
			int counter = 0;
			JSONArray randomNumberArray = new JSONArray(randomArrayString);
			int sortingArray[] = new int[randomNumberArray.length()];
			for (int count = 0; count < randomNumberArray.length(); count++) {
				sortingArray[count] = randomNumberArray.getInt(count);
			}
			Instant start = java.time.Instant.now();
			while (!isSorted(sortingArray)) {
				shuffle(sortingArray);
				counter++;
			}
			Instant end = java.time.Instant.now();
			Duration duration = java.time.Duration.between(start, end);

			String durationString =duration.toString().replaceAll("[PTS]", "");
			
			JSONObject sortedArrayObject = new JSONObject();
			sortedArrayObject.put(SortingServiceConstants.VALUE, new JSONArray(Arrays.asList(sortingArray)));
			sortedArrayObject.put(SortingServiceConstants.TIME_TAKEN, durationString);
			sortedArrayObject.put(SortingServiceConstants.ITERATIONS, counter);
			
			if(sortingRepository != null) {
				SortedValuesBean bean = new SortedValuesBean();
				bean.setOriginalstring(randomArrayString.replace("\"", ""));
				bean.setSortedstring(Arrays.toString(sortingArray));
				bean.setCreateddate(new java.sql.Timestamp(new Date().getTime()));
				sortingRepository.insertSortedData(bean);
			}
			return sortedArrayObject.toString();
		} catch (JSONException e) {
			logger.log(Level.SEVERE, "Exception occured in sortRandomArray: ", e);
		}
		return null;
	}

	/**
	 * Checks for array is sorted or not
	 * @param sortingArray
	 * @return
	 */
	public boolean isSorted(int[] sortingArray) {
		try {
			System.out.println("inside isSorted: ");
			for (int sortedCounter = 0; sortedCounter < sortingArray.length - 1; ++sortedCounter) {
				if (sortingArray[sortedCounter] > sortingArray[sortedCounter + 1]) {
					return false;
				}
			}
			return true;
		}catch(Exception e) {
			logger.log(Level.SEVERE, "Exception occured in isSorted: ", e);
			return false;
		}
	}

	/**
	 * To shuffle the array
	 * @param sortingArray
	 */
	private void shuffle(int[] sortingArray) {
		try {
			for (int x = 0; x < sortingArray.length; ++x) {
				int index1 = (int) (Math.random() * sortingArray.length),
					index2 = (int) (Math.random() * sortingArray.length);
				int a = sortingArray[index1];
				sortingArray[index1] = sortingArray[index2];
				sortingArray[index2] = a;
			}
		}catch(Exception e) {
			logger.log(Level.SEVERE, "Exception occured in shuffle: ", e);
		}
	}
}
