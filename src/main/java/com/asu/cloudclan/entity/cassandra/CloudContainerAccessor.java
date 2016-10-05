package com.asu.cloudclan.entity.cassandra;

import com.datastax.driver.core.Row;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

/**
 * Created by rubinder on 9/17/16.
 */
@Accessor
public interface CloudContainerAccessor {

    @Query("SELECT COUNT(*) FROM cloud_container")
    Result<Row> count();

    @Query("SELECT * FROM cloud_container WHERE cloud_id=:cid")
    Result<CloudContainer> getByCloudId(@Param("cid") String cloudId);
}
