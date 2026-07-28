package io.openapitoools.jackson.hal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.HALTemplate;
import io.openapitools.jackson.dataformat.hal.annotation.Link;
import io.openapitools.jackson.dataformat.hal.annotation.Resource;
import io.openapitools.jackson.dataformat.hal.annotation.Template;
import io.openapitools.jackson.hal.HALMapper;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;

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

        JsonNode node = HALMapper.create().valueToTree(resource);

        assertEquals("/orders/42", node.get("_links").get("self").get("href").asString());
        assertEquals("Edit order", node.get("_templates").get("default").get("title").asString());
        assertEquals("PATCH", node.get("_templates").get("default").get("method").asString());
        assertEquals("status", node.get("_templates").get("default").get("properties").get(0).get("name").asString());
        assertEquals("OPEN", node.get("state").asString());
    }

    @Test
    public void testDeserializeTemplatesSection() throws Exception {
        String json = "{"
                + "\"_links\":{\"self\":{\"href\":\"/orders/42\"}},"
                + "\"_templates\":{\"default\":{\"title\":\"Edit order\",\"method\":\"PATCH\","
                + "\"contentType\":\"application/json\",\"properties\":[{\"name\":\"status\",\"required\":true}]}},"
                + "\"state\":\"OPEN\""
                + "}";

        FormResource resource = HALMapper.create().readValue(json, FormResource.class);

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
