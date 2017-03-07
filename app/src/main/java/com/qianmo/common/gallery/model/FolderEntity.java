package com.qianmo.common.gallery.model;

import java.io.Serializable;
import java.util.ArrayList;

public class FolderEntity implements Serializable {

    /* 文件夹名 */
    private String name;
    /* 文件夹路径 */
    private String dirPath;
    /* 该文件夹下图片列表 */
    private ArrayList<FileEntity> fileEntityList;
    /* 标识是否选中该文件夹 */
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public ArrayList<FileEntity> getFileEntityList() {
        return fileEntityList;
    }

    public void setFileEntityList(ArrayList<FileEntity> fileEntityList) {
        this.fileEntityList = fileEntityList;
    }
}
