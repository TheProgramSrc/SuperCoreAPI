/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class InstanceCreator {
    static Unsafe unsafe;

    public static Object create(Class<?> clazz) {
        try {
            return unsafe.allocateInstance(clazz);
        } catch (Exception ex) {
            return null;
        }
    }

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
