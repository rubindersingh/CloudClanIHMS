package com.asu.cloudclan.entity.cassandra.accessor;

import com.asu.cloudclan.entity.cassandra.Container;
import com.datastax.driver.core.Row;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

/**
 * Created by rubinder on 9/17/16.
 */
@Accessor
public interface ContainerAccessor {

    @Query("SELECT COUNT(*) FROM container")
    Result<Row> count();

    @Query("SELECT * FROM container WHERE id=:id")
    Result<Container> getById(@Param("id") String id);
}
