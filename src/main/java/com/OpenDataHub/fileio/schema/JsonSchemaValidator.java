package com.OpenDataHub.fileio.schema;

import com.OpenDataHub.fileio.FileContentRetriever;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class JsonSchemaValidator {
    private static JsonSchema getJsonSchemaFromString(String content) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
        JsonSchema schema = factory.getSchema(content);
        return schema;
    }

    private static JsonNode getJsonNodeFromStringContent(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(content);
        return node;
    }

    /**
     * Validates a .json file and returns errors
     * @param path to the .json file
     * @param schemaClass the class which the schema gets generated from and validated to
     * @return A set of {@link ValidationMessage}
     */
    public static Set<ValidationMessage> ValidateJson(String path, Class schemaClass) {
        Logger l = LogManager.getRootLogger();

        String schemaNode = JsonSchemaGenerator.GenerateSchemaFromClass(schemaClass);
        Set<ValidationMessage> errors = new TreeSet<>();

        String contents = FileContentRetriever.ReadFileContents(path);

        try {
            JsonSchema schema = getJsonSchemaFromString(schemaNode);
            JsonNode node = getJsonNodeFromStringContent(contents);
            errors = schema.validate(node);
        }
        catch (Exception e)
        {
            l.error("JsonSchemaValidator schema error: " + e.toString());
        }
        return errors;

    }


}
