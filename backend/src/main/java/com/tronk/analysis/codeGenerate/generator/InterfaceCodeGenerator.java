package com.tronk.analysis.codeGenerate.generator;

import com.tronk.analysis.codeGenerate.writter.InterfaceFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class InterfaceCodeGenerator {
    public static void createFile(Path interfaceDirectoryPath, String selectedEntity) throws IOException {
        String interfaceName = selectedEntity + "Service";
        Path filePath = interfaceDirectoryPath.resolve(interfaceName + ".java");

        StringBuilder code = new StringBuilder();
        code.append(InterfaceFileWriter.writeFile(selectedEntity));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info("Interface created at " + filePath);
    }
}
