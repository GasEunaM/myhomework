# 葫芦娃大战妖精
### 151220057  李亦彤
#### 1.应用简介及成果展示

![image](https://github.com/GasEunaM/myhomework/blob/master/ScreenShot/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-01-05%20%E4%B8%8B%E5%8D%883.29.39.png)

开始界面按下空格键进入战斗界面
界面像素800x600，拥有40x30个位置。

葫芦娃+爷爷以长蛇阵站在左侧，蛇精蝎子精以鹤翼阵站在右侧，双方形成对质局面并开始向对方移动进行战斗。
如图：蓝色、红色盒子为双方死亡成员。

![image](https://github.com/GasEunaM/myhomework/blob/master/ScreenShot/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-01-05%20%E4%B8%8B%E5%8D%883.29.56.png)

每个生物有自己的移动速度和能力等级。（速度值越大移动越慢，能力等级越高获胜概率越高）



生物|速度 | 力量
---|---|---
爷爷| 3|3
葫芦娃|2|2
小喽啰|2|1
蛇精|1|2
蝎子精|2|4



两个敌对生物相遇时，某一生物会像对方发起进攻。根据双方力量值，以计算出的概率选择胜者。败者死亡。

当战场上只剩下一方的成员时，战斗结束，显示结束界面并将战斗过程保存在"时间".dat中。

![image](https://github.com/GasEunaM/myhomework/blob/master/ScreenShot/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-01-05%20%E4%B8%8B%E5%8D%883.28.19.png)

![image](https://github.com/GasEunaM/myhomework/blob/master/ScreenShot/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-01-05%20%E4%B8%8B%E5%8D%883.31.24.png)

开始及结束界面按L键可选择文件进行战斗回放。

---

#### 2.系统框架，数据结构介绍。

###### src中共有3个package：

 field(位置场地，界面相关）：myField:战斗场地，管理战斗。
 Queue：队形基类，葫芦娃和小喽啰的队列均为Queue的子类。
 Array:方阵，存储每个位置上的生物。
 
 Replay（回放，文件相关）：singleRecord:单条记录及单条记录读写（存储一个场景）。replayRecords:一场战斗的完整记录。myRecordFile:存储记录的文件及读写相关。
 
 Thing2D（显示的生物，背景图片相关）：Thing2D:所有被显示的物体的基类。Backgroung:背景图片类，继承Thing2D。Creature：生物类，继承Thing2D, implements Runnable。实现所有生物都具有的move，battle等功能。所有具体生物如葫芦娃，爷爷等均为Creature的子类。
 

---

####  3.主要功能实现方式
##### 1.生物的run(包括move,battle。)
move：判断X轴，Y轴方向有无敌人，若有，则向敌人方向移动一个位置。若无敌人，则遍历Array向找到的第一个敌人的方向上/下移动一个位置。每移动一步后休息一段时间，该时间由每个生物的speed决定，速度越慢sleep时间越长。

battle：看自己上下左右是否有敌人，若有则向敌人发起进攻。胜率由双方力量值决定。为（50+10*(strength-enemy.strength））。

控制台会输出move，battle结果。


battle结果如下图所示

![image](https://github.com/GasEunaM/myhomework/blob/master/ScreenShot/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-01-05%20%E4%B8%8B%E5%8D%883.40.49.png)


进攻者：LOULUO6

双方：LOULUO6，YEYE

胜者：YEYE

死亡：LUOLUO6

每个生物为一个线程。针对每个线程协同的主要问题包括

1.选择完移动位置后，若该位置有生物或有生物正向该位置移动，需要进行等待。

2.若正在battle，则不能移动

3.不能选择正在battle的生物或正在move的生物作为battle对象（防止两生物同时向对方发起进攻，或battle到一半该生物死亡或移动）。

根据不同锁（position或creature）实现waitforposition，waitformove，waitforbattle函数解决上述问题。当有生物状态改变（结束战斗，死亡或是移动），让出位置时通过notifyall解除正在等待的其他生物的wait状态。

例如：若a在等待b的位置，则当b移动或是死亡时，将通知a可以移动。若a在等待与b战斗，则当b可以战斗时，通知a可以与b战斗，而当b死亡时，则通知a停止等待，不战斗。
 
##### 2.myField相关
myField中存储了所有战斗相关对象。包括显示当前局面的Array，所有生物Creatures等，实现整个战斗的控制和显示。
通过enum Mode控制当前状态{START，PLAYING，REPLAY，END}通过变换状态控制开始，结束以及界面的绘制。
开始时初始化所有对象，状态变为Playing时所有线程开始运行。每当有生物move，battle时（即场景发生改变时）通知field进行场景重绘。同时判断是否结束（即场上仅剩下一方的成员）。若结束，则确定获胜方并将状态改为END。

##### 3.回放相关
状态变为Playing时创建文件"时间".dat,myField每次update时，将Creatures集合写入文件。（仅写入每个生物名字，状态（是否死亡），当前位置即可）。游戏结束后将获胜方写入文件。

读取文件时将所有Creatures集合读入replayRecords中。最后读入输赢结果。若成功读取文件，则根据replayRecords中的每一条singleRecord进行绘制。

读写文件时对IOexception进行处理。
##### 4.测试相关
实现三个测试函数，分别用于测试是否正确判断结束以及获胜方。是否正确找到battle对象，是否正确读取回放文件。
 

---

####  4.面向对象、设计原则
将所有物体的所有功能（绘制，位置相关）在Thing2D中实现。将所有生物的共同功能（移动，战斗，死亡相关）在Creature类中实现。不同生物特特殊功能在继承Creature的子类中实现。体现了封装、继承和多态，减少的代码的冗余，增强灵活性。

使用了异常处理，多线程，集合，泛型，输入输出流等机制。

SRP：每个功能由单独的类负责实现。如控制生物运动的Creature类，将位置和生物对应的Position类，排列阵型的Queue类，控制战斗的myField类，管理当前所有位置的Array类等。

OCP:使用public，protected，private修饰符进行管理，防止其他类修改本类的成员。同时可通过增加代码来对某类的功能进行扩展。

LSP：Creature类可用其子类进行替换

---
### 其他
精彩回放：demo.dat(爷爷最后反败为胜~厉害了我的爷爷)

写大作业才感受到这学期学了这么多东西，也意识到自己还有许多知识没有掌握，运用的不熟练。果然还是平时写的少TAT。

不过写完了大作业还是很开心的，虽然自己学的不好但是还是不后悔选了java课吧！
