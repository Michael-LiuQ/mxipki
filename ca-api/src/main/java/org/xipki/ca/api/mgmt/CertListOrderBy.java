/*
 *
 * Copyright (c) 2013 - 2019 Lijun Liao
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

package org.xipki.ca.api.mgmt;

/**
 * The mode to sort the certificates.
 *
 * @author Lijun Liao
 * @since 2.1.0
 */

public enum CertListOrderBy {

  NOT_BEFORE("notBefore"),
  NOT_BEFORE_DESC("notBefore-desc"),
  NOT_AFTER("notAfter"),
  NOT_AFTER_DESC("notAfter-desc"),
  SUBJECT("subject"),
  SUBJECT_DESC("subject-desc");

  private final String text;

  private CertListOrderBy(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public static CertListOrderBy forValue(String value) {
    for (CertListOrderBy m : values()) {
      if (m.name().equalsIgnoreCase(value) || m.text.equalsIgnoreCase(value)) {
        return m;
      }
    }

    return null;
  }

}
