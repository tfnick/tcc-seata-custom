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
package com.demo.modules.product.action.impl;

import com.demo.modules.product.action.CompanyProductAction;
import com.demo.modules.product.entity.CompanyProduct;
import com.demo.modules.product.service.ICompanyProductService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Slf4j
public class CompanyProductActionImpl implements CompanyProductAction {
    @Resource
    private ICompanyProductService companyProductService;

    @Override
    @Transactional
    public void deduct(Long id) {
        CompanyProduct id1 = companyProductService.query().eq("id", id).one();
        id1.setAccount(id1.getAccount() - 1);
        companyProductService.updateById(id1);
        log.info("try减库存---------------------{}", id);
        String xid = RootContext.getXID();
        log.info("XID is {} in try action", xid);
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        Long id = Long.parseLong(businessActionContext.getActionContext("id") + "");
        log.info("commit库存---------------------{}", id);
        String xid = RootContext.getXID();
        log.info("XID is {} in commit action", xid);
        return true;
    }

    @Override
    public boolean cancel(BusinessActionContext businessActionContext) {
        Long id = Long.parseLong(businessActionContext.getActionContext("id") + "");
        log.info("cancel库存---------------------{}", id);
        String xid = RootContext.getXID();
        log.info("XID is {} in cancel action", xid);
        CompanyProduct id1 = companyProductService.query().eq("id", id).one();
        id1.setAccount(id1.getAccount() + 1);
        companyProductService.updateById(id1);
        return true;
    }
}
