package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sgnit.ims.model.DocumentFile;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.repository.DocumentFileRepository;
import pl.sgnit.ims.util.DocumentFileResource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class DocumentFileService {

    private final DocumentFileRepository documentFileRepository;
    private final ProcessService processService;
    private final Environment environment;

    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    public DocumentFileService(DocumentFileRepository documentFileRepository, ProcessService processService, Environment environment) {
        this.documentFileRepository = documentFileRepository;
        this.processService = processService;
        this.environment = environment;
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss-SSSSSS");
    }

    public String saveDocument(MultipartFile file, Long processId) {
        String fileName = createFileName(file.getOriginalFilename(), processId, null);
        String result = "OK";
        DocumentFile documentFile = new DocumentFile();

        documentFile.setFileName(fileName);
        documentFileRepository.save(documentFile);
        fileName = createFileName(file.getOriginalFilename(), processId, documentFile.getId());
        documentFile.setFileName(fileName);
        documentFile.setOriginalFileName(file.getOriginalFilename());
        documentFileRepository.save(documentFile);
        try {
            Path path = Paths.get(getUploadDir() + fileName);

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

    public ResponseEntity<Resource> getDocumentFile(Long documentFileId, HttpServletRequest request) {
        Resource resource = loadFileAsResource(documentFileId);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public Boolean checkDocumentExists(Long documentFileId) {
        Optional<DocumentFile> documentFile = documentFileRepository.findById(documentFileId);

        if (documentFile.isPresent()) {
            Path path = Paths.get(getUploadDir() + documentFile.get().getFileName());

            if (Files.exists(path)) {
                return true;
            }
        }
        return false;
    }

    private String createFileName(String originalFileName, Long processId, Long documentId) {
        String result;

        result = processId + "_" + documentId + "_"
                + LocalDateTime.now().format(dateTimeFormatter)
                + "_" + originalFileName;
        return result;
    }

    private void removeOldDocumentFile(DocumentFile oldDocumentFile) {
        if (oldDocumentFile != null) {
            Path path = Paths.get(getUploadDir() + oldDocumentFile.getFileName());
            try {
                Files.deleteIfExists(path);
                documentFileRepository.deleteById(oldDocumentFile.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Resource loadFileAsResource(Long documentFileId) {
        Resource result;

        try {
            DocumentFile documentFile = documentFileRepository.findById(documentFileId).get();
            Path filePath = Paths.get(getUploadDir() + documentFile.getFileName()).normalize();
            Resource resource = new DocumentFileResource(filePath.toUri(), documentFile.getOriginalFileName());
            if (resource.exists()) {
                result = resource;
            } else {
                result = null;
            }
        } catch (MalformedURLException ex) {
            result = null;
        }
        return result;
    }

    private String getUploadDir() {
        return environment.getProperty("file.upload-dir");
    }
}
