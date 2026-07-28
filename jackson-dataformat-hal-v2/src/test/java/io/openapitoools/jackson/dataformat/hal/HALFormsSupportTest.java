package io.openapitoools.jackson.dataformat.hal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;
import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.HALMapper;
import io.openapitools.jackson.dataformat.hal.HALTemplate;
import io.openapitools.jackson.dataformat.hal.annotation.Link;
import io.openapitools.jackson.dataformat.hal.annotation.Resource;
import io.openapitools.jackson.dataformat.hal.annotation.Template;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class HALFormsSupportTest {

    @Test
    public void testSerializeTemplatesSection() throws Exception {
        FormResource resource = new FormResource();
        resource.self = new HALLink.Builder("/orders/42").build();
        resource.template = new HALTemplate.Builder()
                .title("Edit order")
                .method("PATCH")
                .contentType("application/json")
                .properties(Collections.singletonList(
                        new HALTemplate.Property.Builder("status")
                                .required(true)
                                .prompt("Order status")
                                .build()))
                .build();
        resource.state = "OPEN";

        JsonNode node = new HALMapper().valueToTree(resource);

        assertEquals("/orders/42", node.get("_links").get("self").get("href").asText());
        assertEquals("Edit order", node.get("_templates").get("default").get("title").asText());
        assertEquals("PATCH", node.get("_templates").get("default").get("method").asText());
        assertEquals("status", node.get("_templates").get("default").get("properties").get(0).get("name").asText());
        assertEquals("OPEN", node.get("state").asText());
    }

    @Test
    public void testDeserializeTemplatesSection() throws Exception {
        String json = "{"
                + "\"_links\":{\"self\":{\"href\":\"/orders/42\"}},"
                + "\"_templates\":{\"default\":{\"title\":\"Edit order\",\"method\":\"PATCH\","
                + "\"contentType\":\"application/json\",\"properties\":[{\"name\":\"status\",\"required\":true}]}},"
                + "\"state\":\"OPEN\""
                + "}";

        FormResource resource = new HALMapper().readValue(json, FormResource.class);

        assertNotNull(resource.self);
        assertEquals("/orders/42", resource.self.getHref());
        assertNotNull(resource.template);
        assertEquals("Edit order", resource.template.getTitle());
        assertEquals("PATCH", resource.template.getMethod());
        assertEquals("status", resource.template.getProperties().get(0).getName());
        assertEquals(true, resource.template.getProperties().get(0).getRequired());
        assertEquals("OPEN", resource.state);
    }

    @Resource
    public static class FormResource {
        @Link
        public HALLink self;

        @Template("default")
        public HALTemplate template;

        public String state;
    }
}
