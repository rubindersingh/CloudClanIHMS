package com.asu.cloudclan.entity.cassandra.accessor;

import com.asu.cloudclan.entity.cassandra.Image;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

/**
 * Created by rubinder on 9/17/16.
 */
@Accessor
public interface ImageAccessor {

    @Query("SELECT * FROM image WHERE cloud_name=:cn")
    Result<Image> getByCloudName(@Param("cn") String cloudName);


}
