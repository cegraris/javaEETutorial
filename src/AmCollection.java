import sun.reflect.generics.tree.Tree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author Jiahao Wu
 * @date 2021/8/17 - 23:57
 */

/* Collection
Collection接口：单列数据，定义了存取一组对象的方法的集合
    List接口：有序且可重复的集合
        Vector实现 （古老实现类）线程安全，效率低，底层使用Object[] elementData存储
        ArrayList实现 （*）线程不安全，效率高，底层使用Object[] elementData存储
        LinkedList实现 底层使用双向链表存储（频繁插入、删除效率高）
    Set接口：无序，不可重复的集合 （添加元素的类需重写hashCode()和equals()方法，且两者要保持一致性）
        HashSet实现 （*） 线程不安全，可以存储null值，底层采用HashMap类（数组+链表）
            LinkedHashSet实现 遍历内部数据时，可按照添加的顺序（在HashSet基础上加上了额外的双向链）
        SortedSet接口
            TreeSet实现 红黑树，要求元素是同一个实现了Comparable接口的类（按指定属性排序并遍历）
                        或在构造器中提供一个Comparator
Map接口：双列数据，保存具有映射关系“key-value对”的集合
        Map中的key:无序的、不可重复的，使用Set存储所有的key -> key所在的类要重写equals()和hashCode()(以HashMap为例）
        Map中的value：无序的，可重复的，使用Collection存储所有的value ->所在类要重写equals()
        一个键值对：key-value构成了一个Entry对象
        Map中的entry：无序的、不可重复的，使用Set存储所有的entry
    Hashtable实现 （古老实现类） 线程安全，效率低，不能存储null的key和value
        Properties实现 常用来处理配置文件，key和value都是String类型
    HashMap实现 （*） 无序，线程不安全，效率高，存储null的key和value，（数组+链表+红黑树）（多线程，CurrentHashMap）
        LinkedHashMap实现 保证在遍历map元素是，可以按照添加的顺序实现遍历，在原有的基础上添加了双向指针
    SortedMap接口
        TreeMap实现 有序 保证按照添加的key进行排序，实现排序遍历（）自然排序或定制排序（红黑树）
 */
public class AmCollection {
    public static void main(String[] args) {
        //Collection接口的API
        Collection coll = new ArrayList();
        coll.add("AA"); //添加新元素
        coll.add(123);
        coll.add(new Date());
        System.out.println(coll.size()); //获取集合内元素个数
        Collection coll1 = new ArrayList();
        coll1.add("BB");
        coll1.add(456);
        coll.addAll(coll1); //将另一个集合并入
        coll.isEmpty(); //集合是否为空
        System.out.println(coll.contains("BB")); //集合是否包含某元素（Object.equals方法）
        //因此向Collection接口的实现类的对象中添加数据时，该数据的类要重写equals方法
        coll.containsAll(coll1); //coll1中的所有元素是否都包含在coll中
        coll.remove("AA"); //移除某个元素(用equals查找),返回true删除成功，false失败
        coll.removeAll(coll1); //从coll中移除所有coll1中的元素（差集）
        coll.retainAll(coll1); //从coll中移除所有非coll1中的元素（交集）
        coll.equals(coll1); //两集合是否相同
        coll.clear(); //清空集合
        System.out.println(coll.hashCode()); //返回当前对象哈希值
        Object[] obj = coll.toArray(); // Collection -> Object[]
        List<String> list = Arrays.asList(new String[]{"AA", "BB", "CC"});//String[] -> Collection
        List arr1 = Arrays.asList(new int[]{123, 456});//1个元素
        List arr2 = Arrays.asList(new Integer[]{123, 456});//2个元素

        //Iterator接口（迭代Collection）
        Iterator iterator = coll.iterator(); //返回Iterator接口的实例，用于遍历集合元素
        while (iterator.hasNext()) { //是否还有下一个元素
            System.out.println(iterator.next()); //打印下一个元素
            if ("Tom".equals(obj)) {
                iterator.remove(); //遍历过程中移除部分数据（迭代器的remove方法）
            }
        }

        //for-each循环，遍历集合、数组（JDK5.0+）内部仍然调用迭代器
        for (Object element : coll) {
            System.out.println(element);
        }

        //List接口
        //List新增常用方法：
        ArrayList list0 = new ArrayList();
        list0.add(123);
        list0.add(456);
        list0.add("AA");
        list0.add(456);
        list0.add(1, "BB"); //将元素插入指定位置
        List l = Arrays.asList(1, 2, 3);
        list.addAll(l); // 将l中元素都加入
        list0.indexOf(456); //返回首次出现的索引，没有则返回-1
        list0.lastIndexOf(456); //返回最后一次出现的索引，没有则返回-1
        list0.remove(3); //（重载）删除索引为3的元素
        list0.set(1, "CC"); //将某索引对应元素修改
        List subList = list0.subList(1, 4); //左闭右开子list
        //ArrayList
        ArrayList list1 = new ArrayList(); //仅当第一次add元素时才建立一个长度为10的数组
        ArrayList list2 = new ArrayList(20); //初始数组长度
        //LinkedList

        //Set接口(无额外定义新的方法，都是Collection中的方法)
        //无序性：不等于随机性，存储的数据在底层数组中并非按照数组索引的顺序添加，而是依照哈希值添加
        //不可重复性：
        //  HashSet：先用hashcode确定数组中存放的位置，若此位置上无其他元素则添加成果，不然对比此链（单链）上所有元素与它的哈希值是否相同
        //  若不同，则添加成功，若相同则再用equals()进行判断 ，返回true则不必添加，false则添加成功
        Set set = new HashSet();
        set.add(456);
        set.add(123);
        set.add(123);
        set.add("AA");
        set.add("CC");
        set.add(129);

        Iterator iterator1 = set.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator.next()); //与添加的顺序不一致
        }

        //HashMap 实现原理：
        //HashMap map = new HashMap(); 实例化
        //map.put(key1,value1); 第一次put创建长度为16的一维数组Node[] table
        //依据key类似HashSet将键值对存放入数组或数组对应位置下的链表（红黑树）内
        //当put重复key时，新value会覆盖旧value
        //随着数据的加入（超过临界值且要存放的位置非空），数组会扩容，默认为扩容两倍。当数组上某索引位对应元素个数>8且数组长度>64，改用红黑树代替链表，加速查找

        //LinkedHashMap，相比HashMap，采用Entry代替Node，额外添加了两个双向链表

        //HashMap常用方法：
        Map map = new HashMap();
        map.put("AA", 123); //插入
        map.put(45, 123);
        map.put("BB", 56);
        map.put("AA", 87); //修改（替换）
        System.out.println(map); //打印
        Map map1 = new HashMap();
        map1.put("CC", 123);
        map1.put("DD", 123);
        map.putAll(map1); //合并
        Object value = map.remove("CC"); // 返回被删元素，map一个键值对被删
        map.clear(); //清空所有数据

        System.out.println(map.get(45)); //查询，没有则返回null
        System.out.println(map.containsKey("BB")); //查询是否含某key，返回布尔
        System.out.println(map.containsValue(123)); //查询是否含某value
        System.out.println(map.size()); //返回map大小
        System.out.println(map.isEmpty()); //map是否为空
        System.out.println(map.equals(map1)); //查询俩map的所有键值对是否完全相同

        //遍历所有的key
        Set set1 = map.keySet();
        Iterator keyIterator = set.iterator();
        while (keyIterator.hasNext()) {
            System.out.println(keyIterator.next());
        }
        //遍历所有的value
        Collection values = map.values();
        for (Object obj2 : values) {
            System.out.println(obj);
        }
        //遍历所有的key-value
        Set entrySet = map.entrySet();
        Iterator iterator2 = entrySet.iterator();
        while (iterator2.hasNext()) {
            Object obj3 = iterator2.next();
            Map.Entry entry = (Map.Entry) obj3;
            System.out.println(entry.getKey() + "-->" + entry.getValue());
        }

        //TreeMap
        //添加key-value，要求key必须是有同一个类创建的对象,因为要按照key进行排序：自然排序、定制排序
        TreeMap treeMap = new TreeMap(); //自然排序
        treeMap.put("Apple", 23);
        treeMap.put("Cabin", 54);
        treeMap.put("Banana", 34);
        TreeMap treeMap1 = new TreeMap(new Comparator() { //定制排序
            @Override
            public int compare(Object o1, Object o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        treeMap1.put("Apple", 23);
        treeMap1.put("Cabin", 54);
        treeMap1.put("Banana", 34);

        // Properties
        Properties pros = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("jdbc.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            pros.load(fis);
            String name = pros.getProperty("name");
            String password = pros.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        //Collections工具类，可以操控Collection和Map的工具类
        Collections.reverse(list); //反转
        Collections.shuffle(list); //随机
        Collections.sort(list); //排序(自然排序)，也可以加入Comparator实现定制排序
        Collections.swap(list,0,1); //交换
        Collections.max(list); //返回给定集合中的最大元素（自然排序），也可以使用Comparator实现定制排序
        Collections.min(list);
        Collections.frequency(list,765); //某元素出现次数
        List dest = Arrays.asList(new Object[list.size()]);
        Collections.copy(dest,list); //将list中内容复制到dest中（从0位开始覆盖），dest中元素个数一定要>=list中元素个数
        Collections.replaceAll(list,"abc","def"); //将list中所有旧值改成新值
        List synchronizedList = Collections.synchronizedList(list); //返回一个线程安全的List
    }
}