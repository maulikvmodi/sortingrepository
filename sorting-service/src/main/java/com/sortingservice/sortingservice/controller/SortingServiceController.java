package com.sortingservice.sortingservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sortingservice.constants.SortingServiceConstants;

@Controller
public class SortingServiceController {

	@RequestMapping("/")
	public String loadPage() {
		return SortingServiceConstants.INDEX_PAGE;
	}
}
