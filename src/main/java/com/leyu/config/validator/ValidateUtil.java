package com.leyu.config.validator;

import com.leyu.dto.Parameter;
import com.leyu.pojo.StoragePurchase;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ValidateUtil<T> {

    private static Validator validator;

    static {
        /**
         * 使用hibernate的注解来进行验证 failFast true仅仅返回第一条错误信息 false返回所有错误
         */
        validator = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();
    }

    /**
     * @param obj 校验的对象
     * @param <T>
     * @return
     */
    public static <T> Set<ConstraintViolation<T>> validate(T obj,Class... groupClasses) {
        Set<ConstraintViolation<T>> constraintViolations;
        if(groupClasses != null && groupClasses.length>0){
            constraintViolations = validator.validate(obj,groupClasses);
        }else {
            constraintViolations = validator.validate(obj);
        }
        return constraintViolations;
    }

    public static <T> List<Parameter> validateAnd2Reslut(T obj,Class... groupClasses) {
        Set<ConstraintViolation<T>> validateResult=validate(obj,groupClasses);
        List<Parameter> resultList=new ArrayList<>();
        Iterator<ConstraintViolation<T>> it = validateResult.iterator();
        while (it.hasNext()) {
            ConstraintViolation<T> str = it.next();
            Parameter parameter=new Parameter();
            parameter.setKey(str.getPropertyPath().toString());
            parameter.setValue(str.getMessage());
            resultList.add(parameter);
        }
        return resultList;
    }

}
