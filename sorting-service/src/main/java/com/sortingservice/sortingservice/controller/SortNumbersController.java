package com.sortingservice.sortingservice.controller;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sortingservice.constants.SortingServiceConstants;
import com.sortingservice.sortingservice.beans.SortedValuesBean;
import com.sortingservice.sortingservice.beans.SortingValuesRepository;

/**
 * @author maulik Class declared with
 * @RestController annotation for Generating and Sorting Random Number Array
 */
@RestController
public class SortNumbersController {

	private static Logger logger = Logger.getLogger(SortNumbersController.class.getName());

	@Autowired
	SortingValuesRepository sortingRepository;

	@RequestMapping(value = "/previousValues", method = RequestMethod.GET)
	public String getPreviouslyGeneratedValues() {
		try {
			SortedValuesBean bean = sortingRepository.getLastSortedValues();
			JSONObject lastSortedObj = new JSONObject();
			lastSortedObj.put("original", bean.getOriginalstring());
			lastSortedObj.put("sorted", bean.getSortedstring());
			lastSortedObj.put(SortingServiceConstants.ERROR, "");
			return lastSortedObj.toString();
		} catch (JSONException e) {
			logger.log(Level.SEVERE, "Exception occured in getPreviouslyGeneratedValues: ", e);
			try {
				JSONObject errorObject = new JSONObject();
				errorObject.put(SortingServiceConstants.ERROR,
						"Error occured while fetching previously inserted values.");
				return errorObject.toString();
			} catch (JSONException je) {
				logger.log(Level.SEVERE, "Exception occured in getPreviouslyGeneratedValues: ", je);
				return null;
			}
		}
	}

	/**
	 * Request Method for Sorting the Array of Random Numbers.
	 * 
	 * @param randomArrayString
	 * @return
	 */
	@RequestMapping(value = "/sortRandomNumbers", method = RequestMethod.POST)
	public String sortRandomArray(@RequestParam("randomObj") String randomArrayString) {
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

			String durationString = duration.toString().replaceAll("[PTS]", "");

			JSONObject sortedArrayObject = new JSONObject();
			sortedArrayObject.put(SortingServiceConstants.VALUE, new JSONArray(Arrays.asList(sortingArray)));
			sortedArrayObject.put(SortingServiceConstants.TIME_TAKEN, durationString);
			sortedArrayObject.put(SortingServiceConstants.ITERATIONS, counter);
			sortedArrayObject.put(SortingServiceConstants.ERROR, "");

			if (sortingRepository != null) {
				SortedValuesBean bean = new SortedValuesBean();
				bean.setOriginalstring(randomArrayString.replace("\"", ""));
				bean.setSortedstring(Arrays.toString(sortingArray));
				bean.setCreateddate(ZonedDateTime.now());
				sortingRepository.insertSortedData(bean);
			}
			return sortedArrayObject.toString();
		} catch (JSONException e) {
			try {
				logger.log(Level.SEVERE, "Exception occured in sortRandomArray: ", e);
				JSONObject errorObject = new JSONObject();
				errorObject.put(SortingServiceConstants.ERROR, "Error occured while sorting values.");
				return errorObject.toString();
			} catch (JSONException e1) {
				logger.log(Level.SEVERE, "Exception occured in sortRandomArray: ", e1);
				return null;
			}
		}
	}

	/**
	 * Checks for array is sorted or not
	 * 
	 * @param sortingArray
	 * @return
	 */
	public boolean isSorted(int[] sortingArray) {
		for (int sortedCounter = 0; sortedCounter < sortingArray.length - 1; ++sortedCounter) {
			if (sortingArray[sortedCounter] > sortingArray[sortedCounter + 1]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * To shuffle the array
	 * @param sortingArray
	 */
	private void shuffle(int[] sortingArray) {
		int index1 = (int) (Math.random() * sortingArray.length), index2 = (int) (Math.random() * sortingArray.length);
		int a = sortingArray[index1];
		sortingArray[index1] = sortingArray[index2];
		sortingArray[index2] = a;
	}
}
