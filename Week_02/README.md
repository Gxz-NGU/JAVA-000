## 作业1：

借鉴了其他同学运行命令行程序并获取java输出的python程序，并将原有java程序的`System.out.println`改为只输出数字 ：

```python
"""
GC分析测试脚本
"""
import os
import subprocess

if __name__ == "__main__":
    command = ["java", "xmx", "xms", "gc", "GCLogAnalysis"]

    for memory in ["128m", "512m", "1g", "2g", "4g", "8g"]:
        for gc in ["-XX:+UseSerialGC", "-XX:+UseParallelGC", "-XX:+UseConcMarkSweepGC", "-XX:+UseG1GC"]:
            xmx = "-Xmx" + memory
            xms = "-Xms" + memory
            command[1] = xmx
            command[2] = xms
            command[3] = gc

            # result = os.system(" ".join(command))
            try:
                spend = 0
                for _ in range(0, 10):
                    spend += int(subprocess.check_output(" ".join(command)))
                print(memory, gc, "::", spend / 10)
            except Exception as e:
                print(memory, gc, "::OOM")
```

**通过程序计算出各GC平均10次的创建对象次数**：

| 创建次数 | 128m | 512m   | 1g      | 2g     | 4g      | 8g     |
| -------- | ---- | ------ | ------- | ------ | ------- | ------ |
| 串行GC   | OOM  | 8444.8 | 8653.1  | 9144.4 | 7735.2  | 6032.5 |
| 并行GC   | OOM  | 7520.7 | 10906.8 | 9971.4 | 8559.8  | 6226.2 |
| CMSGC    | OOM  | 7961.5 | 10974.5 | 9191.7 | 10784.7 | 7198.6 |
| G1GC     | OOM  | 8431.7 | 9647.5  | 8213.3 | 13588   | 9738.2 |

根据表格可以看出，串行、并行 、CMS GC随着内存的增大，创建对象数有先增大后减少的趋势。

分析原因：随着内存增大，年轻代的容量也随之增大，GC次数减少，用户程序有更多的时间运行，但随着容量的增大，年轻代标记-复制的负担加重，每次运行GC的时间会变长。所以，创建对象次数出现先增大后减小的趋势符合预期。

而G1为何稳定，应该是和G1里面region的算法相关，这里第二节课还没听明白，所以先不做评述。

**接下来看一下吞吐量 GC总时间/ 1秒：**

| 吞吐量 | 128m | 512m | 1g   | 2g   | 4g   | 8g   |
| ------ | ---- | ---- | ---- | ---- | ---- | ---- |
| 串行GC | OOM  | 39%  | 29%  | 23%  | 无GC | 无GC |
| 并行GC | OOM  | 38%  | 25%  | 13%  | 无GC | 无GC |
| CMSGC  | OOM  | 32%  | 23%  | 25%  | 29%  | 37%  |
| G1GC   | OOM  | 24%  | 17%  | 35%  | 27%  | 20%  |

串行GC虽然2g比1g的GC次数少了，但明显单次GC的时间更长了。

CMSGC的初始化标记和最终标记的时间占非常少的暂停时间。但神奇的发现，CMS内存越大，后面单次GC的时间变长之外，4g和8g也还会发生youngGC，而且youngGC的吞吐量还有增大的趋势。但CMS的优势在于相比于FULL GC而言，它的延迟是真的低。

G1GC表现出的趋势就是平稳。

### 如何纵向比较各个GC算法呢？

我自己想了一下未果，然后上网查了一下，在一个博客上找到了思路：使用不同的GC算法对同一个程序进行压测，导出GC日志，统计GC的总时间，然后除以程序的总的运行时间，以此测算程序的吞吐量。然后，找一下最长的暂停时间。



## 作业2

使用压测工具superbenchmarker和wrk，演练gateway-server-0.0.1-SNAPSHOT.jar 示例。

分别测试串行、并行、CMS、G1等GC，内存测试分别为512M、4G

测试的数据如下：wrk -c 200  -d 1m -t 30 http://localhost:8088/api/hello 该数据在mbp 16寸 16G内存配置下得到的结果：

| RPS  | 512M     | 4G       |
| ---- | -------- | -------- |
| 串行 | 42088.26 | 42537.43 |
| 并行 | 38352.21 | 48761.92 |
| CMS  | 38886.83 | 41456.68 |
| G1   | 35911.60 | 45121.15 |

**有个疑问，决定RPS的到底是计算机的什么硬件？为啥有的跑出来结果是几百，有的跑出来几千，有的跑出来几万呢？**

CMS在512M下比并行要快一些，而在4G的情况下，并行反而更好。因为CMS虽然延迟低，但整体GC占用的时间也在增加。

G1的吞吐量比并行的也要低

所以，默认用并行的GC是有价值的。

（这是老师理论上的结论，但实际上在压测过程中，并行GC在4G下跑出来的结果有时会比CMSGC要低）



## 作业3

使用OkHttp访问http://localhost:8801

```java
 public static void main(String[] args) throws IOException {
       OkHttpClient client = new OkHttpClient();
       Request request = new Request.Builder().url("http://localhost:8801").build();
       try (Response response = client.newCall(request).execute()) {
        System.out.println(response.body().string());
       }
    }
```



