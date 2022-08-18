package com.omega.cowalk.domain.entity.attendance;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="attendance")
@Builder
@IdClass(AttendancePrimaryKey.class)
public class Attendance {

    @Id
    @Column(name="attend_date")
    private final java.sql.Date attend_date;

    @Id
    @Column(name="user_id")
    private final long user_id;
}
