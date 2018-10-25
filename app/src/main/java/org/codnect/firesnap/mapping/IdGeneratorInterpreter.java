package org.codnect.firesnap.mapping;

import org.codnect.firesnap.annotation.GeneratedValue;
import org.codnect.firesnap.annotation.GenerationStrategy;
import org.codnect.firesnap.binder.BinderHelper;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.exception.AnnotationException;
import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.reflection.XClass;

/**
 * Created by Burak Koken on 25.10.2018.
 *
 * @author Burak Koken
 */
public class IdGeneratorInterpreter {

    private XClass xClass;
    private GeneratedValue generatedValueAnnotation;
    private MetadataContext metadataContext;

    public IdGeneratorInterpreter(XClass xClass,
                                  GeneratedValue generatedValueAnnotation,
                                  MetadataContext metadataContext) {
        this.xClass = xClass;
        this.generatedValueAnnotation = generatedValueAnnotation;
        this.metadataContext = metadataContext;
    }

    /**
     *
     * @return
     */
    public IdGeneratorType determine() {
        if(generatedValueAnnotation == null) {
            if(!BinderHelper.isIdPropertType(xClass)) {
                throw new MappingException("It is not supported as Id property type : " + xClass.getName());
            }
            return IdGeneratorType.ASSIGNED;
        }
        if(generatedValueAnnotation.strategy() == GenerationStrategy.AUTO) {
            if(!metadataContext.getReflectionManager().equals(xClass, String.class)) {
                throw new AnnotationException("Auto Generation Strategy supports only String types : " + xClass.getName());
            }
            return IdGeneratorType.AUTO;
        }
        return IdGeneratorType.ASSIGNED;
    }

}
