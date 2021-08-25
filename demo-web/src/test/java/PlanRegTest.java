import com.alibaba.fastjson.JSON;
import com.yuantu.common.date.DateUtil;
import com.yuantu.demo.web.DemoWebApplication;
import com.yuantu.plancenter.domain.entity.PageInfo;
import com.yuantu.plancenter.domain.request.RequestAppointRegParams;
import com.yuantu.plancenter.domain.request.RequestPageSchInfoParams;
import com.yuantu.plancenter.domain.vo.AppointRegVO;
import com.yuantu.plancenter.entity.SchResultOriginVO;
import com.yuantu.plancenter.manager.PlancenterManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zhangJingWei
 * @date
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoWebApplication.class)
public class PlanRegTest {
    @Resource
    PlancenterManager plancenterManager;

    @Test
    public void test(){
        String corpCode = "3702011121";
        Date nowDate = new Date();

        String startDateStr = DateUtil.yyyyMMddhhmmss(DateUtil.minusDay(nowDate,10));
        String endDateStr = DateUtil.yyyyMMddhhmmss(nowDate);

        RequestAppointRegParams request = new RequestAppointRegParams();
        request.setOffline(true);
        request.setCorpCode(corpCode);
        request.setIdNo("412725199010108336");
        request.setName("张静伟");

        request.setStartDate(startDateStr);
        request.setEndDate(endDateStr);

        List<AppointRegVO> list = plancenterManager.querySchOrder(request);

        System.out.println(JSON.toJSONString(list));
    }
}
