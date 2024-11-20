package com.blog.demo.service;

import com.blog.demo.model.Tag;
import com.blog.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id, Tag tagDetails) {
        return tagRepository.findById(id).map(tag -> {
            tag.setName(tagDetails.getName());
            return tagRepository.save(tag);
        }).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
