
## GC算法
### -XX:+UseSerialGC
串行GC

### -XX:+UseParallelGC
并行GC

### -XX:+UseConcMarkSweepGC
CmsGC（只作用于Old区，Young区则是使用改进串行算法后的多线程ParNew）
CmsGC有5个阶段，其中只有（初始化标记、最终标记）才会GC暂停，耗时很少

### -XX:+UseG1GC
G1GC，是CmsGC的升级
(详细日志太复杂，所以把 PrintGCDetails 改成 PrintGC )

___

+ java8默认GC策略是并行GC
+ 9和以后默认是G1
+ 并行GC的吞吐量最大，但实际生产环境可能对暂停时间更敏感
+ 如果内存太紧张，CmsGC和G1GC可能会退化成串行GC
+ 不是堆内存越大越好，尽管这样GC频率降低，但每次GC暂停时间可能过长
+ 一般推荐显式配置Xmx=Xms
+ YoungGC后，Eden区+使用中的存活区 => 另一个存活区
+ YoungGC后，Old区增加量 = Young区减少量 - Heap减少量
+ FullGC时，Young区直接清零，内容都复制到Old区，然后Old区做标记清理压缩等操作..
+ 内存溢出OOM：内存满了导致溢出
+ 内存泄露：程序问题导致一些对象永远无法回收，最终往往也会导致内存溢出

___

### 打印GC信息
`java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.log -Xmx512m -Xms512m -jar xxxx.jar`
