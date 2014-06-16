package com.codepath.gridimagesearch.app;

import java.io.Serializable;

/**
 * Created by khayden on 6/14/14.
 */
public class SettingsResult implements Serializable{

    private String imageSize;
    private String colorFilter;
    private String imageType;
    private String siteFilter;

    public SettingsResult () {
        this.imageType = null;
        this.colorFilter = null;
        this.imageType = null;
        this.siteFilter = null;
    }
    public SettingsResult (String imageSize, String colorFilter, String imageType, String siteFilter) {
        this.imageSize = imageSize;
        this.colorFilter = colorFilter;
        this.imageType = imageType;
        this.siteFilter = siteFilter;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }

}
