/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author Alisson
 */
public class PackageClassesGetter {
     /**
 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
 *
 * @param packageName The base package
 * @return The classes
 * @throws ClassNotFoundException
 * @throws IOException
     * @throws java.net.URISyntaxException
 */
public static File[] getClasses(String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
    
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    assert classLoader != null;
    String path = packageName.replace('.', '/');
    Enumeration<URL> resources = classLoader.getResources(path);
    List<File> dirs = new ArrayList<>();
    while (resources.hasMoreElements()) {
        URL resource = resources.nextElement();
//        dirs.add(new File(resource.getFile()));
        File f = new File(resource.toURI());
        dirs.add(f);
        
    }
    ArrayList<File> files = new ArrayList<>();
    for (File directory : dirs) {
        files.addAll(findClasses(directory, packageName));
    }
    return files.toArray(new File[files.size()]);
}

/**
 * Recursive method used to find all classes in a given directory and subdirs.
 *
 * @param directory   The base directory
 * @param packageName The package name for classes found inside the base directory
 * @return The classes
 * @throws ClassNotFoundException
 */
private static List<File> findClasses(File directory, String packageName) throws ClassNotFoundException {
    List<File> files = new ArrayList<File>();
    if (!directory.exists()) {
        return files;
    }
    File[] filesArray = directory.listFiles();
    for (File file : filesArray) {
        if (file.isDirectory()) {
            assert !file.getName().contains(".");
            files.addAll(findClasses(file, packageName + "." + file.getName()));
        } else if (file.getName().endsWith(".xml")) {
            files.add(file);
        }
    }
    return files;
}
}
