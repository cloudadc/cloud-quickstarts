package io.cloudadc.quiz.questions.loader;

import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class ImageLoader {
	
	public static ImageLoader create() {
		return new ImageLoader();
	}
	
	Logger log = LoggerFactory.getLogger(Main.class);
	
	
	public void uploadObject(String objectName, String filePath) throws IOException {
		
		String projectId = System.getProperty("google.project.id");
		
		String bucketName = System.getProperty("google.storage.bucket");
		
		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		
	    BlobId blobId = BlobId.of(bucketName, objectName);
	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
	    
	    if (storage.get(bucketName, objectName) != null) {
	    	storage.delete(blobId);
	    	 log.info(objectName + " exist, deleted from " + bucketName);
	    } 
	    
	    storage.createFrom(blobInfo, Paths.get(filePath));
	    
	    log.info("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
	    		
	}
             
}
