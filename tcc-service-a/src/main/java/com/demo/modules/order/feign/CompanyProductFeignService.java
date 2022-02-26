package com.demo.modules.order.feign;

import com.seata.common.api.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "tcc-service-b")
public interface CompanyProductFeignService {

    @GetMapping("/deduct")
    Result<?> deduct(@RequestParam("id") Long id);
}
