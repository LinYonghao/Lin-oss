package com.linyonghao.oss.common.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DirectoryTree {

    private Set<String> directory  = new HashSet<>();
    private List<CoreObject> files = new ArrayList<>();

    public Set<String> getDirectory() {
        return directory;
    }

    public void setDirectory(Set<String> directory) {
        this.directory = directory;
    }

    public List<CoreObject> getFiles() {
        return files;
    }

    public void setFiles(List<CoreObject> files) {
        this.files = files;
    }

    public void addDirectory(String directory){
        this.directory.add(directory);
    }

    public void addFile(CoreObject filename){
        this.files.add(filename);
    }

}
