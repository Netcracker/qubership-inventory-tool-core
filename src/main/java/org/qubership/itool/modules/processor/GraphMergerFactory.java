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

package org.qubership.itool.modules.processor;

import io.vertx.core.Vertx;

/**
 * Factory interface for creating GraphMerger instances.
 * This allows for different implementations to be provided based on configuration.
 */
public interface GraphMergerFactory {
    /**
     * Creates a new GraphMerger instance with the specified Vertx instance.
     * @param vertx The Vertx instance to use
     * @param failFast Whether to fail fast on errors
     * @return A new GraphMerger instance
     */
    GraphMerger createGraphMerger(Vertx vertx, boolean failFast);

    /**
     * Creates a new GraphMerger instance without a Vertx instance.
     * @return A new GraphMerger instance
     */
    default GraphMerger createGraphMerger() {
        return createGraphMerger(null, false);
    }
} 