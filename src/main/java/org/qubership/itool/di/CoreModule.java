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

package org.qubership.itool.di;

import io.vertx.core.json.JsonObject;
import org.qubership.itool.factories.GraphFactory;
import org.qubership.itool.factories.GraphFactoryImpl;
import org.qubership.itool.modules.diagram.DiagramService;
import org.qubership.itool.modules.diagram.DiagramServiceImpl;
import org.qubership.itool.modules.graph.Graph;
import org.qubership.itool.modules.graph.GraphManager;
import org.qubership.itool.modules.graph.GraphService;
import org.qubership.itool.modules.graph.GraphServiceImpl;
import org.qubership.itool.modules.processor.GraphMerger;
import org.qubership.itool.modules.processor.tasks.CreateAppVertexTask;
import org.qubership.itool.modules.processor.tasks.CreateTransitiveHttpDependenciesTask;
import org.qubership.itool.modules.processor.tasks.CreateTransitiveQueueDependenciesTask;
import org.qubership.itool.modules.processor.tasks.GraphProcessorTask;
import org.qubership.itool.modules.processor.tasks.PatchIsMicroserviceFieldTask;
import org.qubership.itool.modules.processor.tasks.PatchLanguagesNormalizationTask;
import org.qubership.itool.modules.processor.tasks.PatchMockedComponentsNormalizationTask;
import org.qubership.itool.modules.processor.tasks.PatchVertexDnsNamesNormalizationTask;
import org.qubership.itool.modules.processor.tasks.RecreateDomainsStructureTask;
import org.qubership.itool.modules.processor.tasks.RecreateHttpDependenciesTask;
import org.qubership.itool.modules.template.TemplateService;
import org.qubership.itool.modules.template.TemplateServiceImpl;

import javax.inject.Singleton;
import java.util.List;
import java.util.Properties;

import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;

@Module
public class CoreModule {
    @Provides
    @Singleton
    GraphService provideGraphService(GraphManager graphManager) {
        return new GraphServiceImpl(graphManager);
    }

    @Provides
    @Singleton
    GraphManager provideGraphManager() {
        return new GraphManager(null, null, false);
    }

    @Provides
    Graph provideGraph(GraphFactory graphFactory) {
        return graphFactory.createGraph();
    }

    @Provides
    @Singleton
    Properties provideProperties() {
        Properties properties = new Properties();
        // Add default properties if needed
        return properties;
    }

    @Provides
    @Singleton
    JsonObject provideConfig() {
        return new JsonObject();
    }

    @Provides
    DiagramService provideDiagramService(Graph graph, Properties properties) {
        return new DiagramServiceImpl(graph, properties);
    }

    @Provides
    TemplateService provideTemplateService(DiagramService diagramService, JsonObject config) {
        return new TemplateServiceImpl(diagramService, config);
    }
    
    @Provides
    @Singleton
    GraphFactory provideGraphFactory() {
        return new GraphFactoryImpl();
    }
    
    /**
     * Provides a list of finalization tasks in the specific order they should be executed.
     */
    @Provides
    @FinalizationTasks
    List<GraphProcessorTask> provideFinalizationTasks(JsonObject config) {
        return List.of(
            new CreateAppVertexTask(config),
            new RecreateHttpDependenciesTask(),
            new CreateTransitiveQueueDependenciesTask(),
            new CreateTransitiveHttpDependenciesTask(),
            new RecreateDomainsStructureTask()
        );
    }
    
    /**
     * Provides a list of normalization tasks in the specific order they should be executed.
     */
    @Provides
    @NormalizationTasks
    List<GraphProcessorTask> provideNormalizationTasks() {
        return List.of(
            new PatchIsMicroserviceFieldTask(),
            new PatchMockedComponentsNormalizationTask(),
            new PatchVertexDnsNamesNormalizationTask(),
            new PatchLanguagesNormalizationTask()
        );
    }
    
    @Provides
    @Singleton
    Vertx provideVertx() {
        return Vertx.vertx();
    }

    @Provides
    GraphMerger provideGraphMerger(Vertx vertx, GraphFactory graphFactory, @FinalizationTasks List<GraphProcessorTask> finalizationTasks, @NormalizationTasks List<GraphProcessorTask> normalizationTasks) {
        GraphMerger merger = new GraphMerger(vertx);
        merger.setDependencies(graphFactory, finalizationTasks, normalizationTasks);
        return merger;
    }
} 