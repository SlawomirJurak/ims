package pl.sgnit.ims.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sgnit.ims.service.DocumentFileService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DocumentFileRestController {
    private final DocumentFileService documentFileService;

    @Autowired
    public DocumentFileRestController(DocumentFileService documentFileService) {
        this.documentFileService = documentFileService;
    }

    @PostMapping("/documents/radd")
    public String addDocumentFile(@RequestParam("documentFile") MultipartFile file, @RequestParam("processId") Long processId) {
        String result = documentFileService.saveDocument(file, processId);

        return result;
    }

    @GetMapping("/documents/rget/{documentFileId:[1-9][0-9]*}")
    public ResponseEntity<Resource> getDocumentFile(@PathVariable Long documentFileId, HttpServletRequest request) {
        return documentFileService.getDocumentFile(documentFileId, request);
    }

    @PostMapping("/documents/rcheckExists/{documentFileId:[1-9][0-9]*}")
    public Boolean checkDocumentExists(@PathVariable Long documentFileId) {
        return documentFileService.checkDocumentExists(documentFileId);
    }
}
