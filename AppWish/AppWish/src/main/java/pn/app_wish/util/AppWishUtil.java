package pn.app_wish.util;

import pn.cg.datastorage.constant.PathConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public record AppWishUtil( ){

    private static final String DOLLAR_SIGN = "$";

    /**
     * Removes files in the java_source_code_classes_tmp folder that contains duplicates (like wineGui$2.class)
     * @param listOfFilesInJavaAppFolder The list of files in the java_source_code_classes_tmp folder
     */
    public static void removeDuplicateFilesWithAnDollarSign(List<File> listOfFilesInJavaAppFolder){
        listOfFilesInJavaAppFolder.stream().filter(file -> file.getName().contains(DOLLAR_SIGN))
                .forEach(matchedFile -> {
                    try {
                        Files.delete(matchedFile.toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });}


    /**
     * Filter an array of files with .class prefix predicate.
     * This is useful for separating .java files from .class files
     * @param listOfFilesInJavaAppFolder The list of files in the java_source_code_classes_tmp folder
     * @return File[]
     */
    public static List<File> filterOnClassPrefix(List<File> listOfFilesInJavaAppFolder){
    return listOfFilesInJavaAppFolder.stream().filter(f -> f.getPath().endsWith(".class")).collect(Collectors.toList());
    }

    /**
     * Retrieves the continue an app file files
     * @return List<Files>
     */
    public static List<File> retrieveFilesInContinueAnAppFolders(){

        final File rootOfResourceForJavaFiles = new File(PathConstants.RESOURCE_PATH
                + "java_source_code_classes_tmp" + File.separator);

        List<File> outputListOfFiles = new LinkedList<>();

        List<File> filesInRootOfResourceForJavaFilesDir = Arrays.stream(Objects.requireNonNull(rootOfResourceForJavaFiles.listFiles())).toList();

        for (File file: filesInRootOfResourceForJavaFilesDir){

            if( (file.getName().contains("_")) &&  !(file.getName().startsWith("APP_")) ){

                outputListOfFiles.addAll(Arrays.stream(Objects.requireNonNull(file.listFiles())).toList());
            }
        }

        return outputListOfFiles;
    }
}