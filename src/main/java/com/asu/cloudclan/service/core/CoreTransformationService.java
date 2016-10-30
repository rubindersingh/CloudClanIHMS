package com.asu.cloudclan.service.core;

import com.asu.cloudclan.vo.FinalTransformationVO;
import com.asu.cloudclan.vo.TransformationVO;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by rubinder on 10/4/16.
 */
@Service
public class CoreTransformationService {

    public InputStream optimize(InputStream inputStream) {
        return new ScalaTransformationService().optimize(inputStream);
    }

    public InputStream transform(InputStream inputStream, TransformationVO transformationVO) {
        FinalTransformationVO finalTransformationVO = new FinalTransformationVO();
        return new ScalaTransformationService().transform(inputStream, transformationVO.finalTransformationVO);
    }
}
