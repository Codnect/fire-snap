package org.codnect.firesnap.reflection;

import org.codnect.firesnap.exception.FireSnapException;
import org.codnect.firesnap.exception.NotResolvedException;
import org.codnect.firesnap.reflection.binder.IdentityTypeBinder;
import org.codnect.firesnap.reflection.binder.TypeBinder;
import org.codnect.firesnap.reflection.binder.TypeBinderFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Burak Koken on 27.5.2018.
 *
 * @author Burak Koken
 */
public class ReflectionManager {

    private TypeBinderFactory typeBinderFactory;
    private Map<TypePair, XClass> xClassesMap;
    private Map<MemberPair, XMethod> xMethodsMap;
    private Map<MemberPair, XField> xFieldsMap;
    private Map<MemberPair, XProperty> xPropertiesMap;

    public ReflectionManager() {
        typeBinderFactory = new TypeBinderFactory();
        xClassesMap = new HashMap<>();
        xMethodsMap = new HashMap<>();
        xFieldsMap = new HashMap<>();
        xPropertiesMap = new HashMap<>();
    }

    /**
     * Get the instance of the XClass for specified annotated
     * class.
     *
     * @param annotatedClass annotated class
     * @return the instance of the XClass for specified annotated
     * class
     */
    public XClass getXClass(Class annotatedClass) {
        return getXClass(annotatedClass, IdentityTypeBinder.getInstance());
    }

    /**
     *
     * @param classType
     * @param typeBinder
     * @return
     */
    protected XClass getXClass(Type classType, TypeBinder typeBinder) {
        Type type = typeBinder.bind(classType);
        if(type instanceof Class) {
            TypePair typePairKey = new TypePair(type, typeBinder);
            XClass xClass = xClassesMap.get(typePairKey);
            if(xClass == null) {
                xClass = new XClass((Class) type, this, typeBinder);
                xClassesMap.put(typePairKey, xClass);
            }
            return xClass;
        }
        else if(type instanceof ParameterizedType) {
            getXClass(((ParameterizedType)type).getRawType(),
                    typeBinderFactory.getBinder(type, typeBinder));
        }
        throw new NotResolvedException("This type cannot be converted to a XClass : " + type.toString());
    }

    /**
     * Converts a XClass to a Class.
     *
     * @param xClass a XField
     * @return a Class
     */
    public Class toClass(XClass xClass) {
        return (Class) xClass.toAnnotatedElement();
    }

    /**
     * Get the instance of the XMethod for specified Field
     * class member.
     *
     * @param member an instance of the Field
     * @param typeBinder
     * @return  the instance of the XField for Field
     */
    public XField getXField(Member member, TypeBinder typeBinder) {
        MemberPair memberPairKey = new MemberPair(member, typeBinder);
        XField xField = xFieldsMap.get(memberPairKey);
        if(xField != null) {
            return xField;
        }
        xField = XField.create(member, typeBinder, this);
        xFieldsMap.put(memberPairKey, xField);
        return xField;
    }

    /**
     * Converts a XField to a Field.
     *
     * @param xField a XField
     * @return a Field
     */
    public Field toField(XField xField) {
        return (Field) xField.toAnnotatedElement();
    }

    /**
     * Get the instance of the XMethod for specified Method
     * class member.
     *
     * @param member an instance of the Method
     * @return the instance of the XMethod for Method
     */
    public XMethod getXMethod(Member member, TypeBinder typeBinder) {
        MemberPair memberPairKey = new MemberPair(member, typeBinder);
        XMethod xMethod = xMethodsMap.get(memberPairKey);
        if(xMethod != null) {
            return xMethod;
        }
        xMethod = XMethod.create(member, typeBinder, this);
        xMethodsMap.put(memberPairKey, xMethod);
        return xMethod;
    }

    /**
     * Converts a XMethod to a Method.
     *
     * @param xMethod a XMethod
     * @return a Method
     */
    public Method toMethod(XMethod xMethod) {
        return (Method) xMethod.toAnnotatedElement();
    }

    /**
     * Get the instance of the XProperty for specified field or
     * method class member.
     *
     * @param member an instance of the Field or the Method
     * @return the instance of the XProperty for Field or Method
     */
    public XProperty getXProperty(Member member, TypeBinder typeBinder) {
        MemberPair memberPairKey = new MemberPair(member, typeBinder);
        XProperty xProperty = xPropertiesMap.get(memberPairKey);
        if(xProperty != null) {
            return xProperty;
        }
        xProperty = XProperty.create(member, typeBinder, this);
        xPropertiesMap.put(memberPairKey, xProperty);
        return xProperty;
    }

    /**
     *
     * @param typeBinder
     * @param type
     * @return
     */
    public XType getXType(TypeBinder typeBinder, Type type) {
        Type boundType = toApproximateBinder(typeBinder).bind(type);

        if(ReflectionUtil.isSimple(boundType)) {
            return new XSimpleType(type, typeBinder, this);
        } else if(ReflectionUtil.isArray(boundType)) {
            return new XArrayType(type, typeBinder, this);
        } else if(ReflectionUtil.isCollection(boundType)) {
            return new XCollectionType(type, typeBinder, this);
        }

        throw new IllegalArgumentException("void type cannot be convert to XType");
    }

    /**
     *
     * @param typeBinder
     * @return
     */
    public TypeBinder toApproximateBinder(TypeBinder typeBinder) {
        return typeBinderFactory.toApproximateBinder(typeBinder);
    }

    /**
     *
     * @param type
     * @return
     */
    public TypeBinder getTypeBinder(Type type) {
        if(type instanceof Class) {
            return typeBinderFactory.getBinder(type);
        } else if(type instanceof ParameterizedType) {
            return typeBinderFactory.getBinder(type);
        }
        return null;
    }

}
