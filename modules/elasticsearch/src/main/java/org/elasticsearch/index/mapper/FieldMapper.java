/*
 * Licensed to Elastic Search and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Elastic Search licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.mapper;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.StringHelper;
import org.elasticsearch.util.concurrent.Immutable;
import org.elasticsearch.util.concurrent.ThreadSafe;

/**
 * @author kimchy (Shay Banon)
 */
@ThreadSafe
public interface FieldMapper<T> {

    @Immutable
    public static class Names {

        private final String name;

        private final String indexName;

        private final String indexNameClean;

        private final String fullName;

        public Names(String name, String indexName, String indexNameClean, String fullName) {
            this.name = StringHelper.intern(name);
            this.indexName = StringHelper.intern(indexName);
            this.indexNameClean = StringHelper.intern(indexNameClean);
            this.fullName = StringHelper.intern(fullName);
        }

        /**
         * The logical name of the field.
         */
        public String name() {
            return name;
        }

        /**
         * The indexed name of the field. This is the name under which we will
         * store it in the index.
         */
        public String indexName() {
            return indexName;
        }

        /**
         * The cleaned index name, before any "path" modifications performed on it.
         */
        public String indexNameClean() {
            return indexNameClean;
        }

        /**
         * The full name, including dot path.
         */
        public String fullName() {
            return fullName;
        }
    }

    Names names();

    Field.Index index();

    boolean indexed();

    boolean analyzed();

    Field.Store store();

    boolean stored();

    Field.TermVector termVector();

    float boost();

    boolean omitNorms();

    boolean omitTermFreqAndPositions();

    /**
     * The analyzer that will be used to index the field.
     */
    Analyzer indexAnalyzer();

    /**
     * The analyzer that will be used to search the field.
     */
    Analyzer searchAnalyzer();

    /**
     * Returns the value that will be used as a result for search. Can be only of specific types... .
     */
    Object valueForSearch(Fieldable field);

    /**
     * Returns the actual value of the field.
     */
    T value(Fieldable field);

    /**
     * Returns the actual value of the field as string.
     */
    String valueAsString(Fieldable field);

    /**
     * Returns the indexed value.
     */
    String indexedValue(String value);

    /**
     * Returns the indexed value.
     */
    String indexedValue(T value);

    Query fieldQuery(String value);

    Filter fieldFilter(String value);

    /**
     * Constructs a range query based on the mapper.
     */
    Query rangeQuery(String lowerTerm, String upperTerm, boolean includeLower, boolean includeUpper);

    /**
     * Constructs a range query filter based on the mapper.
     */
    Filter rangeFilter(String lowerTerm, String upperTerm, boolean includeLower, boolean includeUpper);

    int sortType();
}
