/*
 * Copyright 2023 The Fury Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fury.collection;

import io.fury.util.LoggerFactory;
import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.util.Set;
import org.slf4j.Logger;
import org.testng.annotations.Test;

public class MultiKeyWeakMapTest {
  private static final Logger LOG = LoggerFactory.getLogger(MultiKeyWeakMapTest.class);

  @SuppressWarnings("unchecked")
  @Test(timeOut = 60000)
  public void testMap() throws Exception {
    MultiKeyWeakMap<Object> map = new MultiKeyWeakMap<>();
    Field referencesField = MultiKeyWeakMap.class.getDeclaredField("REFERENCES");
    referencesField.setAccessible(true);
    Set<Reference<?>> references = (Set<Reference<?>>) referencesField.get(null);
    System.gc();
    Thread.sleep(50);
    LOG.info("Before references: {}", references);
    int size = references.size();
    Object o1 = new Object();
    Object o2 = new Object();
    map.put(new Object[] {o1, o2}, true);

    o1 = null;
    o2 = null;
    System.gc();

    // `references` is global and may contain references put by others.
    while (references.size() != size) {
      Thread.sleep(10);
      System.gc();
      LOG.info("wait object gc, references: {}", references);
    }
  }
}
