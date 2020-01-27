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
package org.hyperledger.besu.tests.acceptance.dsl.transaction.privacy;

import static java.util.Collections.singletonList;

import org.hyperledger.besu.enclave.types.PrivacyGroup;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.privacy.parameters.CreatePrivacyGroupParameter;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.assertj.core.util.Lists;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.besu.Besu;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.Response;

public class PrivacyRequestFactory {

  public static class GetPrivacyPrecompileAddressResponse extends Response<String> {}

  public static class GetPrivateTransactionResponse extends Response<HashMap<String, String>> {
    final String privateFrom;

    @JsonCreator
    public GetPrivateTransactionResponse(
        @JsonProperty("result") final HashMap<String, String> result) {
      this.privateFrom = result.get("privateFrom");
    }

    public String getPrivateFrom() {
      return privateFrom;
    }
  }

  public static class CreatePrivacyGroupResponse extends Response<String> {}

  public static class DeletePrivacyGroupResponse extends Response<String> {}

  public static class FindPrivacyGroupResponse extends Response<PrivacyGroup[]> {}

  private final Besu besuClient;
  private final Web3jService web3jService;

  public PrivacyRequestFactory(final Web3jService web3jService) {
    this.web3jService = web3jService;
    this.besuClient = Besu.build(web3jService);
  }

  public Besu getBesuClient() {
    return besuClient;
  }

  public Request<?, PrivDistributeTransactionResponse> privDistributeTransaction(
      final String signedPrivateTransaction) {
    return new Request<>(
        "priv_distributeRawTransaction",
        singletonList(signedPrivateTransaction),
        web3jService,
        PrivDistributeTransactionResponse.class);
  }

  public static class PrivDistributeTransactionResponse extends Response<String> {

    public PrivDistributeTransactionResponse() {}

    public String getTransactionKey() {
      return getResult();
    }
  }

  public Request<?, GetPrivacyPrecompileAddressResponse> privGetPrivacyPrecompileAddress() {
    return new Request<>(
        "priv_getPrivacyPrecompileAddress",
        Lists.emptyList(),
        web3jService,
        GetPrivacyPrecompileAddressResponse.class);
  }

  public Request<?, GetPrivateTransactionResponse> privGetPrivateTransaction(
      final String transactionHash) {
    return new Request<>(
        "priv_getPrivateTransaction",
        singletonList(transactionHash),
        web3jService,
        GetPrivateTransactionResponse.class);
  }

  public Request<?, CreatePrivacyGroupResponse> privCreatePrivacyGroup(
      final CreatePrivacyGroupParameter params) {
    return new Request<>(
        "priv_createPrivacyGroup",
        singletonList(params),
        web3jService,
        CreatePrivacyGroupResponse.class);
  }

  public Request<?, DeletePrivacyGroupResponse> privDeletePrivacyGroup(final String groupId) {
    return new Request<>(
        "priv_deletePrivacyGroup",
        singletonList(groupId),
        web3jService,
        DeletePrivacyGroupResponse.class);
  }

  public Request<?, FindPrivacyGroupResponse> privFindPrivacyGroup(final String[] groupMembers) {
    return new Request<>(
        "priv_findPrivacyGroup",
        singletonList(groupMembers),
        web3jService,
        FindPrivacyGroupResponse.class);
  }
}
