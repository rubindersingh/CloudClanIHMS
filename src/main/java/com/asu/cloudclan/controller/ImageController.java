package com.asu.cloudclan.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asu.cloudclan.service.ImageService;
import com.asu.cloudclan.vo.TransformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.asu.cloudclan.service.ImageTransformationService;

@RestController
public class ImageController {

	@Autowired
	ImageService imageService;

/*	@RequestMapping(value="/transform/{transformId}", method=RequestMethod.POST)
	public String transform(HttpServletResponse response, @PathVariable("transformId") Long transformId, @RequestParam("file") MultipartFile file) throws IOException {
		BufferedImage output = imageTransformationService.transform(transformId, file);
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
	}*/
	
	@RequestMapping(value = "/images/{state}/{containerId}/**", method = RequestMethod.GET)
	public String getImage(HttpServletResponse response, TransformationVO transformationVO, @PathVariable("containerId")String containerId, @PathVariable("state")String state, HttpServletRequest request) {
			String fullPath = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
			String prefix = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		    AntPathMatcher antPathMatcher = new AntPathMatcher();
		    String imageUrl = antPathMatcher.extractPathWithinPattern(prefix, fullPath);
		    System.out.println(containerId);	
		    System.out.println(state);
			imageService.getImage(containerId, state, imageUrl, transformationVO);
			/*System.out.println(transformationVO.getHeight());
			System.out.println(transformationVO.getWidth());
			System.out.println(transformationVO.getFilter());
			System.out.println(transformationVO.getGravity());
			System.out.println(transformationVO.getOpacity());
			System.out.println(transformationVO.getRadius());*/
			return "done";
	}
	
}