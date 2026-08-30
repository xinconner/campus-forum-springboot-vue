package com.example.repository;

import com.example.entity.es.TopicDocument;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends ElasticsearchRepository<TopicDocument, Integer> {
    @Query("""
            {
               "bool": {
                   "should": [
                       { "match_phrase": { "title": { "query": "?0" } } },
                       { "match_phrase": { "intro": { "query": "?0" } } }
                   ]
               }
            }
            """)
    @Highlight(fields = {
            @HighlightField(name = "title", parameters = @HighlightParameters(numberOfFragments = 1)),
            @HighlightField(name = "intro", parameters = @HighlightParameters(numberOfFragments = 1))
    })
    List<SearchHit<TopicDocument>> findByTitleOrIntro(String keyword);

}
