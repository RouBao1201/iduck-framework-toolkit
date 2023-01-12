# IPageHelper

> 封装工具原因：
>
> 原始的分页查询结果（PageInfo<T>）有太多的返回参数，实际上用到的无非就：pageNum、pageSize、total
>
> 可能对lambda和函数式编程不熟悉的使用起来会很难受，但是【1、2还是非常实用的】，里面的实现逻辑也是简简单单
>
> 函数编程、lambda真香

## 返回实体：

### PageResult

```java
// 当前页
private int pageNum;
// 每页数量
private int pageSize;
// 总数据量
private long total;
// 数据集合
private List<T> list;
```



## 使用场景：

### 1. 常规分页查询

```java
/**
 * reqDto：请求参数（必须继承PageDto,因为需要pageNum和pageSize）
 * DAO接口：lambda形式
 * IssEmergencyRpBtsDetailDto.class响应体：等同于原始PageInfo<IssEmergencyRpBtsDetailDto>中的响应枚举
 */
return IPageHelper.doStart(reqDto, () -> taskNeOrderDAO.queryLogicNeTaskPage(reqDto),
            IssEmergencyRpBtsDetailDto.class);
```

### 2. 返回空分页结果

```java
// 因为无需执行DAO,所以无需传递DAO
return IPageHelper.doEmpty(reqDto, IssEmergencyRpBtsDetailDto.class);
```

### 3. 分页查询（带后置处理分页数据）

> 为何有这个方法？
>
> 可能一个复杂的SQL需要拆开（一条sql难以实现功能），那么主分页sql采用下面的方法执行，辅助的sql自己去执行后，在**后置处理**中将值赋值给主分页sql

```java
/**
 * 前3个参数含义与【1.常规分页查询】一致。第四个参数为函数式编程（lambda）
 * 第四个函数中的参数list，实际是分页查询完成后返回的数据list集合（在此可以对其进行修改等操作,不过一般很少使用吧）
 */
return IPageHelper.doStartAfter(reqDto, () -> taskNeOrderDAO.queryLogicNeTaskPage(reqDto),
            IssEmergencyRpBtsDetailDto.class, (list) -> {
                    ......后置处理逻辑......
            });
```

### 4. 分页查询（带前置判断条件）

> 为何有这个方法？
>
> 想达到这种实现效果，可以在mybatis的xml文件中通过<choose>+<if>的形式实现。
>
> 但是这样，sql还是去执行了，既然知道这sql有问题查不出数据，那又何必去执行SQL呢！

```java
/**
 * 可以看到这个与上述方法的区别就是：前面多了：buildJudge().condition(true)，这里我写死了true，里面可以写一些校验逻辑
 * 前面的前置条件可以衔接多个.buildJudge().condition(true).condition(false)，需全部满足才会取执行SQL语句，直接返回一个空分页对象
 * 使用场景：需要判断某个参数是否存在，不存在则直接返回空分页数据
 */
return IPageHelper.buildJudge().condition(true).doStart(reqDto,
            () -> taskNeOrderDAO.queryLogicNeTaskPage(reqDto), IssEmergencyRpBtsDetailDto.class);

return IPageHelper.buildJudge().condition(true).doStartAfter(reqDto,
            () -> taskNeOrderDAO.queryLogicNeTaskPage(reqDto), IssEmergencyRpBtsDetailDto.class, 
                (list) -> {
                ......后置处理逻辑......
            });
```

