package org.codnect.firesnap.reflection;

import static junit.framework.Assert.*;

import org.codnect.firesnap.exception.NotResolvedException;
import org.codnect.firesnap.reflection.binder.ApproximateTypeBinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Burak Koken on 10.9.2018.
 *
 * @author Burak Koken
 */
public class ApproximateTypeBinderTest {

    private ApproximateTypeBinder approximateTypeBinder;

    @Before
    public void initTestObjects() {
        approximateTypeBinder = new ApproximateTypeBinder();
    }

    /**
     * Object variable type
     */
    @Test
    public void testBindingForClass() throws NoSuchMethodException {
        assertEquals(Object.class, approximateTypeBinder.bind(Object.class));
    }

    /**
     * T variable type becomes Object.
     */
    @Test
    public void testBindingForTypeVariable() throws NoSuchMethodException {
        Type variableType = getMethodTypeFromApproximateTypeBinderTestModel("getTypeVariableProperty");
        assertEquals(Object.class, approximateTypeBinder.bind(variableType));
    }

    /**
     * T extends Foo variable type becomes Object.
     */
    @Test
    public void testBindingForTypeVariableWithExtends() throws NoSuchMethodException {
        Type variableType = getMethodTypeFromApproximateTypeBinderTestModel("getTypeVariableWithExtendsProperty");
        assertEquals(Object.class, approximateTypeBinder.bind(variableType));
    }

    /**
     * List variable type
     */
    @Test
    public void testBindingForCollectionClass() throws NoSuchMethodException {
        assertEquals(List.class, approximateTypeBinder.bind(List.class));
    }

    /**
     * List<E> variable type becomes List<Object>
     */
    @Test
    public void testBindingForParameterizedTypeWithTypeVariable() throws NoSuchMethodException {
        Type parameterizedType = getMethodTypeFromApproximateTypeBinderTestModel("getParameterizedTypeWithTypeVariable");
        ParameterizedType resultType = (ParameterizedType) approximateTypeBinder.bind(parameterizedType);
        assertEquals(Object.class, resultType.getActualTypeArguments()[0]);
    }

    /**
     * List<Integer> variable type
     */
    @Test
    public void testBindingForParameterizedType() throws NoSuchMethodException {
        Type parameterizedType = getMethodTypeFromApproximateTypeBinderTestModel("getParameterizedTypeProperty");
        ParameterizedType resultType = (ParameterizedType) approximateTypeBinder.bind(parameterizedType);
        assertEquals(Integer.class, resultType.getActualTypeArguments()[0]);
    }

    /**
     * List<?> type variable
     */
    @Test
    public void testBindingForUnboundedWildcardType() throws NoSuchMethodException {
        Type unboundedWildcardType = getMethodTypeFromApproximateTypeBinderTestModel("getUnboundedWildcardTypeProperty");
        assertTrue(approximateTypeBinder.bind(unboundedWildcardType).equals(unboundedWildcardType));
    }

    /**
     * List<? extends Integer> type variable
     */
    @Test
    public void testBindingForBoundedWildcardType() throws NoSuchMethodException {
        Type boundedWilcardType = getMethodTypeFromApproximateTypeBinderTestModel("getWildcardTypeProperty");
        assertTrue(approximateTypeBinder.bind(boundedWilcardType).equals(boundedWilcardType));
    }

    public Type getMethodTypeFromApproximateTypeBinderTestModel(String methodName) throws NoSuchMethodException {
        return ApproximateTypeBinderTestModel.class.getMethod(methodName, new Class[0])
                .getGenericReturnType();
    }

}
