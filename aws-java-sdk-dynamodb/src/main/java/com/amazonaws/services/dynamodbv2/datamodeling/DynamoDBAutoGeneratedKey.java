/*
 * Copyright 2011-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amazonaws.services.dynamodbv2.datamodeling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

/**
 * Annotation for marking a hash key or range key property in a class to
 * auto-generate this key. Only String typed keys can be auto generated, and are
 * given a random UUID. The annotation can be applied to either the getter
 * method or the class field for the auto-generated key property. If the
 * annotation is applied directly to the class field, the corresponding getter
 * and setter must be declared in the same class. This annotation can be applied
 * to both primary and index keys.
 *
 * @see UUID
 */
@DynamoDBAutoGenerated(generator=DynamoDBAutoGeneratedKey.Generator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DynamoDBAutoGeneratedKey {

    /**
     * Default generator.
     */
    static final class Generator<T> implements DynamoDBAutoGenerator<T> {
        private final DynamoDBTypeConverter<T,UUID> converter;

        public Generator(final Class<T> targetType, final DynamoDBAutoGeneratedKey annotation) {
            this.converter = StandardTypeConverters.factory().getConverter(targetType, UUID.class);
        }

        @Override
        public final DynamoDBAutoGenerateStrategy getGenerateStrategy() {
            return DynamoDBAutoGenerateStrategy.CREATE;
        }

        @Override
        public final T generate(final T currentValue) {
            return converter.convert(UUID.randomUUID());
        }
    }

}
