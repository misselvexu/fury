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

package io.fury.memory;

/**
 * Util for check whether bounds checking should be turned on or off.
 *
 * @author chaokunyang
 */
public class BoundsChecking {
  public static final boolean BOUNDS_CHECKING_ENABLED;

  private BoundsChecking() {}

  static {
    String envProperty = System.getenv("FURY_ENABLE_UNSAFE_MEMORY_ACCESS");
    String unsafeFlagValue = System.getProperty("fury.enable_unsafe_memory_access");
    if (unsafeFlagValue == null) {
      unsafeFlagValue = envProperty;
    }
    BOUNDS_CHECKING_ENABLED = !"true".equals(unsafeFlagValue);
  }
}
