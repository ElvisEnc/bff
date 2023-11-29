package bg.com.bo.service.template.service;

import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.model.Response;
import bg.com.bo.service.template.model.interfaces.IHttpClientFactory;
import bg.com.bo.service.template.repository.Interfaces.IExampleRepository;
import bg.com.bo.service.template.service.Interfaces.IExampleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ExampleService implements IExampleService {
    @Value("${external.api.baseUrl}")
    private String externalApiBaseUrl;

    @Value("${external.api.complementUrl}")
    private String externalApiComplementUrl;


    @Autowired
    private IExampleRepository iExampleRepository;


    private IHttpClientFactory httpClientFactory;


    public ExampleService(IHttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    public Example getExample(int id) throws IOException {
        String path = externalApiBaseUrl + externalApiComplementUrl;
        HttpGet request = new HttpGet(path);
        CloseableHttpClient client = httpClientFactory.create();

        try (CloseableHttpResponse response = client.execute(request)) {
            String json = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Example.class);
        }
    }

    public Response createExample(Example example) throws IOException {
        String path = externalApiBaseUrl + externalApiComplementUrl;
        HttpPost request = new HttpPost(path);
        CloseableHttpClient client = httpClientFactory.create();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonExample = objectMapper.writeValueAsString(example);

        StringEntity entity = new StringEntity(jsonExample);
        request.setEntity(entity);
        request.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = client.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(jsonResponse, Response.class);
        }
    }

    public Response updateExample(Example example) throws IOException {
        String path = externalApiBaseUrl + externalApiComplementUrl;
        HttpPut request = new HttpPut(path);
        CloseableHttpClient client = httpClientFactory.create();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonExample = objectMapper.writeValueAsString(example);

            StringEntity entity = new StringEntity(jsonExample);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = client.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return objectMapper.readValue(jsonResponse, Response.class);
            }
        } finally {
            client.close();
        }
    }

    public Response deleteExample(String id) throws IOException {
        String path = externalApiBaseUrl + externalApiComplementUrl;
        HttpDelete request = new HttpDelete(path);
        CloseableHttpClient client = httpClientFactory.create();

        try (CloseableHttpResponse response = client.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, Response.class);
        }
    }
}
