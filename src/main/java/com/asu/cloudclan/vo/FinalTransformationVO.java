package com.asu.cloudclan.vo;

import com.sksamuel.scrimage.Filter;

/**
 * Created by ubuntu on 10/4/16.
 */

public class FinalTransformationVO {
    public int w;
    public int h;
    public boolean resize;
    public boolean flipX;
    public boolean flipY;
    public boolean pad;
    public Integer[] padArray;
    public boolean pad_c;
    public Integer[] padColorArray;
    public boolean cover;
    public boolean fit;
    public boolean fit_c;
    public Integer[] fitColorArray;
    public boolean fltr;
    public String filter;
    public boolean rotate;
    public String[] rotations;
    public Double scale;
}
