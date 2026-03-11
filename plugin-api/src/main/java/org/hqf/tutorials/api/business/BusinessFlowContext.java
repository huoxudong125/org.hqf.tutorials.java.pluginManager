package org.hqf.tutorials.api.business;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class BusinessFlowContext {

    private final Map<String, Object> values = new LinkedHashMap<String, Object>();

    public void put(String key, Object value) {
        values.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = values.get(key);
        if (value == null) {
            return null;
        }
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("Key " + key + " is not of type " + type.getName());
        }
        return (T) value;
    }

    public boolean contains(String key) {
        return values.containsKey(key);
    }

    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(values);
    }
}
