package pl.sgnit.ims.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.sgnit.ims.service.DocumentFileService;

@RestController
public class DocumentFileRestController {
    private final DocumentFileService documentFileService;

    @Autowired
    public DocumentFileRestController(DocumentFileService documentFileService) {
        this.documentFileService = documentFileService;
    }

    @PostMapping("/documents/radd")
    public String addPhoto(@RequestParam("documentFile") MultipartFile file, @RequestParam("processId") Long processId) {
        String result = documentFileService.saveDocument(file, processId);

        return result;
    }
}
