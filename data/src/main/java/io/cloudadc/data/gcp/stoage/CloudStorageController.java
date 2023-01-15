package io.cloudadc.data.gcp.stoage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.storage.StorageClass;

import io.cloudadc.data.Entity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path="/gcp/cloudstorage", consumes = { "application/json"}, produces = { "application/json"})
@Tag(name = "CloudStorage", description = "Query GCP CloudStorage")
public class CloudStorageController {
	
	Logger log = LoggerFactory.getLogger(CloudStorageController.class);
	
	@RequestMapping(path = {"/"}, method = {RequestMethod.POST})
	@Operation(summary = "CloudStorage CURD", description = "CloudStorage CURD")
	public Map<String, Object> cloadstorage(@io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody Entity entity) {
		
		log.info("CloudStorage CURD: " + entity);

		Map<String, Object> map = new HashMap<>();
		
		List<Object> clas = new ArrayList<>();
		clas.add(StorageClass.STANDARD.toString());
		clas.add(StorageClass.NEARLINE.toString());
		clas.add(StorageClass.COLDLINE.toString());
		clas.add(StorageClass.ARCHIVE.toString());
		map.put("cloud storage class", clas);
		
		map.put("002()", "TODO");
		
		
		map.put("003()", "TODO");
		
		map.put("004()", "TODO");
		
		
		return map;
		
	}

}
