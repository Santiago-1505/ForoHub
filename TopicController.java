package com.alura.forohub.controller;

import com.alura.forohub.model.Topic;
import com.alura.forohub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Optional<Topic> topic = topicService.findById(id);
        return topic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Topic createTopic(@RequestBody Topic topic) {
        return topicService.save(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        Optional<Topic> optionalTopic = topicService.findById(id);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            topic.setTitle(updatedTopic.getTitle());
            topic.setMessage(updatedTopic.getMessage());
            topic.setAuthor(updatedTopic.getAuthor());
            return ResponseEntity.ok(topicService.save(topic));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        if (topicService.findById(id).isPresent()) {
            topicService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
