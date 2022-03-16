package com.jd.service;

import com.jd.service.entity.PaChongEntity;
import com.jd.vo.ResultVO;

public interface PaChongService {
    ResultVO<Void> addPachongTask(PaChongEntity paChongEntity);
}
