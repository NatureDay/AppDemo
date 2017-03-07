package com.qianmo.common.gallery.model;

import java.io.Serializable;

public class FileEntity implements Serializable {

    private String path;
    private boolean isSelected;

    public FileEntity(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
