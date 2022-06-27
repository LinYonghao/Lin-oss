package com.linyonghao.oss.common.utils;

import com.linyonghao.oss.common.entity.CoreObject;
import com.linyonghao.oss.common.entity.DirectoryTree;
import io.lettuce.core.resource.DirContextDnsResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于将key生产目录树
 */
@Component
public class DirectoryTreeUtil {

    Map<String, DirectoryTree> tree = new ConcurrentHashMap<>();

    public void resolveOne(CoreObject obj){
        String[] dirs = obj.getRemoteKey().split("/");
        // 根目录文件
        if (dirs.length == 1) {
            DirectoryTree directoryTree = tree.get("/");
            if (directoryTree == null) {
                directoryTree = new DirectoryTree();
                tree.put("/", directoryTree);
            }
            directoryTree.addFile(obj);
            return;

        }
        // 非根目录文件
        String currentDir = "/";
        DirectoryTree currentDirTree = getOrCreateRootDir();
        for (int i = 0; i < dirs.length; i++) {
            if (i != dirs.length - 1) {
                String lastDir = currentDir.equals("/") ? "/" : currentDir.substring(0, currentDir.length() - 1);
                currentDirTree = addDirectory(lastDir, currentDir + dirs[i]);
                currentDir += dirs[i] + "/";
            } else {
                currentDirTree.addFile(obj);
            }
        }
    }

    public Map<String, DirectoryTree> resolve(List<CoreObject> objectList) {
        for (CoreObject obj : objectList) {
            resolveOne(obj);
        }
        return tree;
    }

    public DirectoryTree addDirectory(String lastDir, String dir) {
        DirectoryTree directoryTree = tree.get(lastDir);
        String simpleDir = dir.replace(lastDir, "");
        directoryTree.addDirectory(simpleDir);
        DirectoryTree currentDir = tree.get(dir);
        if (currentDir == null) {
            currentDir = new DirectoryTree();
            tree.put(dir, currentDir);
        }
        return currentDir;
    }

    public DirectoryTree getOrCreateRootDir() {
        DirectoryTree rootDir = tree.get("/");
        if (rootDir == null) {
            rootDir = new DirectoryTree();
            tree.put("/", rootDir);
        }
        return rootDir;
    }

    public Map<String, DirectoryTree> addFile(CoreObject object,Map<String, DirectoryTree> tree){
        this.tree = tree;
        resolveOne(object);
        return tree;
    }
}




