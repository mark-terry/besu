/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.ethereum.privacy.markertransaction;

import org.hyperledger.besu.crypto.KeyPair;
import org.hyperledger.besu.crypto.SignatureAlgorithmFactory;
import org.hyperledger.besu.ethereum.core.Address;
import org.hyperledger.besu.ethereum.core.Transaction;
import org.hyperledger.besu.ethereum.privacy.PrivateTransaction;

public class RandomSigningPrivateMarkerTransactionFactory extends PrivateMarkerTransactionFactory {

  public RandomSigningPrivateMarkerTransactionFactory(final Address privacyPrecompileAddress) {
    super(privacyPrecompileAddress);
  }

  @Override
  public Transaction create(
      final String privateMarkerTransactionPayload,
      final PrivateTransaction privateTransaction,
      final Address precompileAddress) {
    final KeyPair signingKey = SignatureAlgorithmFactory.getInstance().generateKeyPair();
    return create(
        privateMarkerTransactionPayload, privateTransaction, 0, signingKey, precompileAddress);
  }
}
