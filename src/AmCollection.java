import java.util.*;

/**
 * @author Jiahao Wu
 * @date 2021/8/17 - 23:57
 */

/* Collection
Collection接口：单列数据，定义了存取一组对象的方法的集合
    List接口：有序且可重复的集合
        Vector实现
        ArrayList实现
        LinkedList实现
    Set接口：无需，不可重复的集合
        HashSet实现
            LinkedHashSet实现
        SortedSet接口
            TreeSet实现
Map接口：双列数据，保存具有映射关系“key-value对”的集合
    Hashtable实现
        Properties实现
    HashMap实现
        LinkedHashMap实现
    SortedMap接口
        TreeMap实现
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
        coll.iterator(); //返回Iterator接口的实例，用于遍历集合元素
    }
}