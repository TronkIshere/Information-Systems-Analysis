package com.tronk.analysis.codeGenerate.generator;

import com.tronk.analysis.codeGenerate.utils.StringUtils;
import com.tronk.analysis.codeGenerate.writter.ResponseFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class ResponseCodeGenerator {
    public static void createFile(Path directoryPath, String responseName, String selectedEntity, List<String> properties) throws IOException {
        Path filePath = directoryPath.resolve(responseName + ".java");

        StringBuilder code = new StringBuilder();
        code.append(ResponseFileWriter.writeFile(properties, responseName, selectedEntity));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info(responseName + " created at " + filePath);
    }
}
