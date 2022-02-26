/*
 *  Copyright 1999-2021 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.demo.modules.order.action.impl;

import com.demo.modules.order.action.UserOrderTccAction;
import com.demo.modules.order.entity.UserOrder;
import com.demo.modules.order.service.UserOderService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Slf4j
@Component
public class UserOrderTccActionImpl implements UserOrderTccAction {
    @Resource
    private UserOderService userOderService;

    @Override
    public void geneOrder(UserOrder userOrder, Long id) {
        // 生成订单
        userOderService.save(userOrder);
        log.info("try创建订单---------------------{}", id);
        String xid = RootContext.getXID();
        log.info("XID is {} in try action", xid);
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        Long id = (Long)businessActionContext.getActionContext("id");
        log.info("commit订单---------------------{}", id);
        String xid = RootContext.getXID();
        log.info("XID is {} in commit action", xid);
        return true;
    }

    @Override
    public boolean cancel(BusinessActionContext businessActionContext) {
        Long id = (Long)businessActionContext.getActionContext("id");
        log.info("cancel订单---------------------{}", id);
        String xid = RootContext.getXID();
        log.info("XID is {} in cancel action", xid);
        if (id == null) {
            return true;
        }
        userOderService.removeById(id);
        return true;
    }
}
