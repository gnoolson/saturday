package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.Base64;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.GetScriptsForExportUseCase;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetScriptsForExportUseCaseImpl implements GetScriptsForExportUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<ScriptDto> execute(Set<ProjectId> projectIdSet) {
        List<Script> allScripts = scriptRepositoryGateway.findAll();

        return allScripts.stream().filter(script -> {
            return projectIdSet.contains(script.getProjectId());
        }).map(script ->
                new ScriptDto(
                        script.getId(),
                        script.getProjectId(),
                        script.getName(),
                        script.getDescription(),
                        codeToBase64(script.getCode()),
                        script.getIncludedScripts(),
                        script.getCachingTime(),
                        script.isAutostart(),
                        script.getErrorEventHandler()
                )
        ).collect(Collectors.toList());
    }

    /*
    *
    *
    * */
    private Base64 codeToBase64(Code code){
        String s = java.util.Base64.getEncoder().encodeToString(code.getValue().getBytes(StandardCharsets.UTF_8));
        return Base64.of(s);
    }

}
