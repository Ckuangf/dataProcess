一、简介
本工具基于开源工具Data-Processer进行改造，能够支持中文词典，随机生成具备特征的数据。使用者可根据实际需求，创建对应的模版和词典，快速构造量级的模拟数据。

二、术语
函数变量：模版和词典中以"$Func{"开头，以"}"结尾的字符串是一个函数变量。形如：$Func{intRand()}，其中，intRand()为内置函数。不支持函数嵌套
词典变量：模版中以"$Dic{"开头，以"}"结尾的字符串是一个词典变量。形如：$Dic{name},其中，name为词典文件中的一个词典名。
自定义变量：模版中以"$Var{"开头，以"}"结尾的字符串是一个自定义变量。形如：$Var{tmp}，其中，tmp是自定义变量名。自定义变量需要与函数变量或者词典变量联合使用，中间以"="隔开，且无空格。
定义方式：$Var{tmp}=$Func{doubleRand(0,10,2)}。引用方式是；$Var{tmp}

三、内置函数
long timestamp() ：生成当前13位时间戳（ms）。
int intRand() ：生成int正随机整数。
int intRand(Integer n) ：生成0～n的随机整数。
int intRand(Integer s, Integer e) ：生成s～e的随机整数。
long longRand() ：生成long正随机整数。
double doubleRand() ：生成0～1.0的随机双精度浮点数。
doubleRand(Integer s, Integer e, Integer n) ：生成s～e的保留n位有效数字的浮点数。
String uuid() ：生成一个uuid。
String numRand(Integer n) ：生成n位随机数。
String strRand(Integer n) ：生成n位字符串，包括数字，大小写字母。
String phoneNumberRand() ：生成手机号码
String addressRand() ：生成地址
String nameRand() ：生成姓名
String idCardRand(Integer digit) ：生成身份证，入参为0或1，其中0为生成18位身份证，1为生成15位身份证
String plateRand() ：生成车牌号
String dateRand(String beginDate, String endDate, String dateFormate) ：生成区间内特定格式的时间（dateFormate参数中限制字为“@”，String类型参数格式为"value"）

四、使用简介
1、引入simulatedata-generator-0.0.1-SNAPSHOT.jar文件作为依赖
2、添加字典
（1）在dictionaries目录下添加以dic为后缀的字典文件
（2）字典定义格式为字典名=字典值，字典值支持以下两种形式：
	枚举：枚举的字典值以“|||”为分割，如男|||女
	变量：变量形式为"$Func{内置函数}"（内置函数不支持嵌套），如$Func{intRand()}
	
3、添加模板
（1）在templas目录下添加以tpl为后缀的模板文件
（2）模板内容根据实际需求编写，支持函数、词典、自定义，若需要继承某一参数的数值，可通过定义变量的形式，例：
	"version": $Var{tmp}=$Func{doubleRand(0,10,2)},
	"os": "$Var{tmp}"
	os的取值将与version保持一致
（3）模板示例与生成数据示例

4、生成数据
（1）初始化字典
 DicInitializer.init();
词典中含有模版中需要的常量以及函数，词典文件以dic为后缀名，test.dic词典文件形如:
    name=xiaoming|||hanmeimei|||lilei
    reqUrl=http://www.abc.com/a/b/c|||http://www.def.com/d/e/f
    tmp=$Func{intRand(1000000000, 1999999999)}
    b=testVar
    ot=$Func{intRand(2)}
等号前面是词典名，等号后面是词典值，值可以是字符串，也可以是函数变量，多个值用"|||"隔开。如果有多个值，取值时，会随机取其中一个值作为词典变量值。 注意：词典文件放置在应用跟目录下的dictionaries目录下。
（2）读取模板文件，创建模版分析器
String tpl = FileUtils.readFileToString(tplFile,"UTF-8");
String tplName = tplFile.getName();
TemplateAnalyzer testTplAnalyzer = new TemplateAnalyzer(tplName, tpl);
模版文件以tpl后缀名，test.tpl模版文件形如：
 {
    "infos":{
        "d_id": $Func{intRand()},
        "version": $Var{tmp}=$Func{doubleRand(0,10,2)},
        "os_type": "$Dic{ot}",
        "os": "$Var{tmp}",
        "test": "$Dic{b}"
        }
 }
在模版文件中，在需要的地方放置变量（函数，词典，或者自定义），变量定义方式如第三点（术语）所述。在上面的模版中，version取值是$Func{doubleRand(0,10,2)}的值，然后将$Func{doubleRand(0,10,2)}的值赋给$Var{tmp}，在下面os处，以$Var{tmp}方式引用。这样，version和os的取值就一样了。
（3）解析模板，生成数据
 testTplAnalyzer.analyse();


