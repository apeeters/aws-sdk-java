/*
 * Copyright 2016-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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
import java.util.Date;

/**
 * Annotation for marking a property as auto-generatable.
 *
 * A minimal example using getter annotations,
 * <pre class="brush: java">
 * &#064;DynamoDBTable(tableName=&quot;TestTable&quot;)
 * public class TestClass {
 *     private String key;
 *     private Date createdDate;
 *     private Date lastUpdatedDate;
 *
 *     &#064;DynamoDBHashKey
 *     public String getKey() { return key; }
 *     public void setKey(String key) { this.key = key; }
 *
 *     &#064;DynamoDBAutoGeneratedTimestamp(strategy=DynamoDBAutoGenerateStrategy.CREATE)
 *     public Date getCreatedDate() { return createdDate; }
 *     public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
 *
 *     &#064;DynamoDBAutoGeneratedTimestamp(strategy=DynamoDBAutoGenerateStrategy.ALWAYS)
 *     public Date getLastUpdatedDate() { return lastUpdatedDate; }
 *     public void setLastUpdatedDate(Date lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
 * }
 * </pre>
 *
 * Please note,
 *
 * Only {@link java.util.Calendar}, {@link java.util.Date} and {@link Long} are
 * supported for now. Primitives such as {@code long} are not supported since
 * the unset (or null) state can't be detected.
 *
 * @see com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGenerateStrategy
 */
@DynamoDBAutoGenerated(generator=DynamoDBAutoGeneratedTimestamp.Generator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DynamoDBAutoGeneratedTimestamp {

    /**
     * The auto-generation strategy; default is {@code ALWAYS}.
     * @see com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGenerateStrategy
     */
    DynamoDBAutoGenerateStrategy strategy() default DynamoDBAutoGenerateStrategy.ALWAYS;

    /**
     * Default generator.
     */
    static final class Generator<T> implements DynamoDBAutoGenerator<T> {
        private final DynamoDBTypeConverter<T,Date> converter;
        private final DynamoDBAutoGenerateStrategy strategy;

        public Generator(final Class<T> targetType, final DynamoDBAutoGeneratedTimestamp annotation) {
            this.converter = StandardTypeConverters.factory().getConverter(targetType, Date.class);
            this.strategy = annotation.strategy();
        }

        @Override
        public final DynamoDBAutoGenerateStrategy getGenerateStrategy() {
            return strategy;
        }

        @Override
        public final T generate(final T currentValue) {
            return converter.convert(new Date());
        }
    }

}
