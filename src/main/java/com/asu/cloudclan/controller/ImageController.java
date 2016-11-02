package com.asu.cloudclan.controller;


import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asu.cloudclan.service.ImageService;
import com.asu.cloudclan.vo.TransformationVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

@RestController
public class ImageController {

	@Autowired
	ImageService imageService;

	@RequestMapping(value = "/images/{state}/{containerId}/**", method = RequestMethod.GET)
	public String getImage(HttpServletResponse response, TransformationVO transformationVO, @PathVariable("containerId") String containerId, @PathVariable("state") String state, HttpServletRequest request) throws IOException {
		String fullPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String prefix = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		String imageUrl = antPathMatcher.extractPathWithinPattern(prefix, fullPath);
		System.out.println(containerId);
		System.out.println(state);
		try {
			InputStream inputStream = imageService.getImage(containerId, state, imageUrl, transformationVO);
			if(inputStream != null) {
				response.setContentType("image/jpg");
				byte[] bytes = IOUtils.toByteArray(inputStream);
				ServletOutputStream responseOutputStream = response.getOutputStream();
				responseOutputStream.write(bytes);
				responseOutputStream.flush();
				responseOutputStream.close();
			} else {
				response.setContentType("application/json");
				return new ObjectMapper().writeValueAsString(transformationVO.errorVOs);
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		return "";
	}
}

