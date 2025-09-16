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

package org.qubership.itool.factories;

import org.qubership.itool.modules.graph.Graph;
import org.qubership.itool.modules.graph.GraphImpl;
import org.qubership.itool.modules.report.GraphReport;
import org.qubership.itool.modules.report.GraphReportImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implementation of GraphFactory that creates Graph and GraphReport instances.
 */
@Singleton
public class GraphFactoryImpl implements GraphFactory {
    
    @Inject
    public GraphFactoryImpl() {
    }
    
    @Override
    public Graph createGraph() {
        Graph graph = new GraphImpl();
        graph.setReport(createGraphReport());
        return graph;
    }
    
    @Override
    public GraphReport createGraphReport() {
        return new GraphReportImpl();
    }
} 