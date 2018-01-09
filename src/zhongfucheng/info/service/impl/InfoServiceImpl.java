package zhongfucheng.info.service.impl;

import org.springframework.stereotype.Service;
import zhongfucheng.core.service.impl.BaseServiceImpl;
import zhongfucheng.info.dao.InfoDao;
import zhongfucheng.info.entity.Info;
import zhongfucheng.info.service.InfoService;

import javax.annotation.Resource;

/**
 * Created by ozc on 2017/5/23.
 */

@Service
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService {

    private InfoDao infoDao;
    @Resource
    public void setInfoDao(InfoDao infoDao) {
        super.setBaseDao(infoDao);
        this.infoDao = infoDao;
    }


}
