package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.TagRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    void save(Set<Tag> tagSet) {
        for (Tag currTag: tagSet) {
            Tag oldTag = tagRepository.findTagByName(currTag.getName());
            if (Objects.isNull(oldTag) || !Objects.equals(oldTag.getName(), currTag.getName())) {
                tagRepository.save(currTag);
            } else {
                currTag.setId(oldTag.getId());
            }
        }
        Set<Tag> tmpTags = tagSet.stream().distinct().collect(Collectors.toSet());
    }
}
