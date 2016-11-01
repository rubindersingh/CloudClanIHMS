package com.asu.cloudclan.vo;

import java.util.List;

/**
 * Created by rubinder on 10/31/16.
 */
public class HomeVO {

    List<ContainerVO> containerVOs;
    UsageDataVO usageDataVO;

    public List<ContainerVO> getContainerVOs() {
        return containerVOs;
    }

    public void setContainerVOs(List<ContainerVO> containerVOs) {
        this.containerVOs = containerVOs;
    }

    public UsageDataVO getUsageDataVO() {
        return usageDataVO;
    }

    public void setUsageDataVO(UsageDataVO usageDataVO) {
        this.usageDataVO = usageDataVO;
    }
}
