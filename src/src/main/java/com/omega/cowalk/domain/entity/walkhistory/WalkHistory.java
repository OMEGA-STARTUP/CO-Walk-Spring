package com.omega.cowalk.domain.entity.walkhistory;

import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDate;


@Document("walk_history")
@Builder
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@CompoundIndex(name = "walk_history_id", def = "{'walk_date' : 1, 'user_id' : 1}")
public class WalkHistory {

    @Field(name = "walk_date")
    private LocalDate walkDate;

    @Field(name = "user_id")
    private long userId;

    @Field(name = "steps", write = Field.Write.NON_NULL)
    private long steps;

}
