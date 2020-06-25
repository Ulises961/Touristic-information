package com.OpenDataHub.fileio.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;

public class JsonSchemaValidator {
    private JsonSchema getJsonSchemaFromString(String content) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = factory.getSchema(content);
        return schema;
    }

    private JsonNode getJsonNodeFromStringContent(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(content);
        return node;
    }


    public Set<ValidationMessage> ValidateJson(String path, Class schemaClass) throws Exception {
        String schemaNode = JsonSchemaGenerator.GenerateSchemaFromClass(schemaClass);
        JsonSchema schema = getJsonSchemaFromString(schemaNode);

        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String fileContent = "";
        String newLine;

        while ((newLine = reader.readLine()) != null)
            fileContent += newLine;
        reader.close();

        JsonNode node = getJsonNodeFromStringContent(fileContent);
        Set<ValidationMessage> errors = schema.validate(node);
        return errors;

    }

}
