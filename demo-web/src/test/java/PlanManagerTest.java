import com.alibaba.fastjson.JSON;
import com.yuantu.common.date.DateUtil;
import com.yuantu.demo.web.DemoWebApplication;
import com.yuantu.plancenter.domain.entity.PageInfo;
import com.yuantu.plancenter.domain.request.RequestPageSchInfoParams;
import com.yuantu.plancenter.entity.SchResultOriginVO;
import com.yuantu.plancenter.manager.PlancenterManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhangJingWei
 * @date 2021/8/6 9:07
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoWebApplication.class)
public class PlanManagerTest {
    @Resource
    PlancenterManager plancenterManager;

    @Test
    public void test(){
        String corpCode = "3702011121";
        Date nowDate = new Date();
        String startDateStr = DateUtil.yyyyMMddhhmmss(nowDate);
        String endDateStr = DateUtil.yyyyMMddhhmmss(nowDate);
        RequestPageSchInfoParams params = new RequestPageSchInfoParams();
        params.setCorpCode(corpCode);
        params.setIsUpdate(0);
        params.setStartDate(startDateStr);
        params.setEndDate(endDateStr);
        params.setPageSize(30);

        PageInfo<SchResultOriginVO> schResultOriginVOPageInfo = plancenterManager.pageSchResultOrigin(params);

        System.out.println(JSON.toJSONString(schResultOriginVOPageInfo));
    }
}
