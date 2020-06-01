package ru.otus.vsh.hw08.json;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw08.json.classes.Colors;
import ru.otus.vsh.hw08.json.classes.collections.*;
import ru.otus.vsh.hw08.json.classes.simples.*;

import java.util.*;

class DiyJsonCollectionsTest {
    private Gson gson;

    Object test(Object o, Class<?> type) {
        try {
            var modelJson = gson.toJson(o);
            System.out.println("modelJson = " + modelJson);
            var json = DiyJson.toJson(o);
            System.out.println("DIY json = " + json);
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    void before() {
        gson = new Gson();
    }

    @Test
    void collectionOfWrapperFields() {
        var aIntegerCollection = new ArrayList<Integer>();
        aIntegerCollection.add(2);
        aIntegerCollection.add(4);
        aIntegerCollection.add(null);
        var aStringSet = new HashSet<String>();
        aStringSet.add("Good day to you");
        aStringSet.add("Happy birthday");
        var aDoubleList = new ArrayList<Double>();
        aDoubleList.add(1.234);
        aDoubleList.add(5.678);
        var object = new CollectionOfWrapperFields(
                aIntegerCollection,
                aStringSet,
                aDoubleList);
        var converted = test(object, CollectionOfWrapperFields.class);
        assert object.equals(converted);
    }

    @Test
    void collectionOfCollectionFields() {
        var aIntegerSet1 = new HashSet<Integer>();
        aIntegerSet1.add(5);
        aIntegerSet1.add(3);
        aIntegerSet1.add(null);
        var aIntegerSet2 = new HashSet<Integer>();
        aIntegerSet2.add(4);
        aIntegerSet2.add(6);
        var aIntegerSetList = new ArrayList<Set<Integer>>();
        aIntegerSetList.add(aIntegerSet1);
        aIntegerSetList.add(aIntegerSet2);
        var object = new CollectionOfCollectionFields(aIntegerSetList);
        var converted = test(object, CollectionOfCollectionFields.class);
        assert object.equals(converted);
    }

    @Test
    void collectionOfObjectFields() {
        var object1 = new SimpleObject(1, "one");
        var object2 = new SimpleObject(2, "two");
        var aSimpleObjectList = new ArrayDeque<SimpleObject>();
        aSimpleObjectList.add(object1);
        aSimpleObjectList.add(object2);
        var object = new CollectionOfObjectFields(aSimpleObjectList);
        var converted = test(object, CollectionOfObjectFields.class);
        assert object.equals(converted);
    }

    @Test
    void collectionOfEnumFields() {
        var aColorSet = new HashSet<Colors>();
        aColorSet.add(Colors.Red);
        aColorSet.add(Colors.Yellow);
        var object = new CollectionOfEnumFields(aColorSet);
        var converted = test(object, CollectionOfEnumFields.class);
        assert object.equals(converted);
    }
}