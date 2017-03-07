package com.qianmo.common.gallery.tools;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.qianmo.common.gallery.model.FileEntity;
import com.qianmo.common.gallery.model.FolderEntity;

import java.io.File;
import java.util.ArrayList;

public class GalleryTool {

    public static ArrayList<FolderEntity> getPhotos(Context context) {
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver()
                .query(imageUri, null, null, null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<FolderEntity> folders = new ArrayList<FolderEntity>();
            FolderEntity allFolderEntity = new FolderEntity();
            allFolderEntity.setSelected(true);
            allFolderEntity.setFileEntityList(new ArrayList<FileEntity>());
            allFolderEntity.setName("所有图片");
            allFolderEntity.setDirPath("");
            folders.add(allFolderEntity);
            do {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                File file = new File(path);
                String parentPath = file.getParent();
                if (TextUtils.isEmpty(parentPath)) continue;
                FileEntity fileEntity = new FileEntity(path);
                fileEntity.setSelected(false);
                fileEntity.setPath(path);
                FolderEntity folderEntity = null;
                for (FolderEntity pf : folders) {
                    if (pf.getDirPath().equals(parentPath)) {
                        folderEntity = pf;
                        break;
                    }
                }
                if (folderEntity == null) {
                    folderEntity = new FolderEntity();
                    folderEntity.setDirPath(parentPath);
                    folderEntity.setName(parentPath.substring(parentPath.lastIndexOf(File.separator) + 1, parentPath.length()));
                    folderEntity.setSelected(false);
                    ArrayList<FileEntity> fileEntities = new ArrayList<FileEntity>();
                    fileEntities.add(fileEntity);
                    folderEntity.setFileEntityList(fileEntities);
                    folders.add(folderEntity);
                } else {
                    folderEntity.getFileEntityList().add(fileEntity);
                }
                allFolderEntity.getFileEntityList().add(fileEntity);
            } while (cursor.moveToNext());
            return folders;
        }
        return null;
    }
}
