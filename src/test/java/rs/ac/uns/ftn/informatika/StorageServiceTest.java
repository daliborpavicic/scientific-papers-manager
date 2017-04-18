package rs.ac.uns.ftn.informatika;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import rs.ac.uns.ftn.informatika.configuration.StorageProperties;
import rs.ac.uns.ftn.informatika.service.impl.FileSystemStorageService;

public class StorageServiceTest {
	
	@Test
	public void storeMultipartFile_givenSameFileTwice_shouldSaveFilesWithUniqueNames() throws IOException {
		StorageProperties storageProperties = new StorageProperties();
		storageProperties.setLocation(System.getProperty("java.io.tmpdir"));
		FileSystemStorageService storageService = new FileSystemStorageService(storageProperties);
		
		MockMultipartFile mockMultipartFile = createMockMultipartFile();
		String firstFileName = storageService.store(mockMultipartFile);
		String secondFileName = storageService.store(mockMultipartFile);
		
		Assert.assertFalse(firstFileName.equals(secondFileName));
	}
	
	public MockMultipartFile createMockMultipartFile() throws IOException {
		URL url = this.getClass().getResource("/01_decoupling_the_internet.pdf");
		File file = new File(url.getFile());
		FileInputStream fileInputStream = new FileInputStream(file);
		String fileName = file.getName();
		
		return new MockMultipartFile(fileName, fileName, "application/pdf", fileInputStream);
	}

}
