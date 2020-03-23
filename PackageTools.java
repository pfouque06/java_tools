package javaTools;

import java.io.File;

public class PackageTools {

    // super class names
    //System.out.println("super class name: "+this.getClass().getSuperclass().getName());	 					        // java.lang.Object
    //System.out.println("super class simple name: "+this.getClass().getSuperclass().getSimpleName());			        // Object
    //System.out.println("super class package name: "+this.getClass().getSuperclass().getPackage().getName());          // java.lang

    // package name
    //System.out.println("class package class name: "+this.getClass().getPackage().getClass().getName());				// java.lang.Package
    //System.out.println("class package class simmple name: "+this.getClass().getPackage().getClass().getSimpleName());	// Package

    // implementation and sepcification details from MANIFST MD file
    //System.out.println("class package ImplementationVendor: "+this.getClass().getPackage().getImplementationVendor());
    //System.out.println("class package ImplementationTitle: "+this.getClass().getPackage().getImplementationTitle());
    //System.out.println("class package getImplementationVersion: "+this.getClass().getPackage().getImplementationVersion());
    //System.out.println("class package getSpecificationTitle: "+this.getClass().getPackage().getSpecificationTitle());
    //System.out.println("class package getSpecificationVendor: "+this.getClass().getPackage().getSpecificationVendor());
    //System.out.println("class package getSpecificationVersion: "+this.getClass().getPackage().getSpecificationVersion());

    // class name : "GetOpts.class" = "this.getClass()"
    //System.out.println("class name: "+this.getClass().getName());							// javaTools.GetOpts
    //System.out.println("class simple name: "+this.getClass().getSimpleName());			// GetOpts
    //System.out.println("class package name: "+this.getClass().getPackage().getName());	//javaTools

    // running directory  or bytecode class file path
    static String runningDir;
    // resources directory
    static String resourceDir;
    // package resources directory
    static String packageClassDir;
    // Project name
    static String projectName;
    // work space directory :
    static String workSpaceDir;

    private PackageTools() {
        init();
    }

    public static String getRunningDir() {
        if (runningDir == null) init();
        //System.out.println("runningDir: "+runningDir);
        return runningDir;
    }

    public static String getResourceDir() {
        if (resourceDir == null) init();
        //System.out.println("resourceDir: "+resourceDir);
        return resourceDir;
    }

    public static String getPackageClassDir() {
        if (packageClassDir == null) init();
        //System.out.println("packageClassDir: "+packageClassDir);
        return packageClassDir;
    }

    public static String getProjectName() {
        if (projectName == null) init();
        //System.out.println("projectName: "+projectName);
        return projectName;
    }

    public static String getWorkSpaceDir() {
        if (workSpaceDir == null) init();
        //System.out.println("workSpaceDir: "+workSpaceDir);
        return workSpaceDir;
    }

    static void init() {
        runningDir = new File("").getAbsolutePath(); // -> /home/user/

        resourceDir = PackageTools.class.getResource("/").getPath();
        // also resourceDir = PackageTools.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        // -> /home/philou/Data/drive/Dev-Sandbox/eclipse-ide/myApps/classes/production/myApps/

        packageClassDir = PackageTools.class.getResource("/" + PackageTools.class.getPackage().getName()).getPath();
        // -> /home/philou/Data/drive/Dev-Sandbox/eclipse-ide/myApps/classes/production/myApps/javaTools

        String[] resourcePath = resourceDir.split("/");
        projectName = resourcePath[resourcePath.length -1]; // -> myApps

        workSpaceDir = resourceDir.substring(0, resourceDir.indexOf(projectName)) + projectName + "/";
        // -> /home/philou/Data/drive/Dev-Sandbox/eclipse-ide/myApps/classes/production/myApps/
    }
}
