package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sgnit.ims.model.DocumentFile;
import pl.sgnit.ims.properties.FileStorageProperties;
import pl.sgnit.ims.repository.DocumentFileRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class DocumentFileService {

    private final DocumentFileRepository documentFileRepository;
    private final FileStorageProperties fileStorageProperties;

    @Autowired
    public DocumentFileService(DocumentFileRepository documentFileRepository, FileStorageProperties fileStorageProperties) {
        this.documentFileRepository = documentFileRepository;
        this.fileStorageProperties = fileStorageProperties;
    }

    public void saveDocument(MultipartFile file, Long processId) {
        DocumentFile documentFile = new DocumentFile();
        String fileName = createFileName(file.getOriginalFilename(), processId);

        documentFile.setFileName(fileName);
        Path path = Paths.get(fileStorageProperties.getUploadDir() + fileName);
        try {
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        documentFileRepository.save(documentFile);
    }

    private String createFileName(String originalFileName, Long processId) {
        return processId + "_"
                + LocalDateTime.now().toString().replace(':', '-').replace('.', '-')
                + "_" + originalFileName;
    }
}
