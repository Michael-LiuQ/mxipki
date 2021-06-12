/*
 *
 * Copyright (c) 2013 - 2020 Lijun Liao
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

package org.xipki.ca.certprofile.xijson.conf;

import com.alibaba.fastjson.annotation.JSONField;
import org.xipki.ca.certprofile.xijson.conf.Describable.DescribableOid;
import org.xipki.security.EdECConstants;
import org.xipki.util.InvalidConfException;
import org.xipki.util.ValidatableConf;

import java.util.Map;

/**
 * Configuration how the keypair is generated by CA.
 *
 * @author Lijun Liao
 * @since 2.0.0
 */

public class KeypairGenerationType extends ValidatableConf {

  public static final String PARAM_keysize = "keysize";

  public static final String PARAM_publicExponent = "publicExponent";

  public static final String PARAM_curve = "curve";

  public static final String PARAM_plength = "plength";

  public static final String PARAM_qlength = "qlength";

  @JSONField(ordinal = 1)
  // CHECKSTYLE:SKIP
  private boolean inheritCA;

  @JSONField(ordinal = 2)
  private boolean forbidden;

  @JSONField(ordinal = 3)
  private DescribableOid algorithm;

  @JSONField(ordinal = 4)
  private KeyType keyType;

  /**
   * The following properties will be evaluated.
   *
   * <ul>
   *   <li>For RSA key
   *     <ul>
   *       <li>keysize (required)</li>
   *       <li>publicExponent (optional)</li>
   *     </ul>
   *   </li>
   *   <li>For EC key
   *     <ul>
   *       <li>curve (required)</li>
   *     </ul>
   *   </li>
   *   <li>For DSA key
   *     <ul>
   *       <li>plength (required)</li>
   *       <li>qlength (optional)</li>
   *     </ul>
   *   </li>
   *   <li>For Edwards and Montgomery key
   *     <ul>
   *       <li>no parameter</li>
   *     </ul>
   *   </li>
   * </ul>
   */
  @JSONField(ordinal = 5)
  private Map<String, String> parameters;

  // CHECKSTYLE:SKIP
  public boolean isInheritCA() {
    return inheritCA;
  }

  // CHECKSTYLE:SKIP
  public void setInheritCA(boolean inheritCA) {
    this.inheritCA = inheritCA;
  }

  public boolean isForbidden() {
    return forbidden;
  }

  public void setForbidden(boolean forbidden) {
    this.forbidden = forbidden;
  }

  public DescribableOid getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(DescribableOid algorithm) {
    this.algorithm = algorithm;
  }

  public KeyType getKeyType() {
    return keyType;
  }

  public void setKeyType(KeyType keyType) {
    this.keyType = keyType;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters == null || parameters.isEmpty() ? null : parameters;
  }

  @Override
  public void validate()
      throws InvalidConfException {
    if (inheritCA || forbidden) {
      return;
    }

    notNull(algorithm, "algorithm");
    validate(algorithm);
    notNull(keyType, "keyType");

    switch (keyType) {
      case rsa:
        notNull(parameters, "parameters");
        if (!parameters.containsKey(PARAM_keysize)) {
          throw new InvalidConfException("parameters " + PARAM_keysize + " may not be null");
        }
        break;
      case dsa:
        notNull(parameters, "parameters");
        if (!parameters.containsKey(PARAM_plength)) {
          throw new InvalidConfException("parameters " + PARAM_plength + " may not be null");
        }
        break;
      case ec:
        notNull(parameters, "parameters");
        if (!parameters.containsKey(PARAM_curve)) {
          throw new InvalidConfException("parameters " + PARAM_curve + " may not be null");
        }
        break;
      case ed25519:
        if (!EdECConstants.id_ED25519.getId().equals(algorithm.getOid())) {
          throw new InvalidConfException("keyType and algorithm not match");
        }
        break;
      case ed448:
        if (!EdECConstants.id_ED448.getId().equals(algorithm.getOid())) {
          throw new InvalidConfException("keyType and algorithm not match");
        }
        break;
      case x25519:
        if (!EdECConstants.id_X25519.getId().equals(algorithm.getOid())) {
          throw new InvalidConfException("keyType and algorithm not match");
        }
        break;
      case x448:
        if (!EdECConstants.id_X448.getId().equals(algorithm.getOid())) {
          throw new InvalidConfException("keyType and algorithm not match");
        }
        break;
      default:
        break;
    }
  } // method validate

  public enum KeyType {
    rsa,
    ec,
    dsa,
    ed25519,
    ed448,
    x25519,
    x448
  } // class KeyType

}
