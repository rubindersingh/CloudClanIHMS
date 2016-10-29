package com.asu.cloudclan.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.StdArraySerializers;

/**
 * Created by ubuntu on 10/4/16.
 */

public class TransformationVO {
    @JsonProperty("w")
    Integer width;

    @JsonProperty("h")
    Integer height;

    @JsonProperty("filter")
    String filter;

    @JsonProperty("o")
    Double opacity;

    @JsonProperty("r")
    Integer radius;

    /*@JsonProperty("c")
    Integer crop;*/

    @JsonProperty("g")
    String gravity;

    /*@JsonProperty("pw")
    Double padding;*/

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public Integer getRadius() { return radius;    }

    public void setRadius(Integer radius) { this.radius = radius;    }

    public String getGravity() { return gravity;    }

    public void setGravity(String gravity) { this.gravity = gravity;    }

    @Override
    public String toString() {
        return "w_" + width +
                ",h_" + height +
                ",f_" + filter +
                ",o_" + opacity +
                ",r_ " + radius +
                ",g_" + gravity;
    }
}
