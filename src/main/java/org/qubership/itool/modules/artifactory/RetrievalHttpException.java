/*
 * Copyright 2024-2025 NetCracker Technology Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.qubership.itool.modules.artifactory;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;

@SuppressWarnings("serial")
public class RetrievalHttpException extends RetrievalException {

    final HttpResponse<Buffer> response;
    final String urlString;
    volatile String message;

    public RetrievalHttpException(String urlString, HttpResponse<Buffer> response) {
        super("HTTP");
        this.urlString = urlString;
        this.response = response;
    }

    public String getUrlString() {
        return urlString;
    }

    public int getStatusCode() {
        return response.statusCode();
    }

    public String getMessage() {
        if (message == null) {  // Do it lazily and hope we will never get here
            message = "Unexpected HTTP status code: "
                + response.statusCode() + " " + response.statusMessage()
                + ":\n" + response.bodyAsString("UTF-8");
        }
        return message;
    }

}
