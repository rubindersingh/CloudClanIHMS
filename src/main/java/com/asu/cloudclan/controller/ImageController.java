package com.asu.cloudclan.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}