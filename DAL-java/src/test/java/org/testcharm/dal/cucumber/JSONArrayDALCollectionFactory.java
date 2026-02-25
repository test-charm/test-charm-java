package org.testcharm.dal.cucumber;

import org.testcharm.dal.runtime.CollectionDALCollection;
import org.testcharm.dal.runtime.DALCollection;
import org.testcharm.dal.runtime.DALCollectionFactory;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class JSONArrayDALCollectionFactory implements DALCollectionFactory<JSONArray, Object> {
    @Override
    public DALCollection<Object> create(JSONArray array) {
        List<Object> list = new ArrayList<>(array.length());
        try {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.get(i));
            }
            return new CollectionDALCollection<>(list);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
