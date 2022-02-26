## 服务说明
- tcc-seata-custom
    - tcc-service-a
      `订单服务`
    - tcc-service-b
      `产品库存服务`

> 注意: 使用seata tcc模式的时候请关闭 enable-auto-data-source-proxy: false 自动代码，否则tcc模式执行后会执行at模式


## demo

场景：<br/>
当创建订单的时候库存-1 <br/>
此时两个服务为分布式事务操作<br/>
> 当两个服务调用完毕的时候1/0，发生异常，两个数据回滚，两个服务的cancel方法均被调用
> 
> 


## 调用

curl http://localhost:8081/geneOrder