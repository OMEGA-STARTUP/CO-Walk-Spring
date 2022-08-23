package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.attendance.Attendance;
import com.omega.cowalk.domain.entity.attendance.AttendancePrimaryKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AttendanceRepository extends CrudRepository<Attendance, AttendancePrimaryKey>
{
    //유저가 출석한 횟수를 가져옴
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.userId = :userId")
    long getAttendanceCountByUserId(long userId);
}
