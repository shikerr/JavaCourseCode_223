
## -XX:+UseSerialGC (串行GC)
- 适合内存小的客户端程序（几十～几百M）
- 优势
    - 单线程，无线程切换导致的资源开销，在单核场景下效率高
- 缺陷
    - 单线程，效率低
    - 回收时需完全暂停业务，大内存时暂停时间(STW)过长

## -XX:+UseParallelGC (并行GC)
- 多线程执行回收，效率更高。适合延时要求不高的业务系统
- 优势
    - 吞吐量最高，相比于`ParallelGC`，GC暂停时间(STW)更短
- 缺陷
    - 回收依然需要完全暂停业务

## -XX:+UseConcMarkSweepGC (CmsGC)
- 适合对于延时有要求的业务系统
- 只作用于Old区，Young区则是使用改进串行算法后的多线程ParNew
- 优势
    - 回收分为5步，其中只有（初始化标记、最终标记）才会完全暂停业务；因此相比于`ParallelGC`，进一步减少GC暂停时间
- 缺陷
    - 相比于`ParallelGC`，吞吐量下降
    - 如果内存太紧张，可能会退化成串行GC

## -XX:+UseG1GC (G1GC)
- 适合于对延时有严格标准的业务系统。
- 是CmsGC的升级，内存分很多小块，一块块回收
- 详细日志太复杂，所以把 PrintGCDetails 改成 PrintGC
- 优势
    - 能设置期望的GC暂停时间，可以比较精确的控制
- 缺陷
    - 相比于`ParallelGC`，吞吐量下降
    - 如果内存太紧张，可能会退化成串行GC
    

## 内存
+ 对象所占内存容量 = 对象头 + 对象体 + 对齐空间
+ 64位的JVM中，对象头占据的空间是12字节，但是以8字节对齐，所以一个空类的实例至少占用16字节
+ 在32为JVM中，对象头占8个字节，以4的倍数对齐
+ Integer占用17个字节
+ Long 一般占用24个字节

## 备注
+ java8默认GC策略是并行GC，9和以后默认是G1
+ 并行GC的吞吐量最大，但实际生产环境可能对暂停时间更敏感
+ 如果内存太紧张，CmsGC和G1GC可能会退化成串行GC
+ 堆内存不是越大越好，尽管这样GC频率降低，但每次GC暂停时间可能过长
+ 启动参数一般推荐显式配置Xmx=Xms，避免在每次GC后调整堆的大小
+ YoungGC后，Eden区+使用中的存活区 => 另一个存活区
+ YoungGC后，Old区增加量 = Young区减少量 - Heap减少量
+ FullGC时，Young区直接清零，内容都复制到Old区，然后Old区做标记清理压缩等操作..
+ 【内存溢出】内存不足以分配程序所需空间；【内存泄露】程序问题导致一些对象永远无法回收，最终往往也会导致内存溢出
+ 堆溢出：java.lang.OutOfMemoryError:Java heap spcace
+ 栈溢出：java.lang.StackOverflowError
+ 方法区溢出：java.lang.OutOfMemoryError:PermGen space

##### 运行java程序时打印GC信息，并输出到文件
```
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.log -Xmx512m -Xms512m -jar xxxx.jar
```
