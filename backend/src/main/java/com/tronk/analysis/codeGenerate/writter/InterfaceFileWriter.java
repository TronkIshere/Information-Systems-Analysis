package com.tronk.analysis.codeGenerate.writter;

import com.tronk.analysis.codeGenerate.utils.ProjectPathUtils;
import com.tronk.analysis.codeGenerate.utils.StringUtils;

import java.util.List;

public class InterfaceFileWriter {
    public static StringBuilder writeFile(String selectedEntity) {

        StringBuilder code = new StringBuilder();
        //add imports
        code.append("package ").append(ProjectPathUtils.findPackage("service")).append(";\n\n");
        code.append("import ").append(ProjectPathUtils.findPackage("request")).append(".").append(StringUtils.lowerFirst(selectedEntity)).append(".Upload").append(selectedEntity).append("Request").append(";\n");
        code.append("import ").append(ProjectPathUtils.findPackage("request")).append(".").append(StringUtils.lowerFirst(selectedEntity)).append(".Update").append(selectedEntity).append("Request").append(";\n");
        code.append("import ").append(ProjectPathUtils.findPackage("response")).append(".").append(StringUtils.lowerFirst(selectedEntity)).append(".").append(selectedEntity).append("Response").append(";\n");
        code.append("import java.util.UUID;\n");
        code.append("import java.util.List;\n\n");

        //add class
        code.append("public interface ").append(selectedEntity).append("Service {\n\n");

        // Create method
        code.append("\t").append(selectedEntity).append("Response create").append(selectedEntity)
                .append("(Upload").append(selectedEntity).append("Request request);\n\n");

        // Get by ID method
        code.append("\t").append(selectedEntity).append("Response get").append(selectedEntity)
                .append("ById(UUID id);\n\n");

        // Get all method
        code.append("\tList<").append(selectedEntity).append("Response> getAll").append(selectedEntity)
                .append("s();\n\n");

        // Update method
        code.append("\t").append(selectedEntity).append("Response update").append(selectedEntity)
                .append("(Update").append(selectedEntity).append("Request request);\n\n");

        // Delete method
        code.append("\tvoid delete").append(selectedEntity).append("ById(UUID id);\n\n");

        // Soft delete method
        code.append("\tString softDelete").append(selectedEntity).append("(UUID id);\n\n");

        code.append("}");
        return code;
    }
}
