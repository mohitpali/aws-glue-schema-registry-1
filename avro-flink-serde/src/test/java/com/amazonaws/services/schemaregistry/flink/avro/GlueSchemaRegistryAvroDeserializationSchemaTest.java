/*
 * Copyright 2020 Amazon.com, Inc. or its affiliates.
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazonaws.services.schemaregistry.flink.avro;

import com.amazonaws.services.schemaregistry.utils.AWSSchemaRegistryConstants;
import org.apache.avro.Schema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GlueSchemaRegistryAvroDeserializationSchemaTest {
    private static Schema userSchema;
    private static Map<String, Object> configs = new HashMap<>();

    private static final String AVRO_USER_SCHEMA_FILE = "src/test/java/resources/avro/user.avsc";

    @BeforeEach
    public void setup() throws IOException {
        configs.put(AWSSchemaRegistryConstants.AWS_REGION, "us-west-2");
        configs.put(AWSSchemaRegistryConstants.AWS_ENDPOINT, "https://test");
        configs.put(AWSSchemaRegistryConstants.SCHEMA_AUTO_REGISTRATION_SETTING, true);

        Schema.Parser parser = new Schema.Parser();
        userSchema = parser.parse(new File(AVRO_USER_SCHEMA_FILE));
    }

    /**
     * Test whether forGeneric method works
     */
    @Test
    public void testForGeneric_withValidParams_succeeds() {
        assertThat(GlueSchemaRegistryAvroDeserializationSchema.forGeneric(userSchema, configs), notNullValue());
        assertThat(GlueSchemaRegistryAvroDeserializationSchema.forGeneric(userSchema, configs),
                instanceOf(GlueSchemaRegistryAvroDeserializationSchema.class));
    }

    /**
     * Test whether forSpecific method works
     */
    @Test
    public void testForSpecific_withValidParams_succeeds() {
        assertThat(GlueSchemaRegistryAvroDeserializationSchema.forSpecific(User.class, configs), notNullValue());
        assertThat(GlueSchemaRegistryAvroDeserializationSchema.forSpecific(User.class, configs),
                instanceOf(GlueSchemaRegistryAvroDeserializationSchema.class));
    }
}
