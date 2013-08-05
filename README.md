MarrPair
========

数字化婚姻配对尝试

一、标题：
    数字化婚姻配对尝试
二、题目：
建立一个模型，来模拟推导社会男女择偶过程。
为了模型简化，一个人的特性指标有三个，这里假设为财富、样貌、品格，每个指标均可取值1-100之间任意数字。同样也对这3项指标有自己的需求。这3个需求值取值范围都在1-98间，当然三者的和必须为100.所以任意一个人可以用以下数组来表述：
G（A、B、C、A1、B1、C1）G代表男，M代表女。
举例G11（80、50、40、10、30、60），表示男11号，拥有财富80、样貌50、品格40，对异性品格的偏好为：财富在乎程度百分之10、样貌在乎程度百分之30、品格在乎程度百分之60。
同样为了模型简化，假设信息是完全对称的，即是说，每个人都能一眼就能看清楚任意一个人的财富、样貌、品格。
还是为了模型简化，我建模所用样本为男女各100个，即男女人数相同。
每个人对异性的满意度将如下定义：每个偏好指标与异性的对应的禀赋指标相乘，三个指标的乘积再相加，即他（她）对某个异性的满意度。
举例G11（80、50、40、10、30、60）对M（50、60、80、40、10、50）的满意度为：
（10*50+30*60+60*80）= 7100分
相对的 MM 对 GG的满意度则为：
（40*80+10*50+50*40） = 5700分
好了，配对活动开始，设计的配对法则如下：
1、100个男方，顺序，轮流从0号到99号女方中挑选自己最满意的一位，然后向她发出配对邀请。
2、接受邀请最多的女方开始行动，对这些邀请的男性中，选择最满意的一位。
3、那么这两位配对成功，剔除出样本，剩下的99对继续这样配对。
4、循环该配对法则，直到最后一对男女配对成功。
三、初赛阶段要求:
1、编程语言为java，C++或C语言任意一种；运行环境windows。
2、能让用户输入自己的参数以及对各项数值的偏好,然后随机生成100位男性100位女性（包括用户在内。如果用为男性则为99男100女），数值全部随机但需满足题设限制。按照上述规则给出一个匹配结果呈现给用户。
3、若采用c/c++,要输出可执行程序；若采用java，给出jar和bat。
4、在匹配时，如果发现有多个满意度相同的对象，要求自身三个属性(财富，外貌，品格)总和大的优先，如果再相同则id小的优先。如果有2位女士的选票相同，优先级规则同上。请把主角的id置为最小值，以便在前2个条件相同情况下，主角可以优先选择。
5、程序读取指定的配置文件，获取样本，然后根据指定的输入，输出结果。同时会给出一组源数据和标准答案给学生自测。最后再让学生根据不同的，指定的输入，给出考试答案。
  请点击下载配置文件附件。附件中，male.txt,female.txt,players.txt 分别是男士样本、女士样本和主角样本各 100位。 男女样本中，每行都代表一位男士或女士的基本属性，从左到右依次是ID, 样貌,品格,财富 , 期望样貌,期望品格,期望财富，没有加入性别，需要在解析时手动添加，每个txt文本的性别都是一样的，请注意。另外，主角样本中没有ID属性，换成了性别属性，其中 0表示女性，1表示男性，其余属性依次为样貌,品格,财富,期望样貌 ,期望品格,期望财富。建议把主角的id都设置为 -1，以便满足优先选择的条件。
给出标准答案2组，用于考生自测:
1号主角(文本第一行)，选择的对象属性为(6,18,82,87,3,10)
2号主角(文本第二行)，选择的对象属性为(27,74,22,22,58,20)
同时要求考生输出9号主角(0,72,55,53,8,87,5),19号主角(0,11,4,63,22,60,18),47号主角(1,19,8,21,1,53,46),83号主角(1,23,11,17,58,31,11),99号主角(1,26,66,1,78,11,11)以及100号主角(0,68,28,19,43,11,46)的选择结果。
四、初赛阶段审核标准及评价细则
1. 功能分（40分）
如果学生最后答案错误，则该项得0分
如果答案正确，得40分
2. 代码质量分（30分）
可读性，整洁性，健壮性，可扩展性，封装性
3. 用户体验（10分）
界面美观,操作方便,有必要的信息提示
4. 代码文档质量（10分）
代码清晰，易读，注释完整
5. 单元测试（10分）
关键函数或容易出错部分应该有单元测试保证
