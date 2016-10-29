package com.asu.cloudclan.service.core

import java.io.InputStream

import com.asu.cloudclan.vo.{FinalTransformationVO, TransformationVO}
import com.sksamuel.scrimage.filter._
import com.sksamuel.scrimage.nio.{JpegWriter, PngWriter}
import com.sksamuel.scrimage.{Color, Image}

/**
  * Created by rubinder on 9/29/16.
  */
class ScalaTransformationService {

  def optimize(input:InputStream) : InputStream = {
    var image = Image.fromStream(input);
    return image.stream(JpegWriter().withCompression(80))
  }

  def transform(input:InputStream, transformationVO: FinalTransformationVO) : InputStream = {
    var image = Image.fromStream(input);
    var (h,w) = image.dimensions;
    if(transformationVO.w == 0) {
      transformationVO.w = w;
    }
    if(transformationVO.h == 0) {
      transformationVO.h= h;
    }

    if(transformationVO.flipX) {
      image = image.flipX;
    }

    if(transformationVO.flipY) {
      image = image.flipY;
    }

    if(transformationVO.rotate) {
      for(str <- transformationVO.rotations ){
        if(str.equals("L")) {
          image = image.rotateLeft
        } else {
          image = image.rotateRight
        }
      }
    }

    if(transformationVO.pad) {
      var color = Color.Transparent;
      if(transformationVO.pad_c) {
        color = Color.apply(transformationVO.padColorArray(0), transformationVO.padColorArray(1),
        transformationVO.padColorArray(2), transformationVO.padColorArray(3));
      }
      image = image.padWith(transformationVO.padArray(0),transformationVO.padArray(1),transformationVO.padArray(2),transformationVO.padArray(3),color);
    }

    if(transformationVO.cover) {
      image = image.cover(transformationVO.w,transformationVO.h)
    }

    if(transformationVO.fit) {
      var color = Color.Transparent;
      if(transformationVO.fit_c) {
        color = Color.apply(transformationVO.fitColorArray(0), transformationVO.fitColorArray(1),
          transformationVO.fitColorArray(2), transformationVO.fitColorArray(3));
      }
      image = image.fit(transformationVO.w,transformationVO.h, color)
    }

    if(transformationVO.scale != null) {
      image = image.scale(transformationVO.scale);
    }

/*    if(transformationVO.resize) {
      if (transformationVO.w == 0) {
        image = image.resize(transformationVO.h)
      } else if (transformationVO.h == 0) {
        image = image.resizeToWidth(transformationVO.w)
      } else {
        image = image.resizeTo(transformationVO.w, transformationVO.h)
      }
    }*/

    if(transformationVO.fltr) {
      if(transformationVO.filter.equals("blur")) {
        image = image.filter(BlurFilter)
      } else if(transformationVO.filter.equals("bump")) {
        image = image.filter(BumpFilter)
      } else if(transformationVO.filter.equals("edge")) {
        image = image.filter(EdgeFilter)
      } else if(transformationVO.filter.equals("grayscale")) {
        image = image.filter(GrayscaleFilter)
      } else if(transformationVO.filter.equals("invert")) {
        image = image.filter(InvertFilter)
      } else if(transformationVO.filter.equals("lensflare")) {
        image = image.filter(LensFlareFilter)
      } else if(transformationVO.filter.equals("noise")) {
        image = image.filter(NoiseFilter())
      } else if(transformationVO.filter.equals("pixelate")) {
        image = image.filter(PixelateFilter())
      } else if(transformationVO.filter.equals("sepia")) {
        image = image.filter(SepiaFilter)
      } else if(transformationVO.filter.equals("vintage")) {
        image = image.filter(VintageFilter)
      } else if(transformationVO.filter.equals("lensblur")) {
        image = image.filter(new LensBlurFilter(50, 2, 255, 50, 0))
      }
    }

    return image.stream(JpegWriter());
  }
}


