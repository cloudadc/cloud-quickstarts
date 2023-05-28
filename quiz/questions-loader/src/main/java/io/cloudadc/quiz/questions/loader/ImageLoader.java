package io.cloudadc.quiz.questions.loader;

import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class ImageLoader {
	
	public static ImageLoader create() {
		return new ImageLoader();
	}
	
	Logger log = LoggerFactory.getLogger(Main.class);
	
	@Value("${google.project.id}")
	private String projectId;
	
	@Value("${google.storage.bucket}")
    private String bucketName;
	
	public void uploadObject(String objectName, String filePath) throws IOException {
		
		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		
	    BlobId blobId = BlobId.of(bucketName, objectName);
	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
	    
	    Storage.BlobWriteOption precondition;
	    if (storage.get(bucketName, objectName) == null) {
	    	precondition = Storage.BlobWriteOption.doesNotExist();
	    } else {
	    	precondition = Storage.BlobWriteOption.generationMatch();
	    }
	    
	    storage.createFrom(blobInfo, Paths.get(filePath), precondition);
	    
	    log.info("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
	    		
	}
             
}
