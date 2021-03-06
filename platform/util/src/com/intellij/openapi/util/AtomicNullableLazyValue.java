/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.openapi.util;

/**
 * @author peter
 */
public abstract class AtomicNullableLazyValue<T> extends NullableLazyValue<T> {
  private volatile T myValue;
  private volatile boolean myComputed;

  @Override
  public final T getValue() {
    boolean computed = myComputed;
    T value = myValue;
    if (computed) {
      return value;
    }
    synchronized (this) {
      computed = myComputed;
      value = myValue;
      if (!computed) {
        myValue = value = compute();
        myComputed = true;
      }
    }
    return value;
  }
}
