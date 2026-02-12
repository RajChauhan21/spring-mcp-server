package com.tutorail.ai.mcp_server.entity;

import jakarta.annotation.Generated;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@RequiredArgsConstructor
public class User {

    @Id
    private String id;

    private String name;
}
