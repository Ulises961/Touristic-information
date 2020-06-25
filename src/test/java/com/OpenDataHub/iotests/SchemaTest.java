package com.OpenDataHub.iotests;


import com.OpenDataHub.fileio.schema.JsonSchemaGenerator;
import com.OpenDataHub.fileio.schema.JsonSchemaValidator;
import com.OpenDataHub.helper.TFileLoader;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.networknt.schema.ValidationMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Schema Test class")
public class SchemaTest {




    @DisplayName("Creating schema and checking if same with pregenerated")
    @Test
    void SchemaCreationAndValidation() throws IOException {
        JsonSchemaGenerator.GenerateSchemaFromClassSaveToFile(TFileLoader.LoadableFiles.GENERATED_TEST_SCHEMA.toString(),ActivityDescription.class);

        byte[] generatedschema = TFileLoader.LoadFile(TFileLoader.LoadableFiles.GENERATED_TEST_SCHEMA).getBytes();
        byte[] sampleschema = TFileLoader.LoadFile(TFileLoader.LoadableFiles.SAMPLE_SCHEMA).getBytes();

        boolean areEquals = true;
        for(int i = 0; i < generatedschema.length; i++) {
            if(generatedschema[i] != sampleschema[i])
                areEquals = false;
        }

        assertEquals(areEquals, true);

    }
    @DisplayName("Validates a basic JsonFile using a JsonSchema")
    @Test
    void TestValidation()
    {
        Set<ValidationMessage> messageSet = JsonSchemaValidator.ValidateJson("src/test/java/com/OpenDataHub/iotests/sample.json",ActivityDescription.class);
        assertEquals(messageSet.size(),0);
    }
}
