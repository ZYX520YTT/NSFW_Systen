package zhongfucheng.complain.service;

import zhongfucheng.complain.entity.Complain;
import zhongfucheng.core.service.BaseService;

import java.util.List;

/**
 * Created by ozc on 2017/6/12.
 */
public interface ComplainService extends BaseService<Complain> {

    void doTask();

    List getAnnualStatisticByYear(int year);
}
