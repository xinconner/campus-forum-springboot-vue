package com.example.entity.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "db_topic")
public class TopicDocument {
    @Id
    @Field(type = FieldType.Keyword)
    Integer id;
    @Field(type = FieldType.Text)
    String title;
    @Field(type = FieldType.Text,index = false)
    String content;
    @Field(type = FieldType.Text)
    String intro;
    @Field(type = FieldType.Integer)
    Integer uid;
    @Field(type = FieldType.Integer)
    Integer type;
    @Field(type = FieldType.Date, index = false, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ssXXX")
    Date time;
    @Field(type = FieldType.Boolean, index = false)
    Boolean top;
    @Field(type = FieldType.Boolean, index = false)
    Boolean locked;
    @Field(type = FieldType.Boolean, index = false)
    Boolean invisible;
}
