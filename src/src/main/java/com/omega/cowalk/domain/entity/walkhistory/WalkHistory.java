package com.omega.cowalk.domain.entity.walkhistory;

import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
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
public class WalkHistory {

    @Indexed(name = "walkDate", direction = IndexDirection.ASCENDING, unique = true)
    private LocalDate walkDate;

    @Indexed(name = "userId", direction = IndexDirection.ASCENDING)
    private long userId;

    @Field(name = "steps", write = Field.Write.NON_NULL)
    private long steps;

}
