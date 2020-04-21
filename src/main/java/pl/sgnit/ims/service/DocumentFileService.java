package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sgnit.ims.model.DocumentFile;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.properties.FileStorageProperties;
import pl.sgnit.ims.repository.DocumentFileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DocumentFileService {

    private final DocumentFileRepository documentFileRepository;
    private final FileStorageProperties fileStorageProperties;
    private final ProcessService processService;

    @Autowired
    public DocumentFileService(DocumentFileRepository documentFileRepository, FileStorageProperties fileStorageProperties, ProcessService processService) {
        this.documentFileRepository = documentFileRepository;
        this.fileStorageProperties = fileStorageProperties;
        this.processService = processService;
    }

    public String saveDocument(MultipartFile file, Long processId) {
        String fileName = createFileName(file.getOriginalFilename(), processId, null);
        String result = "OK";
        DocumentFile documentFile = new DocumentFile();

        documentFile.setFileName(fileName);
        documentFileRepository.save(documentFile);
        fileName = createFileName(file.getOriginalFilename(), processId, documentFile.getId());
        documentFile.setFileName(fileName);
        documentFileRepository.save(documentFile);
        try {
            Path path = Paths.get(fileStorageProperties.getUploadDir() + fileName);

            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
            result = "Błąd zapisu pliku";
        }
        if ("OK".equals(result)) {
            Process process = processService.findById(processId).get();
            DocumentFile oldDocumentFile = process.getDocumentFile();

            process.setDocumentFile(documentFile);
            processService.save(process);
            removeOldDocumentFile(oldDocumentFile);
        } else {
            documentFileRepository.delete(documentFile);
        }
        return result;
    }

    private String createFileName(String originalFileName, Long processId, Long documentId) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss-SSSSSS");
        String result;

        result = processId + "_" + documentId + "_"
                + LocalDateTime.now().format(dateTimeFormatter)
                + "_" + originalFileName;
        return result;
    }

    private void removeOldDocumentFile(DocumentFile oldDocumentFile) {
        if (oldDocumentFile != null) {
            Path path = Paths.get(fileStorageProperties.getUploadDir() + oldDocumentFile.getFileName());
            try {
                Files.deleteIfExists(path);
                documentFileRepository.deleteById(oldDocumentFile.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
