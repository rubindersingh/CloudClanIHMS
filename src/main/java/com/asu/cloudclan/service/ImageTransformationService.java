package com.asu.cloudclan.service;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.Stream2BufferedImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageTransformationService {

	public BufferedImage transform(Long transformId, MultipartFile file) {
		BufferedImage output = null;
		switch (transformId.intValue()) {
		case 1:
			output = transform1(file);
			break;
		case 2:
			output = transform2(file);
			break;
		default:
			break;
		}
		return output;
	}
	
	public BufferedImage transform1(MultipartFile file) {
		BufferedImage output = null;
		try {
			BufferedImage inputImage = ImageIO.read(file.getInputStream());
			
			IMOperation op = new IMOperation();
			op.size(200, 200);
			op.gravity("center");
			op.addRawArgs("xc:none");
			//op.draw("roundRectangle 0,0 200,200 50,50");
			op.draw("circle 100,100 100,-10");
			op.addImage("png:-");
			ConvertCmd convert = new ConvertCmd();
		    Stream2BufferedImage s2b = new Stream2BufferedImage();
		    convert.setOutputConsumer(s2b);
		    convert.run(op);
		    // save result to disk
		    BufferedImage img = s2b.getImage();
		    
			IMOperation op1 = new IMOperation();
			//op.blend(50);
			op1.addImage();  // read and crop first image
			op1.resize(200, 200, "^");
			op1.gravity("center");
			op1.addImage();  // read and crop second image
			op1.addImage("png:-");                 // output image

			CompositeCmd composite = new CompositeCmd();
			Stream2BufferedImage s2b2 = new Stream2BufferedImage();
			composite.setOutputConsumer(s2b2);
			composite.run(op1,inputImage,img);
			output = s2b2.getImage();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return output;
	}
	
	public BufferedImage transform2(MultipartFile file) {
		BufferedImage output = null;
		try {
			BufferedImage inputImage = ImageIO.read(file.getInputStream());
			
			IMOperation op = new IMOperation();
			op.addImage(); 
			//op.p_compress();
			op.compress("Zip");
			op.bench();
			//op.blur(2.0).paint(10.0);
			op.addImage("jpg:-");
			ConvertCmd convert = new ConvertCmd();
		    Stream2BufferedImage s2b = new Stream2BufferedImage();
		    convert.setOutputConsumer(s2b);
		    convert.run(op, inputImage);
		    // save result to disk
		    output = s2b.getImage();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return output;
	}
}
