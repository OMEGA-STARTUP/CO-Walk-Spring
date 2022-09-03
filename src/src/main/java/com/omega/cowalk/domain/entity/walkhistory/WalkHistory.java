package com.omega.cowalk.domain.entity.walkhistory;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@ToString
@Document("walk_history")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Builder
public class WalkHistory {

    @Id
    private final WalkHistoryPrimaryKey walkHistoryPrimaryKey;

    private final long steps;
}
