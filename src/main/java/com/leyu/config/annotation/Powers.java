package com.leyu.config.annotation;


import com.leyu.utils.PowerConsts;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Powers {
	PowerConsts[] value();
}

