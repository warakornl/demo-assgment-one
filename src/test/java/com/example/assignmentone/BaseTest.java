package com.example.assignmentone;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void testBadRequest(String url, Object request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    public void tesCurlPostController(String url, Object request, String statusCode) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("statusCode").value(statusCode));
    }

    public void tesCurlGetController(String url, Object request, String statusCode) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("statusCode").value(statusCode));
    }

    public void tesCurlPutController(String url, Object request, String statusCode) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("statusCode").value(statusCode));
    }

    public void tesCurlDeleteController(String url, Object request, String statusCode) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("statusCode").value(statusCode));
    }

    public void tesCurlPostControllerFromHeader(String url, Object request, String statusCode) throws Exception {
        Map<String, Object> session = (Map<String, Object>) request;
        String keyId = (String) session.get(session);
        String sessionId = (String) session.get("sessionId");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .header("sessionId", sessionId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("statusCode").value(statusCode));
    }

    protected Object resourceMapper(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource(fileName);
        return objectMapper.readValue(resource.getFile(), Object.class);
    }

    protected Object getResourceRequest(String fileName) throws IOException {
        String path = String.format("classpath:request/%1$s.json", fileName);
        return this.resourceMapper(path);
    }

    protected Object getResourceTestData(String fileName) throws IOException {
        String path = String.format("classpath:testData/%1$s.json", fileName);
        return this.resourceMapper(path);
    }
}
