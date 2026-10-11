package fr.cfa.hospital.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public class MongoTestSeeder {
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;

    public MongoTestSeeder(MongoTemplate mongoTemplate, ObjectMapper objectMapper) {
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper;
    }

    public void resetAndSeedDatabase() {
        mongoTemplate.getCollectionNames().forEach(collectionName -> {
            if (!collectionName.startsWith("system.")) {
                mongoTemplate.getCollection(collectionName).deleteMany(new org.bson.Document());
            }
        });

        try (InputStream inputStream = new ClassPathResource("db/test/data.json").getInputStream()) {
            List<Map<String, Object>> documents = objectMapper.readValue(inputStream, new TypeReference<>() {
            });

            for (Map<String, Object> doc : documents) {
                String className = (String) doc.get("_class");
                if (className != null) {
                    Class<?> clazz = Class.forName(className);
                    Object entity = objectMapper.convertValue(doc, clazz);

                    mongoTemplate.save(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de chargement des données", e);
        }
    }
}