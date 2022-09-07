package com.omega.cowalk.domain.entity.walkhistory;

import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Document("walk_history")
@CompoundIndex(name = "walk_history_unique_idx", def = "{'walkDate': 1, 'userId': 1}", unique = true)
public class WalkHistory {

    @Id
    private LocalDate walkDate;

    @Id
    private long userId;

    @Field(name = "steps", write = Field.Write.NON_NULL)
    private long steps;

}
