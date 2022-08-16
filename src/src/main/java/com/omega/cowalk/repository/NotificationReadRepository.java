package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.notificationread.NotificationRead;
import com.omega.cowalk.domain.entity.notificationread.NotificationReadPrimaryKey;
import org.springframework.data.repository.CrudRepository;

public interface NotificationReadRepository extends CrudRepository<NotificationRead, NotificationReadPrimaryKey>
{

}
