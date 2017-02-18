package rs.ac.uns.ftn.informatika.configuration;

import static rs.ac.uns.ftn.informatika.utils.FilePathConstants.UPLOAD_FOLDER;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = UPLOAD_FOLDER;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
