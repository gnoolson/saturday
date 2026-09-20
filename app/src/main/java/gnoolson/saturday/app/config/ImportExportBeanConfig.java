package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.saturday.app.import_export.GetDemoImportFileGatewayImpl;
import gnoolson.saturday.export_import.application.ExportUseCaseImpl;
import gnoolson.saturday.export_import.application.ImportDemoProjectsUseCaseImpl;
import gnoolson.saturday.export_import.application.ImportUseCaseImpl;
import gnoolson.saturday.export_import.application.UploadImportFileUseCaseImpl;
import gnoolson.saturday.export_import.port.inbound.ExportUseCase;
import gnoolson.saturday.export_import.port.inbound.ImportDemoProjectsUseCase;
import gnoolson.saturday.export_import.port.inbound.ImportUseCase;
import gnoolson.saturday.export_import.port.inbound.UploadImportFileUseCase;
import gnoolson.saturday.export_import.port.outbound.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class ImportExportBeanConfig {

    @Bean
    public UploadImportFileUseCase ieBean1(UnzipImportFilesGateway unzipImportFilesGateway,
                                           ReadImportFileGateway readImportFileGateway,
                                           ProjectChecker projectChecker) {

        return new UploadImportFileUseCaseImpl(unzipImportFilesGateway, readImportFileGateway, projectChecker);
    }

    @Bean
    public ImportUseCase ieBean2(
            ReadImportFileGateway readImportFileGateway,
            ImportTablesGateway importTablesGateway,
            DeleteTempFolderGateway deleteTempFolderGateway) {

        return new ImportUseCaseImpl(
                readImportFileGateway,
                importTablesGateway,
                deleteTempFolderGateway);
    }

    @Bean
    public ExportUseCase ieBean3(GetTablesForExportGateway getTablesForExportGateway,
                                 WriteTableInCSVFileGateway writeTableInCSVFileGateway,
                                 ZipExportFilesGateway zipExportFilesGateway,
                                 CreateTempDirForExportGateway createTempDirForExportGateway,
                                 DeleteTempFolderGateway deleteTempFolderGateway,
                                 Locker locker
    ) {

        return new ExportUseCaseImpl(getTablesForExportGateway,
                writeTableInCSVFileGateway,
                zipExportFilesGateway,
                createTempDirForExportGateway,
                deleteTempFolderGateway,
                locker);
    }

    @Bean
    public GetDemoImportFileGateway ieBean4(@Value("${gnoolson.saturday.ie.demo}") String luaLibPath) throws FileNotFoundException {
        File file = ResourceUtils.getFile(luaLibPath);
        return new GetDemoImportFileGatewayImpl(file);
    }

    @Bean
    public ImportDemoProjectsUseCase ieBean5(
            ReadImportFileGateway readImportFileGateway,
            UnzipImportFilesGateway unzipImportFilesGateway,
            DeleteTempFolderGateway deleteTempFolderGateway,
            ImportTablesGateway importTablesGateway,
            DemoRepositoryGateway demoRepositoryGateway,
            GetDemoImportFileGateway getDemoImportFileGateway) {

        return new ImportDemoProjectsUseCaseImpl(readImportFileGateway, unzipImportFilesGateway,
                deleteTempFolderGateway, importTablesGateway, demoRepositoryGateway, getDemoImportFileGateway);
    }

}
