package com.asu.cloudclan.controller;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GreetingController {

    @RequestMapping("/greetme")
    public String index() {
        return "greetings";
    }
    
    @RequestMapping("/greetme1")
    public String index1() {
        return "index1";
    }
    
    @RequestMapping(value="/uploadFiles", method = RequestMethod.POST)
    public String uploadFiles(@RequestParam("name") String name,
			@RequestParam("file") List<MultipartFile> files) throws Exception {
    	if(files!=null) {
    		System.out.println(name);
    		System.out.println(files.size());
    		for(MultipartFile file : files) {
    			System.out.println(file.getOriginalFilename());
    			//String destination = "/images/" + file.getOriginalFilename();
    			String[] fileName= file.getOriginalFilename().split("/");
    			String imageName = (fileName[(fileName.length)-1]);
    			//int last = FileName.length;
    			//String ImageName = (file.getOriginalFilename()).substring(last);
    			//System.out.println(ImageName);    			
    			String destination = "E:/Images/Cloud/" + imageName;
    			System.out.println(destination);
    		    File fileUpload = new File(destination);
    		    //System.out.println(fileUpload);
    		    file.transferTo(fileUpload);
    		}
    	}
        return "greetings";
    }
}