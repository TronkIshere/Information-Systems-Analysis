package com.tronk.analysis.codeGenerate.generator;

import com.tronk.analysis.codeGenerate.writter.ControllerFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class ControllerCodeGenerator {
    public static void createFile(Path controllerDirectoryPath, String selectedEntity) throws IOException {
        String controllerName = selectedEntity + "Controller";
        Path filePath = controllerDirectoryPath.resolve(controllerName + ".java");

        StringBuilder code = new StringBuilder();
        code.append(ControllerFileWriter.writeFile(selectedEntity));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info("Interface created at " + filePath);
    }
}
