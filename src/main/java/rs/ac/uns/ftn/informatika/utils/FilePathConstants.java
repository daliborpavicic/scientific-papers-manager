package rs.ac.uns.ftn.informatika.utils;

import java.io.File;

import rs.ac.uns.ftn.informatika.Application;

public interface FilePathConstants {
	
	static final String APPLICATION_FOLDER = System.getProperty("user.home") + File.separator + Application.NAME;
	
	static final String UPLOAD_FOLDER = APPLICATION_FOLDER + File.separator + "uploads";
	
	static final String ELASTIC_SEARCH_INDEX_FOLDER = APPLICATION_FOLDER + File.separator + "es-index";

}
