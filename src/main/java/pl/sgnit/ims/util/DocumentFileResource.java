package pl.sgnit.ims.util;

import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.net.URI;

public class DocumentFileResource extends UrlResource {
    private String originalFileName;

    public DocumentFileResource(URI uri, String originalFileName) throws MalformedURLException {
        super(uri);
        this.originalFileName = originalFileName;
    }

    @Override
    public String getFilename() {
        return originalFileName;
    }
}
