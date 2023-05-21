package io.cloudadc.quiz.services.gcp.cloudstorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class ImageService {
	
	private static Storage storage = StorageOptions.getDefaultInstance().getService();
	
	@Value("${google.storage.bucket}")
    private String bucketName;
	
	public String saveImage(MultipartFile file) throws IOException {
		
		String fileName = System.nanoTime() + file.getOriginalFilename();
		
		storage.create(
                        BlobInfo.newBuilder(bucketName, fileName)
                                .setContentType(file.getContentType())
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                                .build(),
                        file.getInputStream());
		
		return "https://storage-download.googleapis.com/" + bucketName + "/" + fileName;
		
	}

}
