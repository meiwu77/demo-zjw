package com.yuantu.demo.web.config.offline;

import com.yuantu.plancenter.manager.PlancenterManager;

/**
 * @author zhangJingWei
 * @date 2021/8/23 19:36
 * @desc
 */
public class PlancenterFacade {
    PlancenterManager plancenterManager;

    public PlancenterManager getPlancenterManager() {
        return plancenterManager;
    }

    public void setPlancenterManager(PlancenterManager plancenterManager) {
        this.plancenterManager = plancenterManager;
    }
}
