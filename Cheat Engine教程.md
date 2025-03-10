>下列是对CE的官方教程进行经验总结

1.精确数值

没什么好说的，搜就行了，一般选择4字节，因为windows程序和游戏一般用4字节。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210125525.png)

2.未知的初始值

也没什么好说的，选择未知的初始值进行扫描，然后根据增加或减少选择增加的数值或减少的数值，后面可以加上未变动的数值进行范围缩小，不断重复操作，直到剩下几个地址，慢慢试就行了。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210125639.png)

3.浮点数

有时数值类型要选择单浮点或者双浮点，双浮点可以选择禁止快速扫描。数值类型也可以直接选择所有类型慢慢看变化。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210125817.png)

4.代码查找

寻找到地址后，选择下来右键选择是什么改写了这个地址，进行数值变动，检测到汇编代码出现，单击后点击替换，使用空指令然后即可锁住这个数值的变动，但这种方法往往不能达到预期的效果就是了。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130025.png)
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130121.png)

5.指针

首先找到数值所在的地址，选择是什么改写了这个地址，数值变动，检测到汇编代码出现。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130203.png)
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130228.png)

找有`[]`的就是有指针的，点击详细信息，记录这个指针指向的地址(十六进制)。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130412.png)

在新的扫描中输入这个十六进制的地址进行扫描，然后点击右下角的手动添加地址，勾选指针，输入刚才这个扫到的地址，偏移量要看`[]`有没有别的内容，有的话加到偏移量上，复杂的计算指令就用十六进制计算器算算，点击确定后，修改数值锁定即可。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130605.png)
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130704.png)

6.代码注入

找到数值地址，选择是什么改写了这个地址，数值变动后选择汇编代码，选择显示反汇编程序。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130808.png)
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130826.png)
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210130933.png)
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210131115.png)

上面菜单栏工具里选择自动汇编(或者`crtl+a`)，模板菜单选择代码注入，一般会自动录入，选择确定后对代码进行修改，`sub`在汇编语言中执行减法操作，删除或者注释掉扣一点，变为`ADD [地址],数字`，再输出就把打一下扣一点变为回复几点了。*(切记，一定要看正确再改，乱改容易崩溃)*。

![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210131032.png)

7.多级指针

找到数值的地址后，右键选择是什么访问了这个地址，数值变动后选择出现的汇编代码，点击详细信息，记录偏移值，再次执行新的扫描指针所指地址，出现了新的指针，重复上述操作，直到找到基址，通常基址为静态时，地址将以绿色标示。选择右下角的手动添加地址，勾选指针，输入基址，然后输入之前记录的偏移量指回最初的地址，修改数值后锁住基址即可。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210225631.png)

第一个指针指向的地址
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210225733.png)

第二个指针指向的地址
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210225940.png)

第三个指针指向的地址
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210230215.png)

第四个指针指向的地址
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210230322.png)

8.注入++

首先搜索到所有玩家，找出是什么改写地址，进行空值替换发现会使四个角色都是无敌，但其中有两个是敌人，所以不管用。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210230731.png)
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210230808.png)

这里选择玩家1的地址，右键选择是什么改写了这个地址。数值变动后，右键出现的汇编代码选择找出代码访问的地址，对这四个玩家进行数值变动后发现出现了四个地址，说明这四个玩家数值变化用的是一个代码，一个变了其他也会跟着变。
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210231701.png)
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210231641.png)

选中四个地址后右键选择打开选中地址的分析数据开始观察可以区分敌我的不同之处
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231210231906.png)

然后发现偏移`0014`这里正好能分成两波，记录偏移 *(绿色的是相同的部分就没必要看)*

然后随便选择一个角色，右键选择是什么改写了这个地址，数值变动后，选中这个汇编代码，单击显示反汇编程序
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231211140307.png)

和上面一样，工具里选自动汇编
![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231211140412.png)
然后模板这里选择的是代码注入

![img](https://raw.githubusercontent.com/galact-byte/Property/refs/heads/main/CE/Pasted%20image%2020231211140553.png)

`cmp`是比较的意思，这里的`[rbx+14]`是四个角色共用的地址，根据上面看到的分为1和2，所以这里通过与1比较来区分敌我。若为真，则跳转到m，`je`指令用于在特定条件下跳转到目标地址，这里比较若等于1就是友军，就跳转到m，`mov`是通用的数据传输指令，这里是把999(单浮点)复制给`[rbx+08]`；若不等于1则执行下面的，`movss`指令在针对`xmm`寄存器的指令，这里会把`xmm0`中的数值传送给地址，然后用`xmm0`中的数值减去地址中的值，最后存储回`xmm0`中，也就是是友军会变为999血，敌人则会变为0

9.靶子射击

通过之前的知识很快找到了地址，发现数值变为0会射不到靶子，5次靶子会恢复，发现数值为100，说明攻击一次是20，所以这里把靶子血量改为10，成功击碎。

10.敌机射击

这里用了笨办法，根据提示是共用一个扣血函数，先找到自己的血量地址，然后查看是什么访问了该地址，成功找到了敌人的血量地址，把敌方的血量改为1，成功击败，可惜是同归于尽。

11.涂色游戏

角色坐标是浮点型，因为敌人一直在动，所以通过暂停键不断筛选成功找到三个敌人的x坐标。随便选择一个敌人的地址，右键选择浏览相关内存区域，如下图所示，红色区域变化不停的即为三个敌人的x坐标，那后面即为y坐标，将其都改为10，三个敌人成功消失。
![img](https://github.com/galact-byte/Property/blob/main/CE/Pasted%20image%2020231211172607.png?raw=true)
![img](https://github.com/galact-byte/Property/blob/main/CE/Pasted%20image%2020231211172834.png?raw=true)
用同样的办法找到角色的x坐标，然后浏览相关内存区域，移动角色观察到下图箭头部分会变化，那后面即为y坐标，进行修改即可涂完色。
![img](https://github.com/galact-byte/Property/blob/main/CE/Pasted%20image%2020231211173747.png?raw=true)
如下图所示
![img](https://github.com/galact-byte/Property/blob/main/CE/Pasted%20image%2020231211173857.png?raw=true)
