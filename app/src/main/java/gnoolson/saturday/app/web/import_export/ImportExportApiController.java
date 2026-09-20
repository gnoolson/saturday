package gnoolson.saturday.app.web.import_export;

import gnoolson.saturday.app.import_export.DownloadExportFile;
import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.app.web.import_export.dto.*;
import gnoolson.saturday.export_import.model.vo.FileName;
import gnoolson.saturday.export_import.model.vo.ImportFile;
import gnoolson.saturday.export_import.port.inbound.ExportUseCase;
import gnoolson.saturday.export_import.port.inbound.ImportUseCase;
import gnoolson.saturday.export_import.port.inbound.UploadImportFileUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('EDITOR')")
@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/ie")
public class ImportExportApiController {

    private final ExportUseCase exportUseCase;
    private final DownloadExportFile downloadExportFile;
    private final ImportUseCase importUseCase;
    private final UploadImportFileUseCase uploadImportFileUseCase;

    /*
     *
     *
     * */
    @PostMapping("/export")
    public ResponseEntity<Object> exportAction(@Valid @RequestBody ExportProjectsDto exportProjectsDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            ExportUseCase.ExportProjectsDto domainDto = DtoMapper.toDomainDto(exportProjectsDto);
            FileName fileName = exportUseCase.execute(domainDto);
            return ResponseEntity.ok(new FileNameDto(fileName.getValue()));
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Object> getExportFile(@RequestParam("fileName") String fileName) {
        try {
            File file = downloadExportFile.execute(FileName.of(fileName));
            byte[] fileContent = Files.readAllBytes(file.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\""); // For download
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileContent.length)
                    .body(new ByteArrayResource(fileContent));

        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResponseEntity<Object> importAction(@Valid @RequestBody ImportProjectsDto importProjectsDto, BindingResult result) throws IOException {
        try {
            ImportUseCase.ImportProjectsDto ImportProjectsDto = DtoMapper.toDomainDto(importProjectsDto);
            importUseCase.execute(ImportProjectsDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            UploadImportFileUseCase.UploadResultDto result = uploadImportFileUseCase.execute(FileName.of(file.getOriginalFilename()), ImportFile.of(file.getBytes()));

            UploadResultDto uploadResultDto = DtoMapper.toDto(result);
            return ResponseEntity.ok(uploadResultDto);
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
    }

}
