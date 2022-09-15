package com.mitosv.cinematic.util;

import com.mitosv.cinematic.Cinematic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileManager {

    private static FileManager instance;

    private File dir;

    public FileManager(File dir){
        instance = this;
        this.dir = dir;
        try {
            init();
        } catch (IOException e) {
            Cinematic.LOGGER.error(String.valueOf(e));
        }
    }


    public Video getVideoFromName(String name){
        File[] files = getFiles();
        if (files==null)return null;
        Video video = null;
        for (File file : files){
            if (name.equals(file.getName())){
                video = getVideoFromFile(file);
            }
        }

        return video;
    }

    public static Video getVideoFromFile(File file){
        return new Video(file.toURI().toString().replace("file:/","file:///"), file.getName());
    }

    public Video[] getAllVideos(){
        File[] files = getFiles();
        assert files != null;
        List<Video> videos = new ArrayList<>();
        for(File file : files){
            videos.add(getVideoFromFile(file));
        }
        return videos.toArray(Video[]::new);
    }

    public File[] getFiles(){
        return this.dir.listFiles();
    }

    public String[] getFilesNames(){
        List<String> nameList = new ArrayList<>();
        File[] files = this.dir.listFiles();
        assert files != null;
        for (File file : files){
            nameList.add(file.getName());
        }
        return nameList.toArray(String[]::new);
    }

    private void init() throws IOException {
        if (!dir.exists()) {
            dir.mkdir();
            Cinematic.LOGGER.info("Folder video is created");
        }
    }

    public String getPath(){
        return this.dir.getAbsolutePath();
    }


    public static FileManager getInstance() {
        return instance;
    }



}