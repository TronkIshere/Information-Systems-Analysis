package com.tronk.analysis.codeGenerate.generator;

import com.tronk.analysis.codeGenerate.utils.StringUtils;
import com.tronk.analysis.codeGenerate.writter.RequestFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class RequestCodeGenerator {
    public static void createFile(Path directoryPath, String requestName, String selectedEntity, List<String> properties) throws IOException {
        Path filePath = directoryPath.resolve(requestName + ".java");

        StringBuilder code = new StringBuilder();
        code.append(RequestFileWriter.writeFile(properties, requestName, selectedEntity));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info(requestName + " created at " + filePath);
    }
}
