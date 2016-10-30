package com.asu.cloudclan.vo;

import com.asu.cloudclan.util.ValidationUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sksamuel.scrimage.filter.BlurFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ubuntu on 10/4/16.
 */

public class TransformationVO {
    public Integer w;
    public Integer h;
    public String op; //, resize, max, overlay, pad, underlay
    public String pad;
    public String pad_c;
    public String fit_c;
    public String fltr;
    public String flip;
    public String rot;
    public Double scale;

    @JsonIgnore
    public FinalTransformationVO finalTransformationVO;
    @JsonIgnore
    public List<ErrorVO> errorVOs;

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getFltr() {
        return fltr;
    }

    public void setFltr(String fltr) {
        this.fltr = fltr;
    }

    public String getFlip() {
        return flip;
    }

    public void setFlip(String flip) {
        this.flip = flip;
    }

    public String getPad() {
        return pad;
    }

    public void setPad(String pad) {
        this.pad = pad;
    }

    public String getPad_c() {
        return pad_c;
    }

    public void setPad_c(String pad_c) {
        this.pad_c = pad_c;
    }

    public String getFit_c() {
        return fit_c;
    }

    public void setFit_c(String fit_c) {
        this.fit_c = fit_c;
    }

    public String getRot() {
        return rot;
    }

    public void setRot(String rot) {
        this.rot = rot;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(w!=null) {
            stringBuilder.append("_w_");
            stringBuilder.append(w);
        }
        if(h!=null) {
            stringBuilder.append("_h_");
            stringBuilder.append(h);
        }
        if(op!=null) {
            stringBuilder.append("_op_");
            stringBuilder.append(op);
        }
        if(pad!=null) {
            stringBuilder.append("_pad_");
            stringBuilder.append(pad);
        }
        if(pad_c!=null) {
            stringBuilder.append("_pad_c_");
            stringBuilder.append(pad_c);
        }
        if(fit_c!=null) {
            stringBuilder.append("_fit_c_");
            stringBuilder.append(fit_c);
        }
        if(fltr!=null) {
            stringBuilder.append("_fltr_");
            stringBuilder.append(fltr);
        }
        if(flip!=null) {
            stringBuilder.append("_flip_");
            stringBuilder.append(flip);
        }
        if(rot!=null) {
            stringBuilder.append("_rot_");
            stringBuilder.append(rot);
        }
        if(scale!=null) {
            stringBuilder.append("_scale_");
            stringBuilder.append(scale);
        }
        return stringBuilder.toString();
    }

    public boolean validateAndConvert() {
        try {
            finalTransformationVO = new FinalTransformationVO();
            finalTransformationVO.w = this.w != null ? this.w : 0;
            finalTransformationVO.h = this.h != null ? this.h : 0;
            if(this.op != null) {
                if(this.op.equals("cover")) {
                    if(this.w !=null || this.h !=null) {
                        finalTransformationVO.cover = true;
                    } else {
                        return false;
                    }
                } else if(this.op.equals("fit")) {
                    if(this.w !=null || this.h !=null) {
                        finalTransformationVO.fit = true;
                    } else {
                        return false;
                    }

                    if(this.fit_c != null) {
                        Integer[] colors = Arrays.asList(this.fit_c.split(",")).stream().map(Integer::parseInt).filter(num -> num>=0 && num<256).collect(Collectors.toList()).toArray(new Integer[0]);
                        if(colors.length == 4) {
                            finalTransformationVO.fit_c = true;
                            finalTransformationVO.fitColorArray = colors;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                if(this.w !=null || this.h !=null) {
                    finalTransformationVO.fit = true;
                }
            }

            if(this.pad != null) {
                Integer[] paddings = Arrays.asList(this.pad.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList()).toArray(new Integer[0]);
                Integer[] newPaddings = new Integer[4];
                if(paddings.length == 4) {
                    newPaddings = paddings;
                } else if(paddings.length == 2) {
                    newPaddings[0] = paddings[0];
                    newPaddings[1] = paddings[1];
                    newPaddings[2] = paddings[0];
                    newPaddings[3] = paddings[1];
                } else if(paddings.length == 1) {
                    newPaddings[0] = paddings[0];
                    newPaddings[1] = paddings[0];
                    newPaddings[2] = paddings[0];
                    newPaddings[3] = paddings[0];
                } else {
                    return false;
                }

                if(this.pad_c != null) {
                    Integer[] colors = Arrays.asList(this.pad_c.split(",")).stream().map(Integer::parseInt).filter(num -> num>=0 && num<256).collect(Collectors.toList()).toArray(new Integer[0]);
                    if(colors.length == 4) {
                        finalTransformationVO.pad_c = true;
                        finalTransformationVO.padColorArray = colors;
                    }
                }
            }

            if(this.flip != null) {
                if(this.flip.equals("x")) {
                    finalTransformationVO.flipX = true;
                } else if(this.flip.equals("y")) {
                    finalTransformationVO.flipY = true;
                } else {
                    return false;
                }
            }

            if(this.fltr != null) {
                if(ValidationUtil.isvalidFilter(this.fltr)) {
                    finalTransformationVO.fltr = true;
                    finalTransformationVO.filter = this.fltr;
                } else {
                    return false;
                }
            }

            if(this.rot != null) {
                List<String> rotations = Arrays.asList(this.rot.split(",")).stream().collect(Collectors.toList());
                Set<String> unique = new HashSet<>(rotations);
                boolean cond1 = unique.size()==2 && unique.contains("L") && unique.contains("R");
                boolean cond2 = unique.size()==1 && (unique.contains("L") || unique.contains("R"));
                if(cond1 || cond2) {
                    finalTransformationVO.rotate = true;
                    finalTransformationVO.rotations = rotations.toArray(new String[0]);
                } else {
                    return false;
                }
            }

            if(this.scale != null) {
                finalTransformationVO.scale = this.scale;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
