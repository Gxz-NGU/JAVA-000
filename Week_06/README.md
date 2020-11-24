学习笔记

提到的4个东西：

Function<T,R>有参数有返回值 x -> 2*x

Consumer<T>有参数无返回值 (String s) -> System.out.print(s)

Supplier<T>无参数有返回值 （）->5

好用的git命令：

回退两个版本

git reset --hard head~2 代码不保留

git reset --soft head~2 代码保留

git log -10 --oneline 查看10次提交，在一行



lambda表达式的使用需要一个接口，有且仅有一个未实现的方法，否则，则不能定义lambda表达式。



lambda表达式就是为接口而生的。



泛型的使用，在lambda表达式中，如果接口定义成了T 那么其返回方法的返回类型也要是T，使用该接口的方法，要声明为<T>



流的用法：

1. Arrays.stream侧面说明，数组不能直接用作stream
2. 所有的集合类都是有stream方法的
3. Stream.of也是可以的