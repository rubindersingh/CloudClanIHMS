package com.asu.cloudclan.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.asu.cloudclan.service.ImageTransforamtionService;

@RestController
public class ImageController {

	@Autowired
	ImageTransforamtionService imageTransforamtionService;

	@RequestMapping(value="/transform/{transformId}", method=RequestMethod.POST)
	public String transform(HttpServletResponse response, @PathVariable("transformId") Long transformId, @RequestParam("file") MultipartFile file) throws IOException {
		BufferedImage output = imageTransforamtionService.transform(transformId, file);
		if(output==null) {
			return "Oops, Something went wrong!!!";
		} else {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		    try {
		      ImageIO.write(output, "jpg", outputStream);
		      byte[] imgByte = outputStream.toByteArray();

			  response.setHeader("Cache-Control", "no-store");
			  response.setHeader("Pragma", "no-cache");
			  response.setDateHeader("Expires", 0);
			  response.setContentType("image/jpg");
			  ServletOutputStream responseOutputStream = response.getOutputStream();
			  responseOutputStream.write(imgByte);
			  responseOutputStream.flush();
			  responseOutputStream.close();
		    } catch (Exception e) {
		      response.sendError(HttpServletResponse.SC_NOT_FOUND);
		    }
		    return "";
		}
	}
	
	@RequestMapping(value = "/images/{state}/{containerId}/**", method = RequestMethod.GET)
	public String getImageTransient(HttpServletResponse response, @PathVariable("containerId")String containerId, @PathVariable("state")String state,HttpServletRequest request,
			@RequestParam(value="h", required=false)Integer height,@RequestParam(value="w", required=false)Integer width,@RequestParam(value="pw", required=false)Integer pad,
			@RequestParam(value="fl", required=false)String filter,@RequestParam(value="c", required=false)String crop, @RequestParam(value="g", required=false)String gravity,
			@RequestParam(value = "r", required=false)String radius, @RequestParam(value = "o", required=false)String opacity) throws IOException {
			String imagePath = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
			String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		    AntPathMatcher apm = new AntPathMatcher();
		    String finalPath = apm.extractPathWithinPattern(bestMatchPattern, imagePath);
		    System.out.println(containerId);	
		    System.out.println(state);
			System.out.println(finalPath);	
			System.out.println(height);	
			
			System.out.println(width);	
			System.out.println(pad);	
			System.out.println(filter);	
			return "done";
	}
	
}